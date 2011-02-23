package com.futurenotfound.scalastore.trophies

@serializable
case class Game(val id: Int, val name: String, val trophies: List[Trophy])
