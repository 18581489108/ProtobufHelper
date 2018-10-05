package cn.kurisu9.desc;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.config.IdFileConfig;
import cn.kurisu9.data.ProtoFileData;
import cn.kurisu9.data.ProtoMessageData;
import cn.kurisu9.data.Result;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
import com.google.protobuf.DescriptorProtos;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kurisu9
 * @description 描述文件解析
 * @date 2018/10/2 18:56
 **/
public class DescFileParser {
    private GlobalContext context;


    public DescFileParser(GlobalContext context) {
        this.context = context;
    }

    public Result parse() {
        Result result = spawnDescFiles();
        if (result.isFailed()) {
            return result;
        }

        result = parseDecFiles();
        if (result.isFailed()) {
            return result;
        }

        return Result.DEFAULT_SUCCESS;
    }

    /**
     * 生成描述文件
     * */
    private Result spawnDescFiles() {
        String protocFile = context.getProtocFile().toAbsolutePath().toString();
        String protoSrcPath = context.getProtoSrcDir().toAbsolutePath().toString();

        Path descOutPath = context.getDescOutPath();
        // 判断描述文件的父目录目录是否存在，不存在则创建
        Path descParentPath = descOutPath.getParent();
        if (!Files.exists(descParentPath)) {
            try {
                Files.createDirectories(descParentPath);
            } catch (IOException e) {
                return Result.forFailed(e.getMessage());
            }
        }

        Command command = new Command(protocFile);
        command.addParam("-I=" + protoSrcPath);
        command.addParam("--descriptor_set_out=" + descOutPath.toAbsolutePath().toString());

        IdFileConfig[] idFileConfigs = context.getConfig().getProtoConfig().getIdFileConfigs();
        for (IdFileConfig idFileConfig : idFileConfigs) {
            command.addParam(idFileConfig.getTargetFile());
        }

        ExecResult result = ProcessUtils.exec(command);

        if (!result.isSuccess()) {
            return Result.forFailed(result.getOut());
        }

        return Result.DEFAULT_SUCCESS;
    }

    /**
     * 解析描述文件
     * */
    private Result parseDecFiles() {
        try {
            DescriptorProtos.FileDescriptorSet fdSet = DescriptorProtos.FileDescriptorSet
                    .parseFrom(Files.newInputStream(context.getDescOutPath().toAbsolutePath(), StandardOpenOption.READ));

            List<ProtoFileData> protoFileDataList = new ArrayList<>();
            for (DescriptorProtos.FileDescriptorProto fileDescriptorProto : fdSet.getFileList()) {
                ProtoFileData fileData = new ProtoFileData();
                String fileName = fileDescriptorProto.getName();
                fileData.setFileName(fileName);
                fileData.setFileOptions(fileDescriptorProto.getOptions());

                short msgId = findInitId(fileName);
                if (msgId < 0) {
                    return Result.forFailed(fileName + " don't set initId");
                }
                List<DescriptorProtos.DescriptorProto> messages = fileDescriptorProto.getMessageTypeList();
                List<ProtoMessageData> messageDataList = new ArrayList<>(messages.size());
                for (DescriptorProtos.DescriptorProto msg : messages) {
                    ProtoMessageData messageData = new ProtoMessageData();
                    messageData.setName(msg.getName());
                    messageData.setId(msgId++);
                    // TODO 如何解析message上的注释
                    //messageData.setComment(msg.);

                    messageDataList.add(messageData);
                }
                fileData.setMessages(messageDataList);

                protoFileDataList.add(fileData);
            }

            context.setIdFileDataList(protoFileDataList);
        } catch (IOException e) {
            return Result.forFailed(e.getMessage());
        }


        return Result.DEFAULT_SUCCESS;
    }

    /**
     * 根据proto文件名找到对应的起始id
     * */
    private short findInitId(String fileName) {
        IdFileConfig[] idFileConfigs = context.getConfig().getProtoConfig().getIdFileConfigs();
        for (IdFileConfig idFileConfig : idFileConfigs) {
            if (idFileConfig.getTargetFile().equals(fileName)) {
                return idFileConfig.getInitId();
            }
        }

        return -1;
    }
}


























