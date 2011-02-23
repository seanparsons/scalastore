import sbt._

class ScalastoreBuild(info: ProjectInfo) extends DefaultProject(info) with IdeaProject with AkkaProject {
  // Repositories.
  lazy val scalaToolsSnapshots = "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/"
  lazy val terrastoreRepository = "Terrastore Repository" at "http://m2.terrastore.googlecode.com/hg/repo"
  lazy val spyMemcached = "Spy Memcached" at "http://bleu.west.spy.net/~dustin/m2repo/"
  lazy val akkaRepo = "akka-repo" at "http://www.scalablesolutions.se/akka/repository/"
  lazy val jbossRepo = "jboss-repo" at "http://repository.jboss.org/nexus/content/groups/public-jboss/"
  // Dependencies.
  lazy val akkaTypedActor = akkaModule("typed-actor")
  lazy val akkaRemote = akkaModule("remote")
  lazy val akkaKernel = akkaModule("kernel")
  lazy val slf4j = "org.slf4j" % "slf4j-api" % "1.6.1"
  lazy val specs = "org.scala-tools.testing" %% "specs" % "1.6.7" % "test"
  lazy val scalacheck = "org.scala-tools.testing" %% "scalacheck" % "1.8" % "test"
  lazy val junit = "junit" % "junit" % "4.8.2" % "test"
  lazy val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test"
  lazy val logback = "ch.qos.logback" % "logback-classic" % "0.9.26" % "test"
  lazy val commonsio = "commons-io" % "commons-io" % "2.0"
  lazy val commonscodec = "commons-codec" % "commons-codec" % "1.4"
  lazy val commonslang = "commons-lang" % "commons-lang" % "2.5"
}
