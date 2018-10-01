package cn.kurisu9;

import cn.kurisu9.config.Config;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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

        System.out.println(config.getProtocFile());
        spawnDescFile(config);
    }

    /**
     * 生成描述文件
     * */
    private static void spawnDescFile(Config config) {
        String cmd = ".\\tools\\protoc3.5.1.exe -I=C:\\Users\\zhaoxin_m\\Desktop\\protobuf\\test\\ --descriptor_set_out=./build/desc/example.desc Example.proto";

        Command command = new Command(".\\tools\\protoc3.5.1.exe");
        command.addParam("-I=C:\\Users\\zhaoxin_m\\Desktop\\protobuf\\test\\");
        command.addParam("--descriptor_set_out=./build/desc/example.desc");
        command.addParam("Example.proto");

        ExecResult result = ProcessUtils.exec(command);

        System.out.println(result.isSuccess());
        System.out.println(result.getOut());
    }
}






















