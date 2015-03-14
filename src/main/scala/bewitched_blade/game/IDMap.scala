package com.github.fellowship_of_the_bus.bewitched_blade
package game
import BewitchedBlade._

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
    KnightID -> "img/Knight.png",
    
    ArcherID -> "img/Archer.png",
    CatapultID -> "img/Blade.png",
    CausewayID -> "img/Causeway.png",

    FotBLogoID -> "img/FotB-Logo.png"
  )

  lazy val images = imageMap.map { x =>
    val (id, loc) = x
    val temp = makeImg(loc)      
    if (id == bladeID) {
      temp.setCenterOfRotation(0,0)
    }

    id -> temp
  }
}
