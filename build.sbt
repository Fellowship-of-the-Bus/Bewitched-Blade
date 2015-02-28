fork := true

// need scalatest also as a build dependency: the build implements a custom reporter
libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

scalacOptions ++= Seq("-deprecation", "-feature", "-optimize", "-Yinline-warnings")

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

javaOptions += "-Djava.library.path=" + System.getProperty("java.library.path") + 
  separator + "./lib/native/" + os
