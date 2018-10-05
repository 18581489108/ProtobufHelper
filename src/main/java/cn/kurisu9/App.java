package cn.kurisu9;

import cn.kurisu9.config.Config;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.config.ProtoConfig;
import cn.kurisu9.data.KeyValue;
import cn.kurisu9.data.Result;
import cn.kurisu9.desc.DescFileParser;
import cn.kurisu9.spawner.Spawners;
import cn.kurisu9.utils.FreemarkerUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static cn.kurisu9.GlobalSetting.*;
import static cn.kurisu9.utils.FilePathUtils.*;

/**
 * Hello world!
 *
 */
public class App {
    @FunctionalInterface
    private interface Task {
        Result execute(GlobalContext context);
    }

    /**
     * 启动参数
     *
     * args[0]: config的路径
     *
     * */
    public static void main(String[] args) throws Exception {
        GlobalContext context = createContext(readConfig(args));

        List<KeyValue<String, Task>> tasks = initTasks();

        for (KeyValue<String, Task> kv : tasks) {
            Task task = kv.getValue();
            Result result = task.execute(context);
            if (result.isSuccess()) {
                continue;
            }

            System.out.println(kv.getKey() + " Failed!");
            System.out.println("The following is the error message:");
            System.out.println(result.getMessage());
            System.exit(1);
        }

    }

    private static List<KeyValue<String, Task>> initTasks() {
        List<KeyValue<String, Task>> tasks = new ArrayList<>();

        // 清理临时目录
        tasks.add(KeyValue.of("Clear temp dic", (GlobalContext context) -> {
            Path tempRootDir = context.getTempRootDir();
            try {
                FileUtils.cleanDirectory(tempRootDir.toFile());
                return Result.DEFAULT_SUCCESS;
            } catch (IOException e) {
                return Result.forFailed(e.getMessage());
            }
        }));

        // 初始化FreemarkerUtil
        tasks.add(KeyValue.of("Init FreemarkerUtil", (GlobalContext context) -> {
            Result result = FreemarkerUtil.getInstance().init(context.getTemplateDir());
            if (result.isFailed()) {
                return result;
            }

            return Result.DEFAULT_SUCCESS;
        }));

        // 解析描述文件
        tasks.add(KeyValue.of("Desc file parser", (GlobalContext context) -> {
            DescFileParser descFileParser = new DescFileParser(context);
            Result result = descFileParser.parse();
            if (result.isFailed()) {
                return result;
            }

            return Result.DEFAULT_SUCCESS;
        }));

        // 生成代码文件到临时目录
        tasks.add(KeyValue.of("Spawn code File", (GlobalContext context) -> {
            OutConfig[] outConfigs = context.getConfig().getOutConfigs();

            for (OutConfig outConfig : outConfigs) {
                Result result = Spawners.spawn(context, outConfig);
                if (result.isFailed()) {
                    return result;
                }
            }

            return Result.DEFAULT_SUCCESS;
        }));

        // TODO 移动文件到最终目录
        tasks.add(KeyValue.of("Move file to Final Dic", (GlobalContext context) -> {
            OutConfig[] outConfigs = context.getConfig().getOutConfigs();

            try {
                for (OutConfig outConfig : outConfigs) {
                    Path tempDir = context.getTempDir(outConfig.getType()).toAbsolutePath();
                    Path finalDir = context.getFinalDir(outConfig.getType()).toAbsolutePath();

                    FileUtils.copyDirectory(tempDir.toFile(), finalDir.toFile());
                }

                return Result.DEFAULT_SUCCESS;
            } catch (IOException e) {
                return Result.forFailed(e.toString());
            }

        }));

        // 输出所有任务都执行成功
        tasks.add(KeyValue.of("Tell you success", (GlobalContext context) -> {
            System.out.println("All task Success!!!");
            return Result.DEFAULT_SUCCESS;
        }));

        return tasks;
    }

    /**
     * 读取配置
     * */
    private static Config readConfig(String[] args) throws IOException {
        String configPath;
        if (args != null && args.length > 0) {
            configPath = args[0];
        } else {
            configPath  = CONFIG_PATH;
        }

        File configFile = new File(configPath);
        if (!configFile.exists()) {
            throw new FileNotFoundException("Don't find config.json in " + configPath);
        }

        String content = FileUtils.readFileToString(configFile, FILE_ENCODING);
        return JSON.parseObject(content, Config.class);
    }

    /**
     * 通关配置文件来创建全局的上下文
     * */
    private static GlobalContext createContext(Config config) {
        GlobalContext context = new GlobalContext();

        context.setConfig(config);

        addProtoConfigToContext(context, config.getProtoConfig());

        Path protocFile = Paths.get(config.getProtocFile());
        checkFilePath("protoc", protocFile);
        context.setProtocFile(protocFile);

        Path tempRootPath = Paths.get(config.getTempRootDir());
        checkPath("temp root path", tempRootPath);
        context.setTempRootDir(tempRootPath);

        Path descOutPath = tempRootPath.resolve(config.getDescOutPath());
        context.setDescOutPath(descOutPath);

        Path templateDir = Paths.get(config.getTemplateDir());
        checkPath("Template Dir", templateDir);
        context.setTemplateDir(templateDir);

        addFinalDirsToContext(context, config.getOutConfigs());

        return context;
    }

    /**
     * 将输出的最终目录添加到全局上下文
     * */
    private static void addFinalDirsToContext(GlobalContext context, OutConfig[] outConfigs) {
        for (OutConfig outConfig : outConfigs) {
            Path finalDir = Paths.get(outConfig.getFinalDir());
            checkPath(outConfig.getType() + " Final Dir", finalDir);
            context.addFinalDir(outConfig.getType(), finalDir);
        }
    }

    /**
     * 在上下文添加proto文件相关的配置
     * */
    private static void addProtoConfigToContext(GlobalContext context, ProtoConfig protoConfig) {
        Path protoSrcPath = Paths.get(protoConfig.getSrcDir());
        checkDirectoryPath("Proto src path", protoSrcPath);

        context.setProtoSrcDir(protoSrcPath);

        // 获取需要解析的proto文件
        Set<String> protoFiles = new HashSet<>();

        String[] includeFiles = protoConfig.getIncludeFiles();
        // 当指定了要编译的文件时，只考虑这部分
        if (!ArrayUtils.isNotEmpty(includeFiles)) {
            for (String file : includeFiles) {
                Path filePath = protoSrcPath.resolve(file);
                checkFilePath(file, filePath);
                protoFiles.add(file);
            }
        } else {
            // 如果不指定要编译的文件，则从源目录下读取
            Set<String> excludedFiles;
            if (ArrayUtils.isNotEmpty(protoConfig.getExcludedFiles())) {
                excludedFiles = new HashSet<>(Arrays.asList(protoConfig.getExcludedFiles()));
            } else {
                excludedFiles = new HashSet<>(0);
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(protoSrcPath,
                    (Path path) -> !Files.isDirectory(path) && FilenameUtils.isExtension(path.getFileName().toString(), PROTO_EXTENSION))
            ) {
                for (Path path : stream) {
                    String fileName = path.getFileName().toString();
                    if (excludedFiles.contains(fileName)) {
                        continue;
                    }
                    protoFiles.add(fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        context.setProtoFiles(new ArrayList<>(protoFiles));
    }


}






















