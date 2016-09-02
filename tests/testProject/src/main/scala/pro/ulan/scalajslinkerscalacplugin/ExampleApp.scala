package pro.ulan.scalajslinkerscalacplugin

import scala.scalajs.js.JSApp

import org.threeten.bp._

object ExampleApp extends JSApp {
  def main(): Unit = {
    method
  }

  def method = {
    val fixedClock = Clock.fixed(Instant.ofEpochSecond(1234567890L), ZoneOffset.ofHours(0))
    val date = LocalDateTime.now(fixedClock)
    date.getMonth.getValue
  }
}