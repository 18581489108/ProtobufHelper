package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.data.Result;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author kurisu9
 * @description 定义一些子类都会使用的方法
 * @date 2018/10/2 13:55
 **/
public abstract class AbstractSpawner implements Spawner {
    protected static final Result DEFAULT_SUCCESS_RESULT = Result.DEFAULT_SUCCESS;

    /**
     * 支持的类型
     * */
    private String supportsType;

    public AbstractSpawner(String supportsType) {
        this.supportsType = supportsType;
    }

    public boolean supports(String type) {
        if (StringUtils.isEmpty(type)) {
            return false;
        }
        return supportsType.equals(type.toLowerCase());
    }

    public boolean supports(OutConfig outConfig) {
        return this.supports(outConfig.getType());
    }

    public String getSupportsType() {
        return supportsType;
    }

    /**
     * 生成代码文件到临时目录
     *
     * @param context
     * @param outConfig
     */
    @Override
    public Result spawn(GlobalContext context, OutConfig outConfig) {
        if (!supports(outConfig)) {
            return new Result(false, "this spawner don't support " + outConfig.getType() + " type");
        }

        Path tempDir = context.getTempRootDir().resolve(outConfig.getTempDir()).toAbsolutePath();

        Result createTempPathResult = createTempPath(tempDir);
        if (createTempPathResult.isFailed()) {
            return createTempPathResult;
        }
        context.addTempDir(outConfig.getType(), tempDir);

        Result spawnFileResult = spawnFileToTempPath(context, outConfig, tempDir);
        if (spawnFileResult.isFailed()) {
            return spawnFileResult;
        }

        // 进行id文件的生成
        if (outConfig.getIdFileFlag()) {
            Path idFilePath = tempDir.resolve(outConfig.getIdFilePath()).toAbsolutePath();
            // 生成id文件的父目录
            Result createIdFileTempPathResult = createTempPath(idFilePath.getParent());
            if (createIdFileTempPathResult.isFailed()) {
                return createIdFileTempPathResult;
            }

            Result spawnIdFileResult = spawnIdFileToTempPath(context, outConfig, idFilePath);
            if (spawnIdFileResult.isFailed()) {
                return spawnIdFileResult;
            }
        }

        return DEFAULT_SUCCESS_RESULT;
    }

    /**
     * 生成代码文件到临时目录
     *
     * @param context 全局上下文
     * @param tempPath 代码文件的临时根目录
     * */
    protected abstract Result spawnFileToTempPath(GlobalContext context, OutConfig outConfig, Path tempPath);

    /**
     * 生成id文件到临时目录
     *
     * @param context 全局上下文
     * @param idFilePath id文件的临时路径
     * */
    protected abstract Result spawnIdFileToTempPath(GlobalContext context, OutConfig outConfig, Path idFilePath);

    /**
     * 创建临时目录，如果目录已存在，则直接返回true
     * 如果不存在则进行创建
     * */
    protected Result createTempPath(Path tempPath) {
        if (Files.notExists(tempPath)) {
            try {
                Files.createDirectories(tempPath);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.forFailed(e.getMessage());
            }
        }

        return DEFAULT_SUCCESS_RESULT;
    }
}
