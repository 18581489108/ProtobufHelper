# ProtobufHelper
基于Protobuf的工具链，用于自动生成proto的对应代码文件，并且生成一个额外的消息id文件，最后将生成的文件复制到目标目录。

## 环境要求
1. 目前仅在Windows环境进行过测试，其他操作系统不作保证
2. JDK 1.8
3. [google官方编译器 3.5版本及以上](https://github.com/protocolbuffers/protobuf/releases)
4. 如果需要编译成TypeScript，还需要安装node以及Protobuf.js

## 用法
1. 下载[ProtobufHelper](https://github.com/18581489108/ProtobufHelper/releases)
2. 解压到目标目录
3. 在配置中进行了一些测试配置，如果只想测试效果，直接```java -jar ProtobufHelper-1.0.jar```即可

## 配置说明
所有的运行配置都在```config.json```中，默认在jar包所在的同级目录，也可以在运行时指定其他路径下的配置文件，如```java -jar ProtobufHelper-1.0.jar xxx/config.json```

### 示例
```config.json```示例文件：
```js
{

  "protocFile": "./tools/protoc3.5.1.exe",

  "pbjsFile": "cmd /c pbjs",

  "pbtsFile": "cmd /c pbts",

  "templateDir": "./templates",

  "tempRootDir": "./build",

  "descOutPath": "desc/global.desc",

  "protoConfig": {
    "srcDir": "./src/",
    "includeFiles": ["Test1.proto", "Test2.proto"],
    "excludedFiles": [],
    "idFileConfigs": [
      {
        "targetFile": "Test1.proto",
        "initId": 1001
      },
      {
        "targetFile": "Test2.proto",
        "initId": 2001
      }
    ]
  },

  "outConfigs": [
    {
      "type": "java",
      "tempDir": "java",
      "finalDir": "./finalDir/java/",
      "idFileFlag": true,
      "idFilePath": "cn/kurisu9/example/PackId.java",
      "idFileTemplate": "JavaPacketId.ftl"
    },
    {
      "type": "typescript",
      "tempDir": "ts",
      "finalDir": "./finalDir/ts/",
      "idFileFlag": true,
      "idFilePath": "message/packId.ts",
      "idFileTemplate": "TypeScriptPacketId.ftl"
    }
  ]

}

```
### 1. protocFile
该字段指定了编译描述文件以及```java```代码时的编译工具，默认是使用```./tools/protoc3.5.1.exe```，可以指定其他路径下的编译工具，同样也可以使用全局命令

### 2. pbjsFile
该字段指定了编译成```js```代码时的编译工具，由于是使用的```protobuf.js```进行编译，所以这里通过npm进行全局安装后，使用了全局命令进行编译。如果不需要编译成```js```，那么这个字段可以忽略

### 3. pbtsFile
该字段指定了编译成```ts```声明代码时的编译工具，由于是使用的```protobuf.js```进行编译，所以这里通过npm进行全局安装后，使用了全局命令进行编译。如果不需要编译成```ts```，那么这个字段可以忽略

### 4. templateDir
生成id文件的模板根目录，默认目录是```./templates```，这个目录必须显式指定，即使不需要生成id文件

### 5. tempRootDir
生成代码的所在的临时目录，默认目录是```./build```，这个目录必须显式指定

### 6. descOutPath
描述文件生成的路径，该路径为相对路径，其父目录为```tempRootDir```，需要显式指定输出的描述文件的文件名

### 7. protoConfig
proto文件相关的配置，接下来具体描述每个字段的功能

#### 7.1 srcDir
需要解析的proto文件所在的目录，默认目录是```./src/```，这个目录必须显式指定

#### 7.2 includeFiles
显式指定了需要解析proto文件，如果这个字段不为空，那么在解析过程中仅会解析该字段指定的proto文件。如果这个字段为空，那么会解析目录下所有的proto文件

#### 7.3 excludedFiles
显式指定了不需要解析的proto文件，该字段仅在```includeFiles```为空时生效

#### 7.4 idFileConfigs
id文件相关配置，指定了哪些proto文件需要生成id以及id的初始值

### 8. outConfigs
输出相关配置，指定了输出的类型以及所在的目录，接下来具体描述每个字段的功能

#### 8.1 type
输出类型，目前仅支持```java```、```typescript```

#### 8.2 tempDir
生成代码所在的临时目录，该目录为相对路径，其父目录为```tempRootDir```，需要显式指定临时目录

#### 8.3 finalDir
生成代码结束后，将代码复制到的最终目录，这个目录取决于你的项目结构

#### 8.4 idFileFlag
是否生成id文件，默认值为```false```，当且仅当为```true```时，```idFilePath```和```idFileTemplate```才生效

#### 8.5 idFilePath
id文件生成的路径，该路径为相对路径，
* 在临时目录中，其父目录为```tempRootDir```
* 在最终目录中，其父目录为```finalDir```

同时需要注意，这个路径在不同类型代码生成时，具有一些不同的功能，
* 当```"type": "java"```时，路径的目录同样是生成java类所在的包名，文件名为类名，比如```"idFilePath": "cn/kurisu9/example/PackId.java"```，那么会自动生成如下的代码：
    ```java
    package cn.kurisu9.example;

    public class PackId {
        //...
    }

    ```
* 当```"type": "typescript"```时，文件名是导出的模块名，比如```"idFilePath": "message/packId.ts"```，那么会自动生成如下的代码：
    ```typescript
    export namespace packId {
        //...
    }
    ```

#### 8.6 idFileTemplate
id文件生成时使用的模板文件，其父目录为```templateDir```，不支持特殊指定某一路径下的模板文件




