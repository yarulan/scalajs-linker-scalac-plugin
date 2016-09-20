package pro.ulan.scalajslinkerscalacplugin

class MavenExampleIT extends BaseIT {
  test("it") {
    performTest("""cd example && cd maven && mvn clean compile""", "example/maven/target/classes/app.js")
  }
}
