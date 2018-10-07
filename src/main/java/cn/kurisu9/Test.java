package cn.kurisu9;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author kurisu9
 * @description TODO
 * @date 2018/10/1 20:28
 **/
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*
        Path path = Paths.get("C:\\Users\\kurisu9\\Desktop\\protobuf\\test\\protoc3.5.1.exe");
        System.out.println(Files.isDirectory(path));
        */
        //System.out.println(FilenameUtils.separatorsToSystem("cn/kurisu9/example/PackId.java"));
        //FileUtils.
        /*
        Path path = Paths.get("cn/kurisu9/example/PackId.java");
        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.println(path.getName(i));
        }
        */
        //System.out.println(FilenameUtils.getBaseName("cn/kurisu9/example/PackId.java"));
        Runtime runtime = Runtime.getRuntime();

        Process process = runtime.exec("cmd /c pbjs -help");

        process.waitFor();

        System.out.println(process.exitValue());
    }
}
