package cn.kurisu9.utils.process;

/**
 * @author kurisu9
 * @description 执行结果
 * @date 2018/10/1 15:10
 **/
public class ExecResult {
    /**
     * 是否成功
     * */
    private boolean isSuccess;

    /**
     * 执行输出
     * */
    private String out;

    public ExecResult() {
    }

    public ExecResult(boolean isSuccess, String out) {
        this.isSuccess = isSuccess;
        this.out = out;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
