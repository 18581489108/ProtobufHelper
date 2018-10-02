package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.data.Result;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;

import java.nio.file.Path;
import java.util.List;

/**
 * @author zhaoxin_m
 * @description java代码生成器
 * @date 2018/10/2 15:26
 **/
public class JavaSpawner extends AbstractSpawner {
    public JavaSpawner() {
        super(SpawnerConstants.JAVA_TYPE);
    }

    /**
     * 生成代码文件到临时目录
     *
     * @param context  全局上下文
     * @param tempPath 代码文件的临时根目录
     */
    @Override
    protected Result spawnFileToTempPath(GlobalContext context, Path tempPath) {
        String protocFile = context.getProtocFile().toAbsolutePath().toString();
        String srcPath = context.getProtoSrcPath().toAbsolutePath().toString();
        String javaOutPath = tempPath.toString();

        List<String> protoFiles = context.getProtoFiles();

        for (String file : protoFiles) {
            Command command = new Command(protocFile);
            command.addParam("-I=" + srcPath);
            command.addParam("--java_out=" + javaOutPath);
            command.addParam(file);

            ExecResult result = ProcessUtils.exec(command);
            if (result.isSuccess()) {
                continue;
            }

            return Result.forFailed(result.getOut());
        }

        return DEFAULT_SUCCESS_RESULT;
    }

    /**
     * 生成id文件到临时目录
     *
     * @param context    全局上下文
     * @param idFilePath id文件的临时路径
     */
    @Override
    protected Result spawnIdFileToTempPath(GlobalContext context, Path idFilePath) {
        return null;
    }
}
