package cn.kurisu9.spawner;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.GlobalSetting;
import cn.kurisu9.config.OutConfig;
import cn.kurisu9.data.KeyValue;
import cn.kurisu9.data.ProtoFileData;
import cn.kurisu9.data.ProtoMessageData;
import cn.kurisu9.data.Result;
import cn.kurisu9.data.javaa.PacketIdFileInfo;
import cn.kurisu9.utils.FreemarkerUtil;
import cn.kurisu9.utils.StringExpandUtils;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
import com.google.protobuf.DescriptorProtos;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String protocFile = context.getProtocFile();
        String srcPath = context.getProtoSrcDir().toAbsolutePath().toString();
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
        PacketIdFileInfo packetIdFileInfo = new PacketIdFileInfo();
        packetIdFileInfo.setPackageName(getIdClassPackage(outConfig.getIdFilePath()));
        packetIdFileInfo.setClassName(getIdClassName(outConfig.getIdFilePath()));

        List<ProtoFileData> fileDataList = context.getIdFileDataList();

        List<KeyValue<String, List<ProtoMessageData>>> allMessages = new ArrayList<>();
        Set<String> imports = new HashSet<>();
        for (ProtoFileData fileData : fileDataList) {
            DescriptorProtos.FileOptions fileOptions = fileData.getFileOptions();
            
            String javaOuterClassname = getJavaOuterClassname(fileData.getFileName(), fileOptions);
            String fullClassName = fileOptions.getJavaPackage() + "." + javaOuterClassname;

            imports.add(fullClassName);

            allMessages.add(KeyValue.of(javaOuterClassname, fileData.getMessages()));

        }

        packetIdFileInfo.setImportClasses(imports);
        packetIdFileInfo.setAllMessages(allMessages);

        Result result = FreemarkerUtil.getInstance().processTemplate(outConfig.getIdFileTemplate(), packetIdFileInfo, idFilePath);
        if (result.isFailed()) {
            return result;
        }

        return Result.DEFAULT_SUCCESS;
    }

    /**
     * 获取id类的包名
     * */
    private String getIdClassPackage(String idFilePath) {
        StringBuilder packageName = new StringBuilder();
        Path path = Paths.get(idFilePath);

        // 不需要最后的类名
        int nameCount = path.getNameCount() - 1;
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

    /**
     * 获取proto文件生成时的外部类名称
     * */
    private String getJavaOuterClassname(String fileName, DescriptorProtos.FileOptions fileOptions) {
        if (!StringUtils.isEmpty(fileOptions.getJavaOuterClassname())) {
            return fileOptions.getJavaOuterClassname();
        }
        return StringExpandUtils.toUpperFirst(FilenameUtils.getBaseName(fileName));
    }
}





















