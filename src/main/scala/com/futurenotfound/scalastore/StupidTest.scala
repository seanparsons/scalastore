package com.futurenotfound.scalastore

import akka.actor.Actor._

object StupidTest extends Application {
  val remoteActor = remote.actorOf(new StoreActor[String](new MemoryStore[String]), "localhost", 2552)
  remote.start("localhost", 2552)
  remote.register("store-service", remoteActor)
}