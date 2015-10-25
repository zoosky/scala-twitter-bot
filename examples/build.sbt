name := "twitter-examples"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-remote" % "2.4.0"
)

libraryDependencies += "com.rometools" % "rome" % "1.5.1"

libraryDependencies += "commons-codec" % "commons-codec" % "1.4"

libraryDependencies += "oauth.signpost" % "signpost-core" % "1.2.1.1"

/** Console */
initialCommands in console := "import com.typesafe.sbt.rss._"