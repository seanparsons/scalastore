package com.futurenotfound.scalastore

class MemoryStoreTest extends StoreTest {
  def storeName: String = "MemoryStore"
  def createStore[T]: Store[T] = new MemoryStore[T]()
}