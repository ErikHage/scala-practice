//-------------Setting up SBT

//  1. download jar -> SBT download, http://mng.bz/1E7x.
//  2. create a batch file called sbt.bat with the following lines:
//       Set SCRIPT_DIR=%~dp0
//       Java –Xmx512M –jar "%SCRIPT_DIR%sbt-launch.jar" %*
//  3. place the sbt.bat file and the .jar file in the same directory and alter your path so that it's accessible from any directory


//-------------Basics of SBT

//sources expected at /src/main/scala or /src/main/java
//build.sbt located at root
//in build.sbt all lines must be separated by a blank line

//example settings
//scalaVersion := "2.10.0"
//name := "Testing SBT"
//version := "1.0"
//scalacOptions ++= Seq("-unchecked", "-deprecation")      (enables warnings)

//useful: mkdir -p src/{main,test}/{scala,java,resources} lib project

//importing dependencies
//groupID % artifactID % version
//libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "7.3.0v20110203"

//point to a local maven repository
//resolvers += "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

//import only for testing:
//libraryDependencies += "org.scala-tools.testing" % "specs" % "1.6.2" % "test"

//custom tasks are defined in the .scala build definition file
//  1. create a TaskKey
//  2. provide a value for the TaskKey
//  3. put the task in the .scala build file under project

//import sbt._
//import Keys._
//
//object ExampleBuild extends Build {
//  val hello = TaskKey [Unit]("hello", "Prints 'Hello World'")
//
//  val helloTask: Setting[Task[Unit]] = hello := {
//    println("Hello World")
//  }
//
//  val project = Project (
//    "example",
//    file (".")).settings(helloTask)
//  )
//}