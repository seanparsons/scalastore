package com.futurenotfound.scalastore.trophies

import scala.concurrent._
import scala.concurrent.ops._

class ComputeServer(processorCount: Int) {
  private val openJobs = new Channel[Job]()

  private abstract class Job {
    type JobType
    def task: JobType
    def receiveValue(value: JobType)
    def receiveException(exception: Throwable)
  }

  private def processor(processorIndex: Int) {
    while (true) {
      val job = openJobs.read
      try
      {
        job.receiveValue(job.task)
      }
      catch {
        case throwable: Throwable => job.receiveException(throwable)
      }
    }
  }

  def future[ResultType](resultExpr: => ResultType): () => ResultType = {
    val reply = new SyncVar[Either[Throwable,ResultType]]()
    openJobs.write{
      new Job {
        type JobType = ResultType
        def task = resultExpr
        def receiveValue(value: ResultType) = reply.set(Right(value))
        def receiveException(exception: Throwable) = reply.set(Left(exception))
      }
    }
    () => reply.get.right.getOrElse(throw new RuntimeException(reply.get.left.get))
  }

  spawn(replicate(0, processorCount) {processor})
}
