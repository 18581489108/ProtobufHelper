package cn.kurisu9.utils;

import cn.kurisu9.GlobalSetting;
import cn.kurisu9.data.Result;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

/**
 * @author zhaoxin_m
 * @description freemarker工具类
 * @date 2018/10/5 14:04
 **/
public class FreemarkerUtil {
    private static final FreemarkerUtil INSTANCE = new FreemarkerUtil();

    private FreemarkerUtil() {}

    public static FreemarkerUtil getInstance() {
        return INSTANCE;
    }

    private Configuration config;

    private boolean isInit = false;

    public Result init(Path templateDir) {
        if (isInit) {
            return Result.DEFAULT_SUCCESS;
        }

        config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        try {
            config.setDirectoryForTemplateLoading(templateDir.toFile());
            config.setDefaultEncoding(GlobalSetting.FILE_ENCODING.displayName());
            isInit = true;
        } catch (IOException e) {
            return Result.forFailed(e.getMessage());
        }

        return Result.DEFAULT_SUCCESS;
    }

    /*
    public Result processTemplate(String templateName, Object dataModel, Writer out) {
        try {
            Template template = config.getTemplate(templateName);
            template.process(dataModel, out);

            return Result.DEFAULT_SUCCESS;
        } catch (IOException | TemplateException e) {
            return Result.forFailed(e.getMessage());
        }
    }
    */

    public Result processTemplate(String templateName, Object dataModel, Path filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()));
            Template template = config.getTemplate(templateName);
            template.process(dataModel, writer);
            writer.close();

            return Result.DEFAULT_SUCCESS;
        } catch (IOException | TemplateException e) {
            return Result.forFailed(e.getMessage());
        }
    }
}
