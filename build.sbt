ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-examples"
  )

val sparkVersion = "3.3.0"
val postgresVersion = "42.2.2"

resolvers += "Confluent" at "https://packages.confluent.io/maven/"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "3.3.1",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-avro" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  // logging
  "org.apache.logging.log4j" % "log4j-api" % "2.4.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.4.1",

  // https://mvnrepository.com/artifact/io.confluent/kafka-avro-serializer
  "io.confluent" % "kafka-avro-serializer" % "7.2.1",

// postgres for DB connectivity
  "org.postgresql" % "postgresql" % postgresVersion
)