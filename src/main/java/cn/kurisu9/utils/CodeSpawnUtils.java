package cn.kurisu9.utils;

/**
 * @author kurisu9
 * @description 代码生成工具
 * @date 2018/10/4 21:01
 **/
public class CodeSpawnUtils {
    public static StringBuilder appendSpace(StringBuilder sb) {
        return appendSpace(sb, 1);
    }

    public static StringBuilder appendSpace(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append(' ');
        }

        return sb;
    }

    public static StringBuilder appendWarp(StringBuilder sb) {
        return appendWarp(sb, 1);
    }

    public static StringBuilder appendWarp(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append('\n');
        }

        return sb;
    }

    public static StringBuilder appendTab(StringBuilder sb) {
        return appendTab(sb, 1);
    }

    public static StringBuilder appendTab(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            appendSpace(sb, 4);
        }

        return sb;
    }

    public static StringBuilder appendLineEnd(StringBuilder sb) {
        return sb.append(';');
    }


    public static StringBuilder appendJavaImport(StringBuilder code, String fullClassName) {
        code.append("import");
        appendSpace(code);

        code.append(fullClassName);
        appendLineEnd(code);
        appendWarp(code);

        return code;
    }

    /**
     * 添加多个字符串，每个字符串中间用空格隔开
     * */
    public static StringBuilder appendStringsWithSpace(StringBuilder sb, String... strings) {
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                appendSpace(sb);
            }
        }

        return sb;
    }
}













