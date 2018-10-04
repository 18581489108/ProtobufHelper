package cn.kurisu9.utils;

/**
 * @author kurisu9
 * @description 代码生成工具
 * @date 2018/10/4 21:01
 **/
public class CodeSpawnUtils {
    public static void appendSpace(StringBuilder sb) {
        appendSpace(sb, 1);
    }

    public static void appendSpace(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append(' ');
        }
    }

    public static void appendWarp(StringBuilder sb) {
        appendWarp(sb, 1);
    }

    public static void appendWarp(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append('\n');
        }
    }

    public static void appendTab(StringBuilder sb) {
        appendTab(sb, 1);
    }

    public static void appendTab(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            appendSpace(sb, 4);
        }
    }

    public static void appendLineEnd(StringBuilder sb) {
        sb.append(';');
    }

}