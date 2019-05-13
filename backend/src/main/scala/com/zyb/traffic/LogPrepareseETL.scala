package com.zyb.traffic

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoders, SaveMode, SparkSession}

object LogPrepareseETL {
  def main(args: Array[String]): Unit = {

    val spark=SparkSession.builder()
      .appName("LogPrepareseETL")
      .master("local[3]")
      .enableHiveSupport()
      .getOrCreate()

    //在指定default-fs为hdfs时(core-site.xml),读取本地文件使用file://协议
    val rawLine=spark.sparkContext.textFile("file:///F:\\zhangyingbiao\\ProjectDome\\imitation\\web-traffic-anlysis\\backend\\src\\main\\resources\\web-log\\*",3)

    val parsedLog=rawLine.flatMap(l=>Option(LogPreparser.parse(l)))

    spark.createDataset(parsedLog)(Encoders.bean(classOf[LogPreparser]))
      .repartition(3)
      .write.partitionBy("year","month","day")
      .mode(SaveMode.Overwrite)
      .saveAsTable("traffic.rawlog")

    spark.stop()

  }


}
