name := "AkkaHttp-Spark"

version := "0.0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.2.0"

lazy val akkahttp = (project in file(".")).enablePlugins(JavaAppPackaging)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaStreamVersion = "1.0-RC4"
  val akkaVersion = "2.3.11"

  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamVersion,
    "org.scalatest"     %% "scalatest"                            % "2.2.5" % "test",
    "com.typesafe.akka" %% "akka-testkit"                         % akkaVersion % "test"

  )
}

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % sparkVersion

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.3"

libraryDependencies += "com.github.etaty" % "rediscala_2.11" % "1.8.0"

packageName in Docker := "akka-http-spark"
dockerExposedPorts := Seq(9911)

Revolver.settings
