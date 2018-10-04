package cn.kurisu9.config;

/**
 * @author zhaoxin_m
 * @description id文件配置
 * @date 2018/10/4 11:30
 **/
public class IdFileConfig {
    /**
     * 要生成id文件的目标文件
     * */
    private String targetFile;

    /**
     * id文件的起始id
     * */
    private short initId;

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public short getInitId() {
        return initId;
    }

    public void setInitId(short initId) {
        this.initId = initId;
    }
}
