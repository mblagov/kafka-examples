package com.mblagov.spark

import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}

object SparkStructuredStreamingReadKafka {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Streaming read from Kafka")
      .config("spark.master", "local[2]")
      .getOrCreate()

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:29092")
      .option("subscribe", "mblagov_test")
      .option("startingOffsets", "earliest")
      .load()

    implicit val encoder: Encoder[String] = Encoders.STRING

    val dfw = df.writeStream
      .format("console")
      .outputMode(OutputMode.Append())
      .trigger(Trigger.ProcessingTime("5 seconds"))
    val sq = dfw.start()

    sq.awaitTermination()
  }


}
