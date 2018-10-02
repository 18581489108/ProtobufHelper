package cn.kurisu9;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author kurisu9
 * @description 全局的一些设置
 * @date 2018/10/1 14:43
 **/
public class GlobalSetting {
    /**
     * 文件编码
     * */
    public static Charset FILE_ENCODING = StandardCharsets.UTF_8;

    /**
     * 全局编码
     * */
    public static Charset GLOBAL_ENCODING = StandardCharsets.UTF_8;

    /**
     * 全局的换行符
     * */
    public static String GLOBAL_LINE_SEPARATOR = "\n";

    /**
     * 默认的配置文件的路径
     * */
    public static final String CONFIG_PATH = "./config.json";

    /**
     * proto文件的扩展名
     * */
    public static final String PROTO_EXTENSION = "proto";

    /**
     * proto文件的描述文件的扩展名
     * */
    public static final String DESC_EXTENSION = "desc";

}
