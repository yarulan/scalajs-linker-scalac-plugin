package pro.ulan.scalajslinkerscalacplugin

class LinkerRunnerTest extends FunSuite {
  test("filterClasspath") {
    val classpath = LinkerRunner.filterClasspath(Seq(
      """C:\bin\java\jdk1.8.0_45\jre\lib\resources.jar""",
      """C:\bin\java\jdk1.8.0_45\jre\lib\rt.jar""",
      """C:\bin\java\jdk1.8.0_45\lib\tools.jar""",
      """C:\work\fefr\examples\todo\webapp\target\scala-2.11\classes""",
      """C:\work\fefr\webapp\target\scala-2.11\classes""",
      """C:\Users\mahpella\.ivy2\cache\org.scala-js\scalajs-dom_sjs0.6_2.11\jars\scalajs-dom_sjs0.6_2.11-0.9.1.jar""",
      """C:\Users\mahpella\.ivy2\cache\org.scala-js\scalajs-library_2.11\jars\scalajs-library_2.11-0.6.11.jar""",
      """C:\Users\mahpella\.ivy2\cache\org.scala-lang\scala-library\jars\scala-library-2.11.8.jar"""
    ))

    classpath shouldBe Seq(
      """C:\work\fefr\examples\todo\webapp\target\scala-2.11\classes""",
      """C:\work\fefr\webapp\target\scala-2.11\classes""",
      """C:\Users\mahpella\.ivy2\cache\org.scala-js\scalajs-dom_sjs0.6_2.11\jars\scalajs-dom_sjs0.6_2.11-0.9.1.jar"""
    )
  }
}