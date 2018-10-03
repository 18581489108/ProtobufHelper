package cn.kurisu9.data;

/**
 * @author kurisu9
 * @description 代码生成的结果
 * @date 2018/10/2 14:38
 **/
public class Result {
    public static final Result DEFAULT_SUCCESS = new Result(true);

    public static final Result DEFAULT_FAILED = new Result(false);

    /**
     * 是否成功
     * */
    private boolean isSuccess;

    /**
     * 消息
     * */
    private String message;

    public Result() {
    }

    public Result(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Result(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailed() {
        return !isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static Result forFailed(String message) {
        return new Result(false, message);
    }

    public static Result forSuccess(String message) {
        return new Result(true, message);
    }
}
