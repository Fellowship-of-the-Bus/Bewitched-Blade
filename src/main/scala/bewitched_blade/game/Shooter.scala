package com.github.fellowship_of_the_bus
package bewitched_blade
package game
import BewitchedBlade._
import Game._
import lib.game.GameConfig.{Width,Height}


trait Shooter {
  protected def x: Float
  protected def y: Float
  def width: Int
  def height: Int
  def shotType: Int
  def shotVel: Float
  def groundAcc: Int
  def airAcc: Int
  def arcs: Boolean

  def shotInterval: Int
  var shotTimer = 0

  def numShot: Int
  def shotDelay: Int

  var shotDelayTimer = shotInterval/2
  var numShotLeft = numShot
  def tick() = {
    if (shotTimer > 0) {
      shotTimer -= 1
    }
    if (shotDelayTimer > 0) {
      shotDelayTimer -= 1
    }
  }

  def shoot() = {
    import lib.util.rand
    val roll = rand(100)
    if (shotTimer == 0) {
      shotDelayTimer = 0
      numShotLeft = numShot
    }

    if( shotDelayTimer == 0 && numShotLeft > 0) {

      val px: Float = x
      val py: Float = y

      var xv: Float = 50 - x
      var yv: Float = (Height - BewitchedBlade.game.gameState.towerHeight - y)
      if (arcs == true) {
        yv = yv - 750
      }
      if (y < Ground - 20) { //In the Air
        if (roll > airAcc) {
          xv = xv + 40 - rand(80)
          yv = yv + 75 - rand(150)
        }
      } else { //On the ground
        if (roll > groundAcc) {
          xv = xv + 40 - rand(80)
          if (arcs == true)
            yv = yv + 250 - rand(500)
          else
            yv = yv + 75 - rand(150)
        }
      }
      val norm = math.sqrt(xv*xv + yv*yv).asInstanceOf[Float]
      xv = xv / norm * shotVel
      yv = yv / norm * shotVel

      numShotLeft -= 1
      shotDelayTimer = shotDelay
      shotTimer = shotInterval
      Some(Projectile(shotType, px, py, xv, yv));
    } else {
      None
    }
  }
}



