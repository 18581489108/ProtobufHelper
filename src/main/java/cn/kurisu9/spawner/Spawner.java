package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.data.Result;

/**
 * @author kurisu9
 * @description 生成目标代码的顶层接口
 * @date 2018/10/2 13:44
 **/
public interface Spawner {
    /**
     * 生成代码文件到临时目录
     * */
    Result spawn(GlobalContext context, OutConfig outConfig);

}
