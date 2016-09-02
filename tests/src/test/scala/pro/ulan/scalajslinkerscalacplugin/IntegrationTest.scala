package pro.ulan.scalajslinkerscalacplugin

import java.io.FileReader
import javax.script.ScriptEngineManager

import org.scalatest.{FunSuite, Matchers}

class IntegrationTest extends FunSuite with Matchers {
  test("it") {
    exec("cd tests && cd testProject && sbt clean compile")
    val engine = new ScriptEngineManager(null).getEngineByName("nashorn")
    val reader = new FileReader("tests/testProject/target/scala-2.11/classes/app.js")
    engine.eval(reader)
    val result = engine.eval("pro.ulan.scalajslinkerscalacplugin.ExampleApp().method__I()")
    result shouldBe 2
  }

  def exec(cmd: String): Unit = {
    import sys.process._

    val platformSpecificCmd = if (System.getProperty("os.name").toLowerCase.contains("win")) {
      "cmd /C" + cmd
    } else {
      cmd
    }

    val exitCode = platformSpecificCmd ! new ProcessLogger {
      override def out(s: => String) = System.out.println(s)
      override def err(s: => String) = System.err.println(s)
      override def buffer[T](f: => T) = f
    }

    exitCode shouldBe 0
  }
}