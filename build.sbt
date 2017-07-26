scalaVersion := "2.11.11"

name := "Scala_Practice"

version := "1.0"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation"
)

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "2.10.1",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.6"
)