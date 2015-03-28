package com.github.fellowship_of_the_bus
package bewitched_blade
package game
import IDMap._
import lib.game.GameConfig.{Width,Height}
import org.newdawn.slick.{Graphics}

class Castle(ga: Game) {
  val width = 50
  val height = 100 
  var maxHp = 100
  var hp = maxHp
  val game = ga

  def resetHp = hp = maxHp
  var levels = 2


  private var isActive = true
  def active = isActive
  def inactivate = isActive = false

  def draw(g: Graphics): Unit = {
    if (game.isGameOver) {
      return 
    }
    var img = IDMap.images(BaseID)
    img.draw(0, Height-285)  
    for (i <- 1 to levels) {
      img = IDMap.images(TowerLvlID)
      img.draw(0, Height - i*111-285)
    }
    img = IDMap.images(TopID)
    img.draw(0, Height-285 - levels*111-20)
    img = IDMap.images(WizardID)
    img.draw(25, Height-285 - levels*111-20)
    if (hp > 0) {
      img = IDMap.images(DoorID)
    } else {
      img = IDMap.images(BrokenDoorID)
    }
    img.draw(70, Height - 150-60)
  }
  def hit(pow: Int) = {
    hp = hp - pow
    if (hp < 0) {
      for (e <- game.enemies; if(e.active)) {
        if (e.id == KnightID) {
          e.stopDist = -200
        }
      }
    }
  }
  def collapse = {
    if (levels == 0) {
      game.gameOver
    } else {
      levels = levels -1
      game.towerHeight -= 111
      resetHp
      for (e <- game.enemies; if(e.active) && e.id == KnightID) {
        if (e.x < 100) {
          e.xVel = -15
          e.yVel = -5
          e.thrown = true
          e.x = 40
        }
        e.stopDist = 100
      }
    }
  }

}
