package cn.kurisu9.utils.process;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author kurisu9
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
     * 将命令和参数转换为一个list
     * */
    public List<String> toCmdList() {
        List<String> cmdList = new ArrayList<>(params.size() + 3);

        addParamsToList(cmdList, cmd);

        for (String param : params) {
            addParamsToList(cmdList, param);
        }

        return cmdList;
    }

    private void addParamsToList(List<String> cmdList, String param) {
        StringTokenizer st = new StringTokenizer(param);
        while (st.hasMoreTokens()) {
            cmdList.add(st.nextToken());
        }
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
