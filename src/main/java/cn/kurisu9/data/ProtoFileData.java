package cn.kurisu9.data;

import com.google.protobuf.DescriptorProtos;

/**
 * @author kurisu9
 * @description Proto文件数据
 * @date 2018/10/3 20:24
 **/
public class ProtoFileData {

    /**
     * 文件名
     * */
    private String fileName;

    /**
     * 文件选项
     * */
    private DescriptorProtos.FileOptions fileOptions;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DescriptorProtos.FileOptions getFileOptions() {
        return fileOptions;
    }

    public void setFileOptions(DescriptorProtos.FileOptions fileOptions) {
        this.fileOptions = fileOptions;
    }
}
