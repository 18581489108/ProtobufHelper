package cn.kurisu9.config;

/**
 * @author kurisu9
 * @description 配置文件定义
 * @date 2018/9/30 17:04
 **/
public class Config {
    /**
     * 输出的临时目录
     * */
    private String tempRootPath;

    /**
     * 描述文件的输出目录，在输出的临时目录下
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
}
