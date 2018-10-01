package cn.kurisu9.utils.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxin_m
 * @description 命令
 * @date 2018/10/1 15:13
 **/
public class Command {
    /**
     * 命令名称
     * */
    private String cmd;

    /**
     * 运行参数
     * */
    private List<String> params = new ArrayList<>();

    public Command(String cmd) {
        this.cmd = cmd;
    }

    public Command(String cmd, List<String> params) {
        this.cmd = cmd;
        this.params = params;
    }

    /**
     * 转换为执行语句
     * */
    public String toExecStatement() {
        StringBuilder sb = new StringBuilder();

        sb.append(cmd);

        for (String param : params) {
            sb.append(' ');
            sb.append(param);
        }

        return sb.toString();
    }

    /**
     * 将命令和参数转换为一个list
     * */
    public List<String> toCmdList() {
        List<String> cmdList = new ArrayList<>(params.size() + 1);
        cmdList.add(cmd);
        cmdList.addAll(params);
        return cmdList;
    }

    public void addParam(String param) {
        params.add(param);
    }

    public boolean removeParam(String param) {
        return params.remove(param);
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
