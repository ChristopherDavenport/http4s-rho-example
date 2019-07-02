val Http4sVersion = "0.20.3"
val Specs2Version = "4.6.0"
val LogbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings(
    organization := "org.http4s",
    name := "http4s-rho-example",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server"  % Http4sVersion,
      "org.http4s"      %% "http4s-circe"         % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"           % Http4sVersion,
      "org.http4s"      %% "rho-swagger"          % "0.19.0",
      "io.circe"        %% "circe-generic"       % "0.11.1",
      "org.specs2"     %% "specs2-core"           % Specs2Version % "test",
      "ch.qos.logback"  %  "logback-classic"      % LogbackVersion
    )
  )

