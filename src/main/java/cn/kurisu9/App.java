package cn.kurisu9;

import cn.kurisu9.config.Config;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.config.ProtoConfig;
import cn.kurisu9.spawner.Result;
import cn.kurisu9.spawner.Spawners;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
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

    /**
     * 启动参数
     *
     * args[0]: config的路径
     *
     * */
    public static void main(String[] args) throws Exception {
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
        Config config = JSON.parseObject(content, Config.class);

        GlobalContext context = createContext(config);
        //spawnDescFile(config);
        //loadProtoFiles(config.getProtoConfig());

        spawnFiles(context);
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

        Path tempRootPath = Paths.get(config.getTempRootPath());
        checkPath("temp root path", tempRootPath);
        context.setTempRootPath(tempRootPath);

        return context;
    }

    /**
     * 在上下文添加proto文件相关的配置
     * */
    private static void addProtoConfigToContext(GlobalContext context, ProtoConfig protoConfig) {
        Path protoSrcPath = Paths.get(protoConfig.getSrcPath());
        checkDirectoryPath("Proto src path", protoSrcPath);

        context.setProtoSrcPath(protoSrcPath);

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

    /**
     * 生成描述文件
     * */
    private static void spawnDescFile(Config config) {

        String protocFile = Paths.get(config.getProtocFile()).toAbsolutePath().toString();
        String srcPath = Paths.get(config.getProtoConfig().getSrcPath()).toAbsolutePath().toString();
        Path descRootPath = Paths.get(config.getTempRootPath()).resolve(config.getDescOutPath());

        String[] filesOfGenerateId = config.getProtoConfig().getFilesOfGenerateId();
        for (String file : filesOfGenerateId) {
            Command command = new Command(protocFile);
            command.addParam("-I=" + srcPath);
            command.addParam("--descriptor_set_out=" + descRootPath.resolve(FilenameUtils.getBaseName(file) +".desc").toAbsolutePath().toString());
            command.addParam(file);

            ExecResult result = ProcessUtils.exec(command);

            System.out.println(file + ": " + result.isSuccess());
            System.out.println(result.getOut());
        }


    }


    /**
     * 生成文件
     * */
    private static void spawnFiles(GlobalContext context) {
        OutConfig[] outConfigs = context.getConfig().getOutConfigs();

        for (OutConfig outConfig : outConfigs) {
            Result result = Spawners.spawn(context, outConfig);
            if (result.isFailed()) {
                System.out.println("Spawn Failed! When spawn " + outConfig.getType() + " type");
                System.out.println("The following is the error message:");
                System.out.println(result.getMessage());
                return;
            }
        }

        System.out.println("Spawn Success!!!");
    }
}






















