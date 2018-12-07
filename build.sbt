
name := "test-web-service"

version := "1.0.0"

scalaVersion := "2.12.7"
scalacOptions ++= Seq("-language:higherKinds")
scalacOptions ++= Seq("-Ypartial-unification")
scalacOptions ++= Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature", "-Xlint", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % "0.18.19",
  "org.http4s" %% "http4s-blaze-server" % "0.18.19",
  "org.http4s" %% "http4s-circe" % "0.18.19",

  "io.circe" %% "circe-core" % "0.9.3",
  "io.circe" %% "circe-generic" % "0.9.3",
  "io.circe" %% "circe-parser" % "0.9.3",

  "org.tpolecat" %% "doobie-core" % "0.5.3",
  "mysql" % "mysql-connector-java" % "5.1.47",

  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.11.0",
)
