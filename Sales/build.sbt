name := "Sales"

version := "1.0"

scalaVersion := "2.12.3"

val circeVersion = "0.8.0"
val specs2Version = "3.9.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-java8" % circeVersion
)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % specs2Version % "test"
)

scalacOptions in Test ++= Seq(
  "-Yrangepos"
)

addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
)