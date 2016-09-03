enablePlugins(ScalaJSPlugin)

libraryDependencies += "io.github.soc" %%% "scala-java-time" % "2.0.0-M3"

scalaVersion := "2.11.8"

val versionSbtPattern = """version in ThisBuild := "([^"]+)".*""".r

val pluginVersion = settingKey[String]("Plugin version")

pluginVersion := versionSbtPattern.findFirstMatchIn(scala.io.Source.fromFile("../../version.sbt").mkString).map(_.group(1)).get

val pluginFile = settingKey[File]("Plugin file")

pluginFile := new File(baseDirectory.value, s"../../plugin/target/scala-2.11/scalajs-linker-scalac-plugin-assembly-${pluginVersion.value}.jar")

scalacOptions += s"-Xplugin:${pluginFile.value.getAbsolutePath}"

scalacOptions ++= List("-Ylog:sjsld", "-P:sjsld:--fastOpt")