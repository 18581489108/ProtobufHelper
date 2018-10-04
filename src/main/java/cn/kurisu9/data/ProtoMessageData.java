package cn.kurisu9.data;

/**
 * @author kurisu9
 * @description 协议数据
 * @date 2018/10/4 11:19
 **/
public class ProtoMessageData {
    /**
     * 名称
     * */
    private String name;

    /**
     * 注释
     * */
    private String comment;

    /**
     * 此消息的id
     * */
    private short id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }
}
