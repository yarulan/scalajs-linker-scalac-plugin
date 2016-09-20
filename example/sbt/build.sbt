enablePlugins(ScalaJSPlugin)

libraryDependencies += "io.github.soc" %%% "scala-java-time" % "2.0.0-M3"

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal

scalaSource in Compile := baseDirectory.value / ".." / "src"

addCompilerPlugin("pro.ulan" % "scalajs-linker-scalac-plugin" % "test" classifier "assembly")

scalacOptions ++= List("-Ylog:sjsld", "-P:sjsld:--fastOpt")