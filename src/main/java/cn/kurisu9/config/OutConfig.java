package cn.kurisu9.config;

/**
 * @author kurisu9
 * @description 输出文件相关配置
 * @date 2018/9/30 17:05
 **/
public class OutConfig {
    /**
     * 输出的目标类型
     * */
    private String type;

    /**
     * 输出的临时目录
     * */
    private String tempPath;

    /**
     * 输出的最终目录
     * */
    private String finalPath;

    /**
     * 在输出前是否清除最终目录
     * */
    private String clearFlag;

    /**
     * 是否生成id文件
     * */
    private String idFileFlag;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getFinalPath() {
        return finalPath;
    }

    public void setFinalPath(String finalPath) {
        this.finalPath = finalPath;
    }

    public String getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }

    public String getIdFileFlag() {
        return idFileFlag;
    }

    public void setIdFileFlag(String idFileFlag) {
        this.idFileFlag = idFileFlag;
    }
}
