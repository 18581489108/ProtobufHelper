package cn.kurisu9.data;

import com.google.protobuf.DescriptorProtos;

import java.util.List;

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

    /**
     * 该proto文件中所有的message定义
     * */
    private List<ProtoMessageData> messages;

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

    public List<ProtoMessageData> getMessages() {
        return messages;
    }

    public void setMessages(List<ProtoMessageData> messages) {
        this.messages = messages;
    }
}
