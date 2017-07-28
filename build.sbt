scalaVersion := "2.11.11"

name := "Scala_Practice"

version := "1.0"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation"
)

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "2.10.1",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.6",
  "org.scalaz" % "scalaz-core_2.10" % "6.0.3",
  "org.scalaz" % "scalaz-http_2.10" % "6.0.3",
  "org.eclipse.jetty" % "jetty-servlet" % "7.3.0.v20110203" % "container",
  "org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "test,container",
  "org.eclipse.jetty" % "jetty-server" % "7.3.0.v20110203" % "container"
)

enablePlugins(JettyPlugin)