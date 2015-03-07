package com.github.fellowship_of_the_bus.bewitched_blade
package game
import IDMap._

object Enemy {
  def apply (eid: Int) = {
    eid match {
      case DroneID => new Drone(spawnx, spawny, Down)
    }
  }
}

abstract class Enemy (base: EnemyType, xc: Float, yc: Float) {
  var hp = base.maxHp
  val id = base.id
}

trait EnemyType {
  def id: Int
  def maxHp: Float
}

trait Shield {
  def ang: Float
  def dur: Float
  def size: Float
}

trait Shooter {
  def x: Float

  def shotInterval: Int
  def initVel: Float
  def dmg: Float
  var shotTimer = 0

  def shoot(height: Float) = {
    if (shotTimer == 0) {
      shotTimer = shotInterval
      Some(Projectile(shotType, px, py, dir));
    } else {
      None
    }
  }
}

object Drone extends EnemyType {
  val id = DroneID
  val maxHp = 1

}

class Drone(x: Float, y: Float) extends Enemy(Drone, x, y, dir) {

}

