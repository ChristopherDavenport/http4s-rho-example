val Http4sVersion = "0.18.25"
val Specs2Version = "4.6.0"
val LogbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings(
    organization := "org.http4s",
    name := "http4s-rho-example",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.10"),
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server"  % Http4sVersion,
      "org.http4s"      %% "http4s-circe"         % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"           % Http4sVersion,
      "org.http4s"      %% "rho-swagger"          % "0.18.0",
      "io.circe"        %% "circe-generic"       % "0.9.3",
      "org.specs2"     %% "specs2-core"           % Specs2Version % "test",
      "ch.qos.logback"  %  "logback-classic"      % LogbackVersion
    )
  )

