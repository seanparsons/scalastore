package com.futurenotfound.scalastore.trophies

import com.futurenotfound.scalastore._
import org.scalacheck.Gen
import Using.using
import java.io.File.createTempFile
import java.io.File
import java.io.{FileInputStream, ObjectInputStream, FileOutputStream, ObjectOutputStream}
import org.apache.commons.io.FileUtils._
import org.specs.mock.Mockito
import org.specs.SpecificationWithJUnit

class SimpleTrophyTest extends SpecificationWithJUnit with Mockito {
  var trophyFile: File = null
  var playerFile: File = null
  "FileStore" should { doAfter{cleanup()}
    "after adding a bunch of trophy and player information" in {
      trophyFile = createTempFile("filestore-trophy", null)
      trophyFile.delete()
      trophyFile.mkdir()
      playerFile = createTempFile("filestore-player", null)
      playerFile.delete()
      playerFile.mkdir()
      var players = new SynchronizedStore(new FileStore[Player](playerFile.getAbsolutePath()))
      val playerList = (1 to 10000).map(number => new Player(number, "player" + number))
      playerList.foreach(player => players.update(player.id.toString, new ReplaceUpdate(player)))
      var trophies = new SynchronizedStore(new FileStore[Trophy](trophyFile.getAbsolutePath()))
      val trophyList = (1 to 100).map(number => new Trophy(number, "trophy" + number))
      trophyList.foreach(trophy => trophies.update(trophy.id.toString, new ReplaceUpdate(trophy)))
      val computeServer = new ComputeServer(10)
      "not return None for any of the results" in {
        val results = (1 to 1000).map(number => (Gen.oneOf(playerList).sample.get, Gen.oneOf(trophyList).sample.get))
          .map{playerTrophyPair =>
            computeServer.future(players.update(playerTrophyPair._1.id.toString, Player.acquiredTrophyUpdate(playerTrophyPair._2.id, 10)))
          }
          .map(future => future().get)
        results.foreach(result => result must not(equalTo(None)))
      }
    }
  }
  def cleanup() = {
    deleteDirectory(trophyFile)
    deleteDirectory(playerFile)
  }
}