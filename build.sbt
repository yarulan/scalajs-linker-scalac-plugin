val scalaJsVersion = "0.6.11"

scalaVersion in ThisBuild := "2.11.8"

val plugin = defproj("plugin").settings(
  organization := "pro.ulan",

  name := "scalajs-linker-scalac-plugin",

  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),

  libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value,

  libraryDependencies += "org.scala-js" %% "scalajs-cli" % scalaJsVersion,

  crossVersion := new CrossVersion.Binary(version => s"sjs${scalaJsVersion}_$version"),

  artifact in(Compile, assembly) := (artifact in(Compile, assembly)).value.copy(`classifier` = Some("assembly")),

  addArtifact(artifact in(Compile, assembly), assembly)
)

val tests = defproj("tests").settings(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test",

  (test in Test) <<= (test in Test) dependsOn (assembly in plugin)
)

val root = defproj("root", Some(".")).aggregate(plugin, tests)

def defproj(id: String, base: Option[String] = None) = Project(id, new File(base.getOrElse(id))).settings(
  publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))),

  releaseTagComment := s"Release ${(version in ThisBuild).value}",

  releaseCommitMessage := s"Update version to ${(version in ThisBuild).value}",

  publishArtifact := false
)