package com.github.fellowship_of_the_bus
package bewitched_blade.game

import bewitched_blade.BewitchedBlade._
import ui.{Image, Animation}

object IDMap{
  val bladeID = 1
  val ArrowID = 2
  val RockID = 3

  val EnemyStart = 10
  val KnightID = 10
  val ArcherID = 11
  val CatapultID = 12
  val EnemyEnd = 13

  val CausewayID = 100

  val FotBLogoID = 1000

  val imageMap = Map(
    bladeID -> "img/Blade.png",
    ArrowID -> "img/Arrow.png", 
    RockID -> "img/Knight.png",

    KnightID -> Array("img/Knight.png", "img/Knight2.png"),
    ArcherID -> Array("img/Archer.png", "img/Archer2.png"),
    CatapultID -> "img/Blade.png",

    CausewayID -> "img/Causeway.png",

    FotBLogoID -> "img/FotB-Logo.png"
  )

  lazy val images: Map[Int, ui.Drawable] = imageMap.map { x =>
    val (id, loc) = x
    val img = loc match {
      case xs: Array[String] => Animation(xs)
      case str: String => Image(str)
    }
    id -> img
  }

  // lazy val animations = images.filter {
  //   case (id, xs: Array[Image]) => true
  //   case _ => false
  // } map {
  //   case (id, imgs: Array[Image]) => 
  //     val duration = Array(30, 30)
  //     id -> new Animation(imgs, duration, false)
  // }
}
