package com.futurenotfound.scalastore

import akka.actor.Actor._
import akka.actor.ActorRef

class RemoteStoreActorTest extends StoreTest {
  var remoteActor: ActorRef = null
  var localActor: ActorRef = null
  def storeName: String = "StoreActor"
  def createStore[T]: Store[T] = {
    println("Creating Store!")
    remoteActor = remote.actorOf(new StoreActor[T](new MemoryStore[T]), "localhost", 2552)
    remote.start("localhost", 2552)
    remote.register("store-service", remoteActor)

    Thread.sleep(1000)
    localActor = remote.actorFor("store-service", "localhost", 2552).start

    return new Store[T] {
      def lookup(key: String): Option[T] = {
        println("Performing a lookup!")
        (localActor !! KeyLookupCall(key)).get.asInstanceOf[Option[T]]
      }
      def update(key: String, update: Update[T]): Option[T] = {
        println("Performing an update!")
        (localActor !! UpdateCall(key, update)).get.asInstanceOf[Option[T]]
      }
      override def lookup(keys: Seq[String]): Seq[Option[T]] = (localActor !! MultiKeyLookupCall(keys)).get.asInstanceOf[Seq[Option[T]]]
    }
  }
  override def cleanup() = {
    try {
      println("Cleaning up!")
      remote.shutdown

      Thread.sleep(1000)
      println("Cleaned up!")
    }
    catch {
      case e => ()
    }
  }
}