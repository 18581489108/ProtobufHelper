package cn.kurisu9.data.javaa;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhaoxin_m
 * @description 类文件的基本信息
 * @date 2018/10/5 14:42
 **/
public class BaseClassFileInfo extends CodeFileInfo {
    /**
     * import信息
     * */
    private Set<String> importClasses = new HashSet<>();

    /**
     * 类名
     * */
    private String className;

    public Set<String> getImportClasses() {
        return importClasses;
    }

    public void setImportClasses(Set<String> importClasses) {
        this.importClasses = importClasses;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
