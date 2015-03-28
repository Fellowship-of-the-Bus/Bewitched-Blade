package com.github.fellowship_of_the_bus
package bewitched_blade
package game
import IDMap._
import lib.game.GameConfig.{Width,Height}
import org.newdawn.slick.{Graphics}

class Castle() {
  val width = 50
  val height = 100 
  var maxHp = 400
  var hp = maxHp

  def resetHp = hp = maxHp
  var levels = 2


  private var isActive = true
  def active = isActive
  def inactivate = isActive = false

  def draw(g: Graphics) {
    var img = IDMap.images(BaseID)
    img.draw(0, Height-285)  
    for (i <- 1 to levels) {
      img = IDMap.images(TowerLvlID)
      img.draw(0, Height - i*111-285)
    }
    img = IDMap.images(WizardID)
    img.draw(25, Height-285 - levels*111-20)
  }

}
