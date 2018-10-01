package cn.kurisu9.utils.process;

import cn.kurisu9.GlobalSetting;

import java.io.*;

/**
 * @author kurisu9
 * @description 进程执行工具
 * @date 2018/10/1 15:08
 **/
public class ProcessUtils {
    private ProcessUtils() {}

    public static ExecResult exec(Command command) {
        ExecResult result = new ExecResult();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.toCmdList());
            Process process = processBuilder.start();

            // 读取错误信息
            InputStreamRunnable readErrorRunnable = new InputStreamRunnable(process.getErrorStream());
            Thread readErrorThread = new Thread(readErrorRunnable);
            readErrorThread.start();

            String out = getOutFromInputStream(process.getInputStream());

            process.waitFor();

            // 等待读取错误信息线程结束
            readErrorThread.join();
            if (process.exitValue() == 1) {
                result.setSuccess(false);
                result.setOut(readErrorRunnable.getOut());
            } else {
                result.setSuccess(true);
                result.setOut(out);
            }

        } catch (InterruptedException | IOException e) {
            result.setSuccess(false);
            result.setOut(e.getMessage());
        }

        return result;
    }

    /**
     * 从输入流中获取输出
     * */
    private static String getOutFromInputStream(InputStream is) {
        StringBuilder out = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is), GlobalSetting.GLOBAL_ENCODING))) {
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(GlobalSetting.GLOBAL_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();
    }

    private static class InputStreamRunnable implements Runnable {
        private InputStream is;

        private String out;

        public InputStreamRunnable(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            out = ProcessUtils.getOutFromInputStream(is);
        }

        /**
         * 获取输出
         * */
        private String getOut() {
            return out;
        }
    }
}
