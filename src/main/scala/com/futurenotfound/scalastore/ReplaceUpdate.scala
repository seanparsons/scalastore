package com.futurenotfound.scalastore

@serializable
class ReplaceUpdate[T](val replacement: T) extends Update[T] {
  def apply(currentValue :Option[T]): Option[T] = Some(replacement)
}