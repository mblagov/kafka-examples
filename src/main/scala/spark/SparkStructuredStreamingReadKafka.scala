package com.mblagov.spark

import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}

object SparkStructuredStreamingReadKafka {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Streaming read from file")
      .config("spark.master", "local[2]")
      .getOrCreate()

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:29092")
      .option("subscribe", "mblagov_test")
      .option("startingOffsetsByTimestamp", """{"mblagov_test": {"0": 1665522188000}}""")
      .load()

    implicit val encder: Encoder[String] = Encoders.STRING

    val dfw = df.writeStream
      .format("console")
      .outputMode(OutputMode.Append())
      .trigger(Trigger.ProcessingTime("5 seconds"))
    val sq = dfw.start()

    sq.awaitTermination()
  }


}
