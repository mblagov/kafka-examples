package com.mblagov.scala

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._

import java.time.Duration
import java.util.{Collections, Properties}

object KafkaConsumerExample {

  def main(args: Array[String]): Unit = {
    val consumerProperties = new Properties()
    consumerProperties.put("bootstrap.servers", "localhost:29092")
    consumerProperties.put("key.deserializer", classOf[StringDeserializer].getName)
    consumerProperties.put("value.deserializer", classOf[StringDeserializer].getName)
    consumerProperties.put("group.id", "mblagov" + System.currentTimeMillis())
    consumerProperties.put("auto.offset.reset", "earliest")
    consumerProperties.put("enable.auto.commit", "true")

    val consumer = new KafkaConsumer[String, String](consumerProperties)

    consumer.subscribe(Collections.singletonList("mblagov_from_scala_producer"))

    while (true) {
      val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofSeconds(1))
      for (record <- records.asScala) {
        println(record)
      }
    }
  }

}
