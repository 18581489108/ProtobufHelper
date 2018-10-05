package cn.kurisu9.config;

/**
 * @author kurisu9
 * @description 配置文件定义
 * @date 2018/9/30 17:04
 **/
public class Config {
    /**
     * 编译proto文件的工具路径
     * */
    private String protocFile;

    /**
     * 代码模板所在目录
     * */
    private String templateDir;

    /**
     * 输出的临时目录
     * */
    private String tempRootDir;

    /**
     * 描述文件的输出路径，在输出的临时目录下
     * */
    private String descOutPath;

    /**
     * proto文件相关的配置
     * */
    private ProtoConfig protoConfig;

    /**
     * 输出配置
     * */
    private OutConfig[] outConfigs;

    public String getProtocFile() {
        return protocFile;
    }

    public void setProtocFile(String protocFile) {
        this.protocFile = protocFile;
    }

    public String getTempRootDir() {
        return tempRootDir;
    }

    public void setTempRootDir(String tempRootDir) {
        this.tempRootDir = tempRootDir;
    }

    public String getDescOutPath() {
        return descOutPath;
    }

    public void setDescOutPath(String descOutPath) {
        this.descOutPath = descOutPath;
    }

    public ProtoConfig getProtoConfig() {
        return protoConfig;
    }

    public void setProtoConfig(ProtoConfig protoConfig) {
        this.protoConfig = protoConfig;
    }

    public OutConfig[] getOutConfigs() {
        return outConfigs;
    }

    public void setOutConfigs(OutConfig[] outConfigs) {
        this.outConfigs = outConfigs;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }
}