package cn.kurisu9.utils;

/**
 * @author kurisu9
 * @description 字符串扩展工具包
 * @date 2018/10/5 13:31
 **/
public class StringExpandUtils {

    /**
     * 首字母大写
     * */
    public static String toUpperFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);

        return String.valueOf(chars);
    }

    /**
     * 首字母小写
     * */
    public static String toLowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);

        return String.valueOf(chars);
    }
}
