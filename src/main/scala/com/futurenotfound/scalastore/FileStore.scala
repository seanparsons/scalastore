package com.futurenotfound.scalastore

import scala.io.Source
import org.apache.commons.codec.digest.DigestUtils.md5Hex
import Using._
import collection.mutable.HashMap
import java.io.{FileOutputStream, FileInputStream, File}
import org.apache.commons.lang.SerializationUtils._
import java.net.URLClassLoader
import java.security.SecureClassLoader


class FileStore[T](val basepath: String) extends Store[T] {
  type SerializableHashMap[String, T] = HashMap[String, T] with java.io.Serializable
  def lookup(key: String): Option[T] = {
    val file = new File(basepath + "/" + keyHash(key))
    println("Performing lookup for key " + key + " against file: " + file)
    if (file.exists) {
      using (new FileInputStream(file)) {fileInputStream =>
        val map: SerializableHashMap[String, T] = deserialize(fileInputStream).asInstanceOf[SerializableHashMap[String, T]]
        println("lookup: map = " + map)
        map.get(key)
      }
    }
    else None
  }
  def update(key: String, update: Update[T]): Option[T] = {
    val file = new File(basepath + "/" + keyHash(key))
    println("Performing update for key " + key + " against file: " + file)
    val map: SerializableHashMap[String, T] =
      if (file.exists) {
        using (new FileInputStream(file)) {fileInputStream =>
          using (new CustomObjectInputStream(fileInputStream)) {objectInputStream =>
            objectInputStream.readObject().asInstanceOf[SerializableHashMap[String, T]]
          }
        }
      }
      else new HashMap[String, T] with java.io.Serializable
    println("map = " + map)
    val current = map.get(key)
    val latest = update.apply(current).get
    map(key) = latest
    println("map after = " + map)
    using (new FileOutputStream(file)) { fileOutputStream =>
      serialize(map, fileOutputStream)
    }
    Some(latest)
  }
  private def keyHash(key: String): String = md5Hex(key)
}
