package com.futurenotfound.scalastore

class KeySynchronizedStoreTest extends StoreTest {
  def storeName: String = "KeySynchronizedStore"
  def createStore[T]: Store[T] = new KeySynchronizedStore[T](new MemoryStore[T])
}