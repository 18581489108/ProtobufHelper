package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.GlobalSetting;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.data.Result;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static cn.kurisu9.utils.CodeSpawnUtils.*;

/**
 * @author kurisu9
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
    protected Result spawnFileToTempPath(GlobalContext context, OutConfig outConfig, Path tempPath) {
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
    protected Result spawnIdFileToTempPath(GlobalContext context, OutConfig outConfig, Path idFilePath) {
        String codeContent = createJavaCode(context, outConfig, idFilePath);
        try {
            FileUtils.write(idFilePath.toFile(), codeContent, GlobalSetting.FILE_ENCODING);
        } catch (IOException e) {
            return Result.forFailed(e.getMessage());
        }

        return Result.DEFAULT_SUCCESS;
    }

    /**
     * 获取id类的包名
     * */
    private String getIdClassPackage(String idFilePath) {
        StringBuilder packageName = new StringBuilder();
        Path path = Paths.get(idFilePath);

        int nameCount = path.getNameCount();
        for (int i = 0; i < nameCount; i++) {
            packageName.append(path.getName(i));
            if (i < nameCount - 1) {
                packageName.append(".");
            }
        }
        return packageName.toString();
    }

    private String getIdClassName(String idFilePath) {
        return FilenameUtils.getBaseName(idFilePath);
    }

    private String createJavaCode(GlobalContext context, OutConfig outConfig, Path idFilePath) {
        StringBuilder code = new StringBuilder();

        // 包名
        code.append("package");
        appendSpace(code);
        code.append(getIdClassPackage(outConfig.getIdFilePath()));
        appendLineEnd(code);

        // 引入message所在的包


        return code.toString();
    }


}





















