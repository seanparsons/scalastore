package com.futurenotfound.scalastore

import akka.actor.Actor._
import akka.actor.ActorRef

class StoreActorTest extends StoreTest {
  var storeActor: ActorRef = null
  def storeName: String = "StoreActor"
  def createStore[T]: Store[T] = {
    storeActor = actorOf(new StoreActor[T](new MemoryStore[T])).start
    return new Store[T] {
      def lookup(key: String): Option[T] = (storeActor !! KeyLookupCall(key)).get.asInstanceOf[Option[T]]
      def update(key: String, update: Update[T]): Option[T] = (storeActor !! UpdateCall(key, update)).get.asInstanceOf[Option[T]]
      override def lookup(keys: Seq[String]): Seq[Option[T]] = (storeActor !! MultiKeyLookupCall(keys)).get.asInstanceOf[Seq[Option[T]]]
    }
  }
  override def cleanup() = {
    storeActor.stop
  }
}