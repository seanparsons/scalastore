package com.futurenotfound.scalastore

import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito

trait StoreTest extends SpecificationWithJUnit with Mockito {
  def storeName: String
  def createStore[T]: Store[T]
  def cleanup() = {}
  var store: Store[String] = null

  storeName should {
    doAfter {cleanup()}
    doBefore {store = createStore[String]}
    "return None for empty key" in {
      "each time it is called" in {
        (1 to 100).foreach{count =>
          store.lookup("test") must equalTo(None)
        }
      }
    }
    "with updates for different keys" in {
      var update1 = new ReplaceUpdate("first update")
      var update2 = new ReplaceUpdate("second update")
      "apply updates successfully" in {
        "for differing updates" in {
          store.update("update1", update1) must equalTo(Some("first update"))
          store.update("update2", update2) must equalTo(Some("second update"))
        }
        "return the result correctly in a multiget" in {
          store.update("update1", update1)
          store.update("update2", update2)
          store.lookup(List("update1", "update2")) must equalTo(List(Some("first update"), Some("second update")))
        }
        "return the result correctly" in {
          "for update1" in {
            store.update("update1", update1)
            store.lookup("update1") must equalTo(Some("first update"))
          }
          "for update2" in {
            store.update("update2", update2)
            store.lookup("update2") must equalTo(Some("second update"))
          }
        }
      }
    }
    "with ReplaceUpdate" in {
      var update = new ReplaceUpdate("cake")
      "return successful update" in {
        store.update("test", update) must equalTo(Some("cake"))
      }
      "return correct result from lookup method after update" in {
        val store = createStore[String]
        store.update("test", update)
        store.lookup("test") must equalTo(Some("cake"))
      }
    }
  }
}