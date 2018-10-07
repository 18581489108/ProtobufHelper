package cn.kurisu9.data.typescript;

import cn.kurisu9.data.KeyValue;
import cn.kurisu9.data.ProtoMessageData;

import java.util.List;

/**
 * @author kurisu9
 * @description 消息id文件的信息
 * @date 2018/10/5 14:45
 **/
public class PacketIdFileInfo {
    /**
     * 命名空间
     * */
    private String namespace;

    /**
     * 所有要生成id的消息
     *
     * Key: 消息所在的proto文件类名
     * Value: 该proto文件中所有的消息信息
     * */
    private List<KeyValue<String, List<ProtoMessageData>>> allMessages;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<KeyValue<String, List<ProtoMessageData>>> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<KeyValue<String, List<ProtoMessageData>>> allMessages) {
        this.allMessages = allMessages;
    }
}
