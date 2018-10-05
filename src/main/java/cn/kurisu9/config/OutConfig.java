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
    private String tempDir;

    /**
     * 输出的最终目录
     * */
    private String finalDir;

    /**
     * 是否生成id文件
     * */
    private boolean idFileFlag;

    /**
     * id文件的路径
     *
     * 与输出的代码文件在同一个根目录下
     * */
    private String idFilePath;

    /**
     * id文件的模板
     * */
    private String idFileTemplate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public String getFinalDir() {
        return finalDir;
    }

    public void setFinalDir(String finalDir) {
        this.finalDir = finalDir;
    }

    public boolean getIdFileFlag() {
        return idFileFlag;
    }

    public void setIdFileFlag(boolean idFileFlag) {
        this.idFileFlag = idFileFlag;
    }

    public String getIdFilePath() {
        return idFilePath;
    }

    public void setIdFilePath(String idFilePath) {
        this.idFilePath = idFilePath;
    }

    public String getIdFileTemplate() {
        return idFileTemplate;
    }

    public void setIdFileTemplate(String idFileTemplate) {
        this.idFileTemplate = idFileTemplate;
    }
}
