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

    public static String GLOBAL_LINE_SEPARATOR = "\n";

    /**
     * 默认的配置文件的路径
     * */
    public static final String CONFIG_PATH = "./config.json";

}
