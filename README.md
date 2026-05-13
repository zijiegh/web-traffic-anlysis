# Web Traffic Analysis Project

## 项目概述

这是一个基于 Apache Spark 构建的 **Web 流量日志分析 ETL 系统**，用于解析和存储 Web 服务器访问日志，为后续的流量分析、用户行为分析提供数据基础。

## 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Java | 1.8 | 日志解析核心逻辑 |
| Scala | 2.11.8 | Spark 作业开发 |
| Apache Spark | 2.2.0 | 分布式数据处理 |
| Apache Hive | - | 数据仓库存储 |
| Maven | - | 项目构建管理 |

## 项目结构

```
/workspace/
├── .idea/                    # IntelliJ IDEA 配置
├── backend/                  # 后端代码
│   ├── src/main/java/        # Java 源码
│   │   └── com/zyb/traffic/LogPreparser.java
│   ├── src/main/scala/       # Scala 源码
│   │   └── com/zyb/traffic/LogPrepareseETL.scala
│   ├── src/main/resources/   # 资源文件
│   │   ├── web-log/          # 日志数据文件
│   │   ├── core-site.xml     # Hadoop 配置
│   │   └── hive-site.xml     # Hive 配置
│   └── pom.xml               # Maven 依赖管理
└── README.md
```

## 核心组件

### 1. LogPreparser.java

日志解析器，负责解析单行 Web 服务器日志，提取以下字段：

- **ServTime** - 服务时间
- **serverIp** - 服务器 IP
- **method** - HTTP 请求方法（GET/POST）
- **uriStem** - 请求 URI 路径
- **queryString** - 查询字符串
- **serverPort** - 服务器端口
- **clientIp** - 客户端 IP
- **userAgent** - 用户代理（浏览器信息）
- **profileId** - 用户配置 ID
- **command** - 命令类型（从查询字符串提取）
- **year/month/day** - 日期分区字段

### 2. LogPrepareseETL.scala

Spark ETL 作业，执行以下流程：

1. 初始化 SparkSession（支持 Hive）
2. 读取原始日志文件（支持本地文件或 HDFS）
3. 使用 `LogPreparser.parse()` 解析日志行
4. 将解析结果转换为 DataFrame
5. 按 `year/month/day` 分区写入 Hive 表

## 数据流程

```
原始日志文件 → Spark RDD → 解析过滤 → DataFrame → Hive表分区存储
```

## 运行方式

项目通过 Maven 构建，Spark 作业支持本地模式和集群模式运行：

```bash
# 构建项目
mvn clean package -DskipTests

# 运行 Spark ETL 作业
spark-submit \
  --class com.zyb.traffic.LogPrepareseETL \
  --master local[*] \
  target/backend-1.0-SNAPSHOT.jar
```

### 配置参数

| 参数 | 默认值 | 说明 |
|------|--------|------|
| spark.traffic.rawdata.inPath | file:///path/to/web-log/* | 输入日志文件路径 |
| spark.master | local | Spark 运行模式 |

## 输出

解析后的日志数据按日期分区存储到 Hive 表 `traffic.rawlog`，支持按年份、月份、日期进行分区查询。