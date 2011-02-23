package com.futurenotfound.scalastore

import collection.immutable.TreeMap


class SharedStoreTest extends StoreTest {
  def storeName: String = "ShardedStore"
  def createStore[T]: Store[T] = new ShardedStore[T](
    new TreeMap[Int, Store[T]] ++
      (Integer.MIN_VALUE until Integer.MAX_VALUE by Integer.MAX_VALUE / 8)
        .map(number => (number, new MemoryStore[T]()))
        .toMap,
    3
  )
}