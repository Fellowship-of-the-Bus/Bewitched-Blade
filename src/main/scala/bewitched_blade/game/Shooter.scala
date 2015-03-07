package com.github.fellowship_of_the_bus.bewitched_blade
package game
import BewitchedBlade._


trait Shooter {
  protected def x: Float
  protected def y: Float
  def width: Int
  def height: Int
  def shotType: Int
  def shotVel: Float

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

  def shoot = {
    if (shotTimer == 0) {
      shotDelayTimer = 0
      numShotLeft = numShot
    }

    if( shotDelayTimer == 0 && numShotLeft > 0) {
      var px: Float = x
      var py: Float = y

      var xv: Float = 50 - x
      var yv: Float = (BewitchedBlade.Height - BewitchedBlade.game.gameState.towerHeight - y)

      var norm = math.sqrt(xv*xv + yv*yv).asInstanceOf[Float]
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



