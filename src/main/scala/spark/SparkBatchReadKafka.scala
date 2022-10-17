package com.mblagov.spark

import org.apache.spark.sql.SparkSession

object SparkBatchReadKafka {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Streaming read from file")
      .config("spark.master", "local[2]")
      .getOrCreate()

    val df = spark.read
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:29092")
      .option("subscribe", "mblagov_test")
      .option("endingOffsetsByTimestamp", """{"mblagov_test": {"0": 1665522188000}}""")
      .load()

    df.show()
  }


}
