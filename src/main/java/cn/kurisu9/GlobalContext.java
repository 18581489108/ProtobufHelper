package cn.kurisu9;

import cn.kurisu9.config.Config;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kurisu9
 * @description 全局上下文
 * @date 2018/10/2 11:52
 **/
public class GlobalContext {
    /**
     * 读取的配置
     * */
    private Config config;

    /**
     * 编译proto文件的工具
     * */
    private Path protocFile;

    /**
     * 临时输出的根目录
     * */
    private Path tempRootPath;

    /**
     * proto文件的源路径
     * */
    private Path protoSrcPath;

    /**
     * 描述文件输出目录
     * */
    private Path descOutPath;

    /**
     * 要处理的proto文件列表，这里仅需文件名即可
     * */
    private List<String> protoFiles;

    /**
     * 临时目录的映射表
     *
     * key: type 生成器的类型
     * value: Path 生成器输出文件的临时目录
     * */
    private Map<String, Path> tempPaths = new HashMap<>();

    public void addTempPath(String key, Path tempPath) {
        tempPaths.put(key, tempPath);
    }

    public Path removeTempPath(String key) {
        return tempPaths.remove(key);
    }

    //region getter/setter
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Path getProtocFile() {
        return protocFile;
    }

    public void setProtocFile(Path protocFile) {
        this.protocFile = protocFile;
    }

    public Path getTempRootPath() {
        return tempRootPath;
    }

    public void setTempRootPath(Path tempRootPath) {
        this.tempRootPath = tempRootPath;
    }

    public Path getProtoSrcPath() {
        return protoSrcPath;
    }

    public void setProtoSrcPath(Path protoSrcPath) {
        this.protoSrcPath = protoSrcPath;
    }

    public List<String> getProtoFiles() {
        return protoFiles;
    }

    public void setProtoFiles(List<String> protoFiles) {
        this.protoFiles = protoFiles;
    }

    public Path getDescOutPath() {
        return descOutPath;
    }

    public void setDescOutPath(Path descOutPath) {
        this.descOutPath = descOutPath;
    }

    //endregion

}
