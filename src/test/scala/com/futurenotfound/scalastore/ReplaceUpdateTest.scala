package com.futurenotfound.scalastore

import org.scalacheck.Gen
import org.scalacheck.Gen._

class ReplaceUpdateTest extends UpdateTest {
  def createReplaceUpdate() = new ReplaceUpdate(7)
  "ReplaceUpdate" should {
    var replaceUpdate = createReplaceUpdate()
    val permutations = for (list <- listOfN(10, Gen.oneOf(Some(-9999), Some(0), Some(1), Some(7), None))) yield list
    "apply should always return a successful update with the value 7" in {
      permutations must pass { permutation: List[Option[Int]] =>
        val replaceUpdateToHitRepeatedly = createReplaceUpdate()
        permutation.forall { currentValue: Option[Int] =>
          replaceUpdateToHitRepeatedly.apply(currentValue) == Some(7)
        } must beTrue
      }
    }
  }
}