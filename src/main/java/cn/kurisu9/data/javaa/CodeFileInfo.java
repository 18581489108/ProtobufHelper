package cn.kurisu9.data.javaa;

/**
 * @author zhaoxin_m
 * @description java代码文件的信息
 * @date 2018/10/5 14:39
 **/
public class CodeFileInfo {
    /**
     * 包名
     * */
    protected String packageName;

    /**
     * 文件名
     * */
    protected String fileName;

    /**
     * 使用的模板名称
     * */
    protected String templateName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
