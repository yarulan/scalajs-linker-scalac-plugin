package pro.ulan.scalajslinkerscalacplugin

import scala.tools.nsc.plugins.{Plugin, PluginComponent}
import scala.tools.nsc.{Global, Phase}

class ScalajsLinkerScalacPlugin(val global: Global) extends Plugin {
  private var args = List[String]()

  override val name = "sjsld"

  override val components = List(Component)

  override val description = "Scalajs linker scalac plugin"

  override def init(options: List[String], error: (String) => Unit): Boolean = {
    this.args = options
    true
  }

  object Component extends PluginComponent {
    override val global = ScalajsLinkerScalacPlugin.this.global

    override val phaseName = "sjsld"

    override val runsAfter = List("jscode") // the phase of the scalajs compiler plugin

    override def newPhase(prevPhase: Phase) = new MyPhase(prevPhase)

    override def description = "runs scalajs linker"

    class MyPhase(prevPhase: Phase) extends StdPhase(prevPhase) {
      override def apply(unit: global.CompilationUnit): Unit = {}

      override def run(): Unit = {
        super.run()
        // hook here to be ran once
        new LinkerRunner(global, args).run()
      }
    }
  }
}