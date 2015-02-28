package com.github.fellowship_of_the_bus.bewitched_blade.game
import spaceInvader.SpaceInvader._

object IDMap{
  val bladeID = 1

  val imageMap = Map(
    bladeID -> "img/blade.png"

  )

  lazy val images = imageMap.map { x =>
    val (id, loc) = x
    id -> makeImg(loc)
  }
}
