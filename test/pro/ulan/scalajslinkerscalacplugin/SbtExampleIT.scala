package pro.ulan.scalajslinkerscalacplugin

class SbtExampleIT extends BaseIT {
  test("it") {
    performTest("""cd example && cd sbt && sbt clean compile""", "example/sbt/target/scala-2.11/classes/app.js")
  }
}
