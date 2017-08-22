name := "Sales"

version := "1.0"

scalaVersion := "2.12.3"

val circeVersion = "0.8.0"
val slickVersion = "3.2.0"
val h2Version = "1.4.195"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-java8" % circeVersion,
  "com.typesafe.slick" %% "slick" % slickVersion,
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.h2database" % "h2" % h2Version
)

val specs2Version = "3.9.1"
val scalaTestVersion = "3.0.1"

libraryDependencies ++= Seq(
//  "org.specs2" %% "specs2-core" % specs2Version % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

scalacOptions in Test ++= Seq(
  "-Yrangepos"
)