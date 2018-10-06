package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.data.Result;

import java.nio.file.Path;

/**
 * @author kurisu9
 * @description ts的proto代码生成
 * @date 2018/10/6 20:51
 **/
public class TypeScriptSpawner extends AbstractSpawner {

    public TypeScriptSpawner() {
        super(SpawnerConstants.TYPE_SCRIPT_TYPE);
    }

    /**
     * 生成代码文件到临时目录
     *
     * @param context   全局上下文
     * @param outConfig
     * @param tempPath  代码文件的临时根目录
     */
    @Override
    protected Result spawnFileToTempPath(GlobalContext context, OutConfig outConfig, Path tempPath) {
        return null;
    }

    /**
     * 生成id文件到临时目录
     *
     * @param context    全局上下文
     * @param outConfig
     * @param idFilePath id文件的临时路径
     */
    @Override
    protected Result spawnIdFileToTempPath(GlobalContext context, OutConfig outConfig, Path idFilePath) {
        return null;
    }
}















