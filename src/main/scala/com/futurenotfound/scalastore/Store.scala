package com.futurenotfound.scalastore

trait Store[T] {
  def lookup(key: String): Option[T]
  def update(key: String, update: Update[T]): Option[T]
  def lookup(keys: Seq[String]): Seq[Option[T]] = keys.map(key => lookup(key))
}