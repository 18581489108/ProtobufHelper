package cn.kurisu9.desc;

import cn.kurisu9.GlobalContext;
import cn.kurisu9.data.ProtoFileData;
import cn.kurisu9.data.Result;
import cn.kurisu9.utils.process.Command;
import cn.kurisu9.utils.process.ExecResult;
import cn.kurisu9.utils.process.ProcessUtils;
import com.google.protobuf.DescriptorProtos;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static cn.kurisu9.GlobalSetting.DESC_EXTENSION;

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
        String protoSrcPath = context.getProtoSrcPath().toAbsolutePath().toString();

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
        String[] filesOfGenerateId = context.getConfig().getProtoConfig().getFilesOfGenerateId();
        for (String file : filesOfGenerateId) {
            command.addParam(file);
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

            // TODO 如何解析proto文件
            for (DescriptorProtos.FileDescriptorProto fileDescriptorProto : fdSet.getFileList()) {
                ProtoFileData data = new ProtoFileData();
                data.setFileName(fileDescriptorProto.getName());
                data.setFileOptions(fileDescriptorProto.getOptions());

                List<DescriptorProtos.DescriptorProto> messages = fileDescriptorProto.getMessageTypeList();

                for (DescriptorProtos.DescriptorProto msg : messages) {
                    System.out.println(msg.getName());
                }
                System.out.println();
            }
        } catch (IOException e) {
            return Result.forFailed(e.getMessage());
        }


        return Result.DEFAULT_SUCCESS;
    }
}


























