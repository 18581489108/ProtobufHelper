package cn.kurisu9.utils;

import cn.kurisu9.exception.PathNotDirectoryException;
import cn.kurisu9.exception.PathNotExistsException;
import cn.kurisu9.exception.PathNotFileException;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author kurisu9
 * @description 文件路径工具类
 * @date 2018/10/2 14:24
 **/
public class FilePathUtils {
    /**
     * 检测路径是否存在
     *
     * 不存在直接抛出异常
     *
     * @param label 标签，表示是检测哪部分的路径
     * @param path 路径
     * */
    public static void checkPath(String label, Path path) {
        if (!Files.exists(path)) {
            throw new PathNotExistsException(label + " not exists, path: " + path.toString() + ", absolutePath: " + path.toAbsolutePath().toString());
        }
    }

    /**
     * 检测目录路径是否存在
     *
     * 不存在直接抛出异常
     *
     * @param label 标签，表示是检测哪部分的路径
     * @param path 路径
     * */
    public static void checkDirectoryPath(String label, Path path) {
        checkPath(label, path);

        if (!Files.isDirectory(path)) {
            throw new PathNotDirectoryException(label + " is not a directory, path: " + path.toString() + ", absolutePath: " + path.toAbsolutePath().toString());
        }
    }

    /**
     * 检测文件路径是否存在
     *
     * 不存在直接抛出异常
     *
     * @param label 标签，表示是检测哪部分的路径
     * @param path 路径
     * */
    public static void checkFilePath(String label, Path path) {
        checkPath(label, path);

        if (Files.isDirectory(path)) {
            throw new PathNotFileException(label + " is not a file, path: " + path.toString() + ", absolutePath: " + path.toAbsolutePath().toString());
        }
    }
}
