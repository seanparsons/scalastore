package com.futurenotfound.scalastore

import collection.mutable.HashMap

class MemoryStore[T] extends Store[T] {
  val internalMap = new HashMap[String, T]()
  def lookup(key: String): Option[T] = internalMap.get(key)

  def update(key: String, update: Update[T]): Option[T] = {
    val result = update.apply(internalMap.get(key))
    internalMap.put(key, result.get)
    return result
  }
}
