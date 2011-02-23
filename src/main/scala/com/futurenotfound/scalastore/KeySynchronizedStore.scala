package com.futurenotfound.scalastore

import collection.mutable.WeakHashMap

case class KeySynchronizedStoreSyncTarget()

class KeySynchronizedStore[T](private val innerStore: Store[T]) extends Store[T] {
  private val syncMap = WeakHashMap.empty[String, Object]
  private def syncTarget(key: String) = syncMap.getOrElseUpdate(key, new KeySynchronizedStoreSyncTarget())
  def lookup(key: String): Option[T] = {
    syncTarget(key).synchronized{
      innerStore.lookup(key)
    }
  }
  def update(key: String, update: Update[T]): Option[T] = {
    syncTarget(key).synchronized{
      innerStore.update(key, update)
    }
  }
}