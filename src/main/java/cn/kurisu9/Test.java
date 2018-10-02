package cn.kurisu9;

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
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\zhaoxin_m\\Desktop\\protobuf\\test\\protoc3.5.1.exe");
        System.out.println(Files.isDirectory(path));
    }
}
