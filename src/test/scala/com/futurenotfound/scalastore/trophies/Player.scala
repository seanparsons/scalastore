package com.futurenotfound.scalastore.trophies

import com.futurenotfound.scalastore.Update

@serializable
case class Player(val id: Int, val name: String, val acquiredTrophies: List[AcquiredTrophy] = List())

@serializable
case class AcquiredTrophyUpdate(val trophy: Int, val acquiredAt: Long) extends Update[Player] {
  def apply(currentValue: Option[Player]): Option[Player] = {
    val player = currentValue.get
    val updatedTrophies = if (!player.acquiredTrophies.exists(_.trophy == trophy)) new AcquiredTrophy(trophy, acquiredAt) :: player.acquiredTrophies else player.acquiredTrophies
    return Some(new Player(player.id, player.name, updatedTrophies))
  }
}

object Player {
  def acquiredTrophyUpdate(trophy: Int, acquiredAt: Long): Update[Player] = new AcquiredTrophyUpdate(trophy, acquiredAt)
}
