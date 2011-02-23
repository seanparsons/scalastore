package com.futurenotfound.scalastore

import java.io.File._
import org.apache.commons.io.FileUtils._

class FileStoreTest extends StoreTest {
  def storeName: String = "FileStore"
  var cleanupCall: () => Unit = {null}
  def createStore[T]: Store[T] = {
    val file = createTempFile("filestore", null)
    file.delete()
    file.mkdir()
    cleanupCall = () => {
      deleteDirectory(file)
      null
    }
    new FileStore[T](file.getAbsolutePath())
  }
  override def cleanup() = {
    if (cleanupCall != null) {
      cleanupCall()
    }
  }
}