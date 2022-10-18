package com.mblagov
package spark

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object SparkStreamWriteKafka {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Batch read from Kafka")
      .config("spark.master", "local[2]")
      .getOrCreate()

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:29092")
      .option("subscribe", "mblagov_write")
      .option("startingOffsets", "earliest")
      .load()

    val streamingWrite = df.writeStream.format("kafka")
      .option("kafka.bootstrap.servers", "localhost:29092")
      .option("topic", "mblagov_write_stream")
      .option("checkpointLocation", "checkpoints/")
      .trigger(Trigger.ProcessingTime("5 seconds"))
      .start()

    streamingWrite.awaitTermination()
  }

}
