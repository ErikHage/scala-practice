scalaVersion := "2.11.8"

name := "Scala_Practice"

version := "1.0"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation"
)

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "2.10.1",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.6",
  "org.scalaz" % "scalaz-core_2.10" % scalazVersion,
  "org.scalaz" % "scalaz-http_2.10" % scalazVersion,
  "org.eclipse.jetty" % "jetty-servlet" % jettyVersion % "container",
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "test,container",
  "org.eclipse.jetty" % "jetty-server" % jettyVersion % "container",
  "com.h2database" % "h2" % "1.2.137",
  "org.squeryl" %% "squeryl" % "0.9.5-7"
)

enablePlugins(JettyPlugin)