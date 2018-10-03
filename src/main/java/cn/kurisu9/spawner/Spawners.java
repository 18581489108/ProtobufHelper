package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.data.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kurisu9
 * @description 封装了现在已有的生成器，并提供对外统一接口
 * @date 2018/10/2 15:34
 **/
public class Spawners {
    private static Map<String, Spawner> spawnerMap = new HashMap<>();

    static {
        JavaSpawner javaSpawner = new JavaSpawner();
        spawnerMap.put(javaSpawner.getSupportsType(), javaSpawner);
    }


    /**
     * 根据输出配置进行代码文件的生成
     * */
    public static Result spawn(GlobalContext context, OutConfig outConfig) {
        Spawner spawner = spawnerMap.get(outConfig.getType().toLowerCase());
        if (spawner == null) {
            return new Result(false, "Don't support " + outConfig.getType() + " type");
        }

        return spawner.spawn(context, outConfig);
    }
}
