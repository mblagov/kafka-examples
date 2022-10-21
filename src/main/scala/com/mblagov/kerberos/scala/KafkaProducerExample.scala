package com.mblagov.kerberos.scala

import io.confluent.kafka.serializers.KafkaAvroSerializer
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
    producerProperties.put("value.serializer", classOf[KafkaAvroSerializer].getName)
    producerProperties.put("schema.registry.url", "schemaRegistry")

    val userSchema =
    """

    {
      "type": "record",
      "name": "user_scheme",
      "fields" : [
      {
        "name": "id",
        "type": "long"
      },
      {
        "name": "name",
        "type": "string"
      }
      ]
    }

    """

    val schema = new Schema.Parser().parse(userSchema)
    val avroRecord = new GenericData.Record(schema)
    avroRecord.put("name", "Ivan")
    avroRecord.put("id", 123L)


    val producer = new KafkaProducer[String, GenericRecord](producerProperties)
    try {
      val record = new ProducerRecord[String, GenericRecord]("mblagov_avro", "key", avroRecord)
      producer.send(record)
    } catch {
      case e:Exception => e.printStackTrace()
    } finally {
      // Important!!!
      producer.close()
    }
  }

}
