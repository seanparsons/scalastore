
package com.futurenotfound.scalastore

class SynchronizedStoreTest extends StoreTest {
  def storeName: String = "SynchronizedStore"
  def createStore[T]: Store[T] = new SynchronizedStore[T](new MemoryStore[T])
}