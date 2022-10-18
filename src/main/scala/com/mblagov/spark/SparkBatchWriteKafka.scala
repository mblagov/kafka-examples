package com.mblagov.spark

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object SparkBatchWriteKafka {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Batch read from Kafka")
      .config("spark.master", "local[2]")
      .getOrCreate()

    val data = List(("foo", "bar"), ("baz", "qux"))
    val rdd = spark.sparkContext.parallelize(data).map(v => Row(v._1, v._2))

    val schema = StructType(Seq(StructField("key", StringType), StructField("value", StringType)))
    val df = spark.createDataFrame(rdd, schema)

    df.show()
    df.write.format("kafka")
      .option("kafka.bootstrap.servers", "localhost:29092")
      .option("topic", "mblagov_write")
      .save()
  }

}
