package com.futurenotfound.scalastore


object Using {
  def using[Closeable <: {def close(): Unit}, ReturnType](closeable: Closeable)(closeableCall: Closeable => ReturnType): ReturnType = {
    try {
        closeableCall(closeable)
    }
    finally {
        closeable.close()
    }
  }
}