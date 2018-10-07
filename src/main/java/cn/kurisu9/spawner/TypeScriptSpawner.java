package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.data.KeyValue;
import cn.kurisu9.data.ProtoFileData;
import cn.kurisu9.data.ProtoMessageData;
import cn.kurisu9.data.Result;
import cn.kurisu9.data.typescript.PacketIdFileInfo;
import cn.kurisu9.utils.FreemarkerUtil;
import cn.kurisu9.utils.StringExpandUtils;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
import com.google.protobuf.DescriptorProtos;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kurisu9
 * @description ts的proto代码生成
 * @date 2018/10/6 20:51
 *
 * TS的代码生成依赖的是protobuf.js，因此需要安装protobuf.js
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
        String pbjsFile = context.getPbjsFile();
        String pbtsFile = context.getPbtsFile();

        List<String> protoFiles = context.getProtoFiles();

        for (String file : protoFiles) {
            // TODO js的生成目前仅支持生成到一个目录中
            // TODO 等待proto的更新对js的包名支持

            String protoPath = context.getProtoSrcDir().resolve(file).toAbsolutePath().toString();
            String outFileName = StringExpandUtils.toLowerFirst(FilenameUtils.getBaseName(file));

            // 先生成js
            String jsOutFile = outFileName + ".js";
            String jsOutPath = tempPath.resolve(jsOutFile).toAbsolutePath().toString();

            Result result;
            if ((result = spawnJs(pbjsFile, protoPath, jsOutPath)).isFailed()) {
                return result;
            }

            // 再生成ts声明文件
            String tsOutFile = outFileName + ".d.ts";
            String tsOutPath = tempPath.resolve(tsOutFile).toAbsolutePath().toString();

            if ((result = spawnTs(pbtsFile, jsOutPath, tsOutPath)).isFailed()) {
                return result;
            }
        }

        return Result.DEFAULT_SUCCESS;
    }

    private Result spawnJs(String pbjsFile, String protoPath, String jsOutPath) {
        Command command = new Command(pbjsFile);
        command.addParam("-t");
        command.addParam("static-module");
        command.addParam("-w");
        command.addParam("commonjs");
        command.addParam("-o");
        command.addParam(jsOutPath);
        command.addParam(protoPath);

        ExecResult result = ProcessUtils.exec(command);

        if (result.isSuccess()) {
            return Result.DEFAULT_SUCCESS;
        }

        return Result.forFailed(result.getOut());
    }

    private Result spawnTs(String pbtsFile, String jsOutPath, String tsOutPath) {
        Command command = new Command(pbtsFile);
        command.addParam("-o");
        command.addParam(tsOutPath);
        command.addParam(jsOutPath);

        ExecResult result = ProcessUtils.exec(command);

        if (result.isSuccess()) {
            return Result.DEFAULT_SUCCESS;
        }

        return Result.forFailed(result.getOut());
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
        PacketIdFileInfo packetIdFileInfo = new PacketIdFileInfo();
        packetIdFileInfo.setNamespace(getNamespace(outConfig.getIdFilePath()));

        List<ProtoFileData> fileDataList = context.getIdFileDataList();

        List<KeyValue<String, List<ProtoMessageData>>> allMessages = new ArrayList<>();
        for (ProtoFileData fileData : fileDataList) {
            allMessages.add(KeyValue.of(FilenameUtils.getBaseName(fileData.getFileName()), fileData.getMessages()));
        }

        packetIdFileInfo.setAllMessages(allMessages);

        Result result = FreemarkerUtil.getInstance().processTemplate(outConfig.getIdFileTemplate(), packetIdFileInfo, idFilePath);
        if (result.isFailed()) {
            return result;
        }

        return Result.DEFAULT_SUCCESS;
    }

    /**
     * 以文件名作为命名空间
     * */
    private String getNamespace(String idFilePath) {
        return FilenameUtils.getBaseName(idFilePath);
    }
}






























