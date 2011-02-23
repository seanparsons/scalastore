package com.futurenotfound.scalastore

trait Update[T] {
  def apply(currentValue: Option[T]): Option[T]
}

object Update {
  implicit def functionToUpdate[T](function: (Option[T]) => Option[T]) = new Update[T] {
    override def apply(currentValue: Option[T]): Option[T] = function(currentValue)
  }
}