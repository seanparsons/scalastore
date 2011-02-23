package com.futurenotfound.scalastore

import akka.actor.Actor

@serializable case class KeyLookupCall(val key: String)
@serializable case class MultiKeyLookupCall(val keys: Seq[String])
@serializable case class UpdateCall[T](val key: String, val update: Update[T])

class StoreActor[T](private val store: Store[T]) extends Actor {
  def receive = {
    case KeyLookupCall(key) => self.reply(store.lookup(key))
    case MultiKeyLookupCall(keys) => self.reply(store.lookup(keys))
    case UpdateCall(key, update) => self.reply(store.update(key, update.asInstanceOf[Update[T]]))
    case _ => throw new RuntimeException("Unknown request.")
  }
}