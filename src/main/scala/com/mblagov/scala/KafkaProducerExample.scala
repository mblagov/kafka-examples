package com.mblagov.scala

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import java.util.Properties

object KafkaProducerExample {

  def main(args: Array[String]): Unit = {
    val producerProperties = new Properties()
    producerProperties.put("bootstrap.servers", "localhost:29092")
    producerProperties.put("key.serializer", classOf[StringSerializer].getName)
    producerProperties.put("value.serializer", classOf[StringSerializer].getName)

    val producer = new KafkaProducer[String, String](producerProperties)
    try {
      val record = new ProducerRecord[String, String]("mblagov_from_scala_producer", "key", "quux")
      producer.send(record)
    } catch {
      case e:Exception => e.printStackTrace()
    } finally {
      // Important!!!
      producer.close()
    }
  }

}
