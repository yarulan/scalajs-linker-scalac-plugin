package pro.ulan.scalajslinkerscalacplugin

import java.io.File
import java.net.URL

import org.scalajs.cli.Scalajsld

import scala.tools.nsc.Global

class LinkerRunner(global: Global, pluginArgs: List[String]) {
  val classPath = global.classPath

  def run(): Unit = {
    val outputDirOpt = global.settings.outputDirs.getSingleOutput.map(_.canonicalPath)

    if (outputDirOpt.exists(_.equals("(memory)"))) {
      global.log("Skipping linking phase, unsupported output dir: " + outputDirOpt.getOrElse(""))
      return
    }

    val scalaJsLibraryUrlOpt = classPath.asURLs.find(_.getFile.contains("scalajs-library"))

    (scalaJsLibraryUrlOpt, outputDirOpt) match {
      case (None, _) =>
        global.abort("Unable to find scalajs-library in classpath: " + classPath.asClassPathString)

      case (_, None) =>
        global.abort("Only single output is supported")

      case (Some(scalaJsLibraryUrl), Some(outputDir)) =>
        run(scalaJsLibraryUrl, outputDir)
    }
  }

  def run(scalaJsLibraryUrl: URL, outputDir: String): Unit = {
    global.log("Plugin args: " + pluginArgs.mkString(" "))
    global.log("Classpath: " + classPath.asClassPathString)

    val scalaJsClassPath = LinkerRunner.filterClasspath(classPath.asURLs.map(_.getPath))

    global.log("Scalajs jars: " + scalaJsClassPath.mkString(File.pathSeparator))

    global.log("Output dir: " + outputDir)

    val destFile = new File(outputDir, "app.js")

    val linkerArgs =
      (if (pluginArgs.exists(List("-o", "--output").contains)) List() else List("--output", destFile.getAbsolutePath)) ++
      (if (pluginArgs.exists(List("stdlib").contains)) List() else List("--stdlib", scalaJsLibraryUrl.getFile)) ++
      pluginArgs ++
      scalaJsClassPath ++
      Seq(outputDir)

    global.log("Linker args: " + linkerArgs.mkString(" "))

    val (_, time) = measureTime {
      Scalajsld.main(linkerArgs.toArray)
    }

    global.log(s"Linking completed in ${time / 1000}s ${time % 1000}ms, result is written to $destFile")
  }

  def measureTime[T](f: => T): (T, Long) = {
    val before = System.currentTimeMillis()
    val result = f
    val after = System.currentTimeMillis()
    (result, after - before)
  }
}

object LinkerRunner {
  def filterClasspath(files: Seq[String]): Seq[String] = {
    files.filter { file =>
      file.contains("_sjs") || !file.endsWith(".jar")
    }
  }
}