package cn.kurisu9;

import cn.kurisu9.config.Config;
import cn.kurisu9.data.ProtoFileData;

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
     * 模板所在目录
     * */
    private Path templateDir;

    /**
     * 临时输出的根目录
     * */
    private Path tempRootDir;

    /**
     * proto文件的源目录
     * */
    private Path protoSrcDir;

    /**
     * 描述文件输出路径
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
    private Map<String, Path> tempDirs = new HashMap<>();

    /**
     * 最终目录的映射表
     *
     * key: type 生成器的类型
     * value: Path 最终输出到的目录
     * */
    private Map<String, Path> finalDirs = new HashMap<>();

    /**
     * id文件相关数据
     * */
    private List<ProtoFileData> idFileDataList;

    public void addTempDir(String spawnerType, Path tempDir) {
        tempDirs.put(spawnerType.toLowerCase(), tempDir);
    }

    public Path removeTempDir(String spawnerType) {
        return tempDirs.remove(spawnerType);
    }

    public Path getTempDir(String spawnerType) {
        return tempDirs.get(spawnerType.toLowerCase());
    }

    public void addFinalDir(String spawnerType, Path tempDir) {
        finalDirs.put(spawnerType.toLowerCase(), tempDir);
    }

    public Path removeFinalDir(String spawnerType) {
        return finalDirs.remove(spawnerType);
    }

    public Path getFinalDir(String spawnerType) {
        return finalDirs.get(spawnerType.toLowerCase());
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

    public Path getTempRootDir() {
        return tempRootDir;
    }

    public void setTempRootDir(Path tempRootDir) {
        this.tempRootDir = tempRootDir;
    }

    public Path getProtoSrcDir() {
        return protoSrcDir;
    }

    public void setProtoSrcDir(Path protoSrcDir) {
        this.protoSrcDir = protoSrcDir;
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

    public List<ProtoFileData> getIdFileDataList() {
        return idFileDataList;
    }

    public void setIdFileDataList(List<ProtoFileData> idFileDataList) {
        this.idFileDataList = idFileDataList;
    }

    public Path getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(Path templateDir) {
        this.templateDir = templateDir;
    }

    //endregion

}
