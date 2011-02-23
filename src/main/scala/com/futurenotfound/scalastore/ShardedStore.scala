package com.futurenotfound.scalastore

import scala.collection.immutable.SortedMap

class ShardedStore[T](private val stores: SortedMap[Int, Store[T]], val replicationFactor: Int, val hashing: (String) => Int) extends Store[T] {
  def this(stores: SortedMap[Int, Store[T]], replicationFactor: Int) = this(stores, replicationFactor, (key: String) => key.hashCode())

  // Ensure this doesn't loop around back over itself.
  if (stores.size < replicationFactor)
    throw new IllegalArgumentException("Replication factor cannot be larger than the number of stores passed in.")

  def lookup(key: String): Option[T] = determineStores(key).head.lookup(key)

  def update(key: String, update: Update[T]): Option[T] = {
    determineStores(key).map(store => store.update(key, update)).head
  }

  private def determineStores(key: String): Iterable[Store[T]] = {
    (stores.from(hashing(key)).values ++ stores.values).take(replicationFactor)
  }
}