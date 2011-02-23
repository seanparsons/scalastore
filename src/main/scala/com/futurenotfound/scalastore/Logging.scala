package com.futurenotfound.scalastore

import org.slf4j.LoggerFactory

trait Logging {
  lazy val logger = LoggerFactory.getLogger(this.getClass)
  def debug(message: => String) = logger.debug(message)
  def info(message: => String) = logger.info(message)
  def error(message: => String, throwable: => Throwable) = logger.error(message, throwable)
}
