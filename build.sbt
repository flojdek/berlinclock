name := "berlinclock"

version := "3.14"

scalaVersion := "2.11.8"

lazy val bclock = project in file("bclock")

lazy val root = (project in file("."))
  //.settings(mainClass in assembly := Some("MainApp")) - Only needed for sbt assembly.
	.dependsOn(bclock)
	.aggregate(bclock)
