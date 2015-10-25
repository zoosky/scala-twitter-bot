name := "twitter-akka-client"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.rometools" % "rome-fetcher" % "1.5.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-remote" % "2.4.0"
)

libraryDependencies += "commons-codec" % "commons-codec" % "1.4"

libraryDependencies += "oauth.signpost" % "signpost-core" % "1.2.1.1"

libraryDependencies += "jtwitter" % "jtwitter" % "3.1.0" from "http://www.winterwell.com/software/jtwitter/jtwitter.jar"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.4"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.2"

/** Console */
initialCommands in console := "import com.typesafe.sbt.rss._"