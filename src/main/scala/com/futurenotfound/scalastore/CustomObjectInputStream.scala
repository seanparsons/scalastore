package com.futurenotfound.scalastore

import java.io.{InputStream, ObjectStreamClass, ObjectInputStream}

class CustomObjectInputStream(inputStream: InputStream) extends ObjectInputStream(inputStream) {
  override def resolveClass(objectStreamClass: ObjectStreamClass): Class[_] = {
    val className = objectStreamClass.getName()
    className match {
      case "boolean" => classOf[java.lang.Boolean]
      case "byte" => classOf[java.lang.Byte]
      case "char" => classOf[java.lang.Character]
      case "short" => classOf[java.lang.Short]
      case "int" => classOf[java.lang.Integer]
      case "long" => classOf[java.lang.Long]
      case "float" => classOf[java.lang.Float]
      case "double" => classOf[java.lang.Double]
      case "void" => classOf[java.lang.Void]
      case _ => Class.forName(className, false, Thread.currentThread().getContextClassLoader())
    }
  }
}