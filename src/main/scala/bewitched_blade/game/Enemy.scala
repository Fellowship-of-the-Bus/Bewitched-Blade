package com.github.fellowship_of_the_bus.bewitched_blade
package game
import IDMap._
import BewitchedBlade.{Width}

object Enemy {
  def apply (eid: Int) = {
    eid match {
      case KnightID => new Knight(Width, 450-20)
    }
  }
}

abstract class Enemy (base: EnemyType, xc: Float, yc: Float) {
  var hp = base.maxHp
  val id = base.id
  private val xVel = base.xVel
  private val yVel = base.yVel
  val difficulty = base.difficulty
  var x = xc
  var y = yc

  private var isActive = true
  def active = isActive
  def inactivate = isActive = false

  def move() = {
    x -= xVel
    y += yVel
  }

  def topLeftCoord = (x-15, y-20)
}

trait EnemyType {
  def difficulty: Int
  def id: Int
  def maxHp: Float
  val xVel: Float = 1
  val yVel: Float = 0
}

trait Shield {
  def ang: Float
  def dur: Float
  def size: Float
}

// trait Shooter {
//   def x: Float

//   def shotInterval: Int
//   def initVel: Float
//   def dmg: Float
//   var shotTimer = 0

//   def shoot(height: Float) = {
//     if (shotTimer == 0) {
//       shotTimer = shotInterval
//       Some(Projectile(shotType, px, py));
//     } else {
//       None
//     }
//   }
// }

object Knight extends EnemyType {
  val id = KnightID
  val maxHp = 1.0f
  val difficulty = 1
}

class Knight(x: Float, y: Float) extends Enemy(Knight, x, y) {

}

