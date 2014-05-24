// Copyright 2012 Foursquare Labs Inc. All Rights Reserved.
import sbt._
import Keys._

object RogueBuild extends Build {
  override lazy val projects =
    Seq(all, index, core, lift, spindle)

  lazy val all: Project = Project("all", file(".")) aggregate(
    index, core, lift, spindle)

  lazy val index = Project("rogue-index", file("rogue-index/")) dependsOn()
  lazy val core = Project("rogue-core", file("rogue-core/")) dependsOn(index % "compile;test->test;runtime->runtime")
  lazy val lift = Project("rogue-lift", file("rogue-lift/")) dependsOn(core % "compile;test->test;runtime->runtime")
  lazy val spindle = Project("rogue-spindle", file("rogue-spindle/")) dependsOn(core % "compile;test->test;runtime->runtime")

  lazy val defaultSettings: Seq[Setting[_]] = Seq(
    version := "2.3.0-SNAPSHOT",
    organization := "com.foursquare",
    scalaVersion := "2.10.2",
    crossScalaVersions := Seq("2.10.2"),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    pomExtra := (
      <url>http://github.com/foursquare/rogue</url>
      <licenses>
        <license>
          <name>Apache</name>
          <url>http://www.opensource.org/licenses/Apache-2.0</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:foursquare/rogue.git</url>
        <connection>scm:git:git@github.com:foursquare/rogue.git</connection>
      </scm>
      <developers>
        <developer>
          <id>jliszka</id>
          <name>Jason Liszka</name>
          <url>http://github.com/jliszka</url>
        </developer>
      </developers>),
    resolvers ++= Seq(
        "Bryan J Swift Repository" at "http://repos.bryanjswift.com/maven2/",
        "Releases" at "http://oss.sonatype.org/content/repositories/releases",
        "Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"),
    retrieveManaged := true,
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    scalacOptions <++= scalaVersion map { scalaVersion =>
        scalaVersion.split('.') match {
            case Array(major, minor, _*) if major.toInt >= 2 && minor.toInt >= 10 => Seq("-feature", "-language:_")
            case _ => Seq()
        }
    },

    // Hack to work around SBT bug generating scaladoc for projects with no dependencies.
    // https://github.com/harrah/xsbt/issues/85
    unmanagedClasspath in Compile += Attributed.blank(new java.io.File("doesnotexist")),

    testFrameworks += new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))
}
