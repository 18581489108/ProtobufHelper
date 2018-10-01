package cn.kurisu9;

import cn.kurisu9.config.Config;
import cn.kurisu9.config.ProtoConfig;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static cn.kurisu9.GlobalSetting.*;

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

        spawnDescFile(config);
        //loadProtoFiles(config.getProtoConfig());
    }

    /**
     * 获取指定的proto文件
     * */
    private static List<String> loadProtoFiles(ProtoConfig protoConfig) {
       // Path srcPath = protoConfig.getSrcPath();
        //System.out.println(srcPath.toAbsolutePath());

        return null;
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
}






















