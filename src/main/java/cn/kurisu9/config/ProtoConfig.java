package cn.kurisu9.config;

/**
 * @author kurisu9
 * @description proto文件的相关配置
 * @date 2018/9/30 17:53
 **/
public class ProtoConfig {
    /**
     * proto文件所在目录
     *
     * 需要解析的proto文件都在这个根目录下，不支持子目录
     * */
    private String srcPath;

    /**
     * 要生成代码的文件，如果为空，
     * 则认为目录下所有文件都会生成代码文件
     * */
    private String[] includeFiles;

    /**
     * 要排除的文件，如果在includeFiles中指定了，那么excludeFiles不再生效
     * */
    private String[] excludedFiles;

    /**
     * id文件配置
     * */
    private IdFileConfig[] idFileConfigs;

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String[] getIncludeFiles() {
        return includeFiles;
    }

    public void setIncludeFiles(String[] includeFiles) {
        this.includeFiles = includeFiles;
    }

    public String[] getExcludedFiles() {
        return excludedFiles;
    }

    public void setExcludedFiles(String[] excludedFiles) {
        this.excludedFiles = excludedFiles;
    }

    public IdFileConfig[] getIdFileConfigs() {
        return idFileConfigs;
    }

    public void setIdFileConfigs(IdFileConfig[] idFileConfigs) {
        this.idFileConfigs = idFileConfigs;
    }
}
