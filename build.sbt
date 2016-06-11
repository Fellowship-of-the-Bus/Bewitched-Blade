val os = System.getProperty("os.name").split(" ")(0).toLowerCase match {
  case "linux" => "linux"
  case "mac" => "macosx"
  case "windows" => "windows"
  case "sunos" => "solaris"
  case x => x
}

val separator = System.getProperty("os.name").split(" ")(0).toLowerCase match {
  case "linux" => ":"
  case "mac" => ":"
  case "windows" => ";"
  case "sunos" => ":"
  case x => ":"
}

lazy val commonSettings = Seq(
  name := "Bewitched-Blade",
  organization := "com.github.fellowship_of_the_bus",
  scalaVersion := "2.11.8",
  version := "1.0",
  fork := true,
  javacOptions ++= Seq(
    "-encoding", "utf8",
    "-source", "1.7",
    "-target", "1.7",
    "-Xlint"
  ),
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-encoding", "utf8",
    "-target:jvm-1.7",
    "-optimize",
    "-Xlint",
    "-Yinline-warnings",
    "-Yinline",
    "-Yinline-handlers",
    "-Ybackend:GenBCode", // until 2.12 to eliminate inline warnings
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-value-discard",
    "-Ywarn-unused"
  ),
  javaOptions ++= Seq(
    s"-Djava.library.path=${System.getProperty("java.library.path")}${separator}./src/main/resources/natives/${os}"
  ),
  resolvers ++= Seq(
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    Resolver.url(
      "sbt-plugin-releases",
      new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/")
    )(Resolver.ivyStylePatterns)
  ),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "junit" % "junit" % "4.12" % "test",
    "com.propensive" %% "rapture-json-jackson" % "2.0.0-M5",
    "com.github.pathikrit" %% "better-files" % "2.16.0",
    "com.github.fellowship_of_the_bus" %% "fellowship-of-the-bus-slick2d-lib" % "0.1-SNAPSHOT" changing(),
    "com.github.fellowship_of_the_bus" %% "fellowship-of-the-bus-lib" % "0.4-SNAPSHOT" changing(),
    "org.jbox2d" % "jbox2d-library" % "2.2.1.1"
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)

