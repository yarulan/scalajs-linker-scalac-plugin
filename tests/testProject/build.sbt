enablePlugins(ScalaJSPlugin)

libraryDependencies += "io.github.soc" %%% "scala-java-time" % "2.0.0-M3"

scalaVersion := "2.11.8"

scalacOptions += "-Xplugin:../../plugin/target/scala-2.11/scalajs-linker-scalac-plugin-assembly-0.1-SNAPSHOT.jar"

scalacOptions ++= List("-Ylog:sjsld", "-P:sjsld:--fastOpt")