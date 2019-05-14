package com.zyb.traffic

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoders, SaveMode, SparkSession}

object LogPrepareseETL {
  def main(args: Array[String]): Unit = {

    val conf=new SparkConf().setAppName("LogPrepareseETL")
    if(!conf.contains("spark.master")){
      conf.setMaster("local")
    }

    val spark=SparkSession.builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()

    val inPath=conf.get("spark.traffic.rawdata.inPath",
      "file:///F:\\zhangyingbiao\\ProjectDome\\imitation\\web-traffic-combat\\backend\\src\\main\\resources\\web-log\\*")
    val savedTable=if(args.length>=1) args(0) else "traffic.rawlog"

    //在指定default-fs为hdfs时(core-site.xml),读取本地文件使用file://协议
    val rawLine=spark.sparkContext.textFile(inPath,3)

    val parsedLog=rawLine.flatMap(l=>Option(LogPreparser.parse(l)))

    spark.createDataset(parsedLog)(Encoders.bean(classOf[LogPreparser]))
      .repartition(3)
      .write.partitionBy("year","month","day")
      .mode(SaveMode.Overwrite)
      .saveAsTable(savedTable)

    spark.stop()

  }


}
