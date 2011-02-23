package com.futurenotfound.scalastore

import collection.immutable.HashSet

@serializable
class AddToSetUpdate[T](val addition: T) extends Update[Set[T]] {
  def apply(currentValue: Option[Set[T]]): Option[Set[T]] = {
    currentValue.orElse(Some(new HashSet[T]())).map(set => set + addition)
  }
}