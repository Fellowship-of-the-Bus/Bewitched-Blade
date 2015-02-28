package com.github.fellowship_of_the_bus.bewitched_blade
package game
import BewitchedBlade._

object IDMap{
  val bladeID = 1

  val imageMap = Map(
    bladeID -> "img/blade.png"

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
