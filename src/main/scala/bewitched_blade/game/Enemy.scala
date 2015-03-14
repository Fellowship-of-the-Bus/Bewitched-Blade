package com.github.fellowship_of_the_bus
package bewitched_blade
package game
import IDMap._
import BewitchedBlade.{Width, Ground}

object Enemy {
  def apply (eid: Int) = {
    eid match {
      case KnightID => new Knight(Width, Ground-20)
      case ArcherID => new Archer(Width, Ground-20)
      case CatapultID => new Catapult(Width, Ground -20) 
    }
  }
}

abstract class Enemy (base: EnemyType, xc: Float, yc: Float) extends GameObject(xc, yc) {
  var hp = base.maxHp
  val id = base.id
  protected var xVel = base.xVel
  protected var yVel = base.yVel
  val difficulty = base.difficulty
  def numShot = base.numShot
  def shotDelay = base.shotDelay
  def shotType = base.shotType
  def shotInterval = base.shotInterval
  def shotVel = base.shotVel
  def groundAcc = base.groundAcc
  def airAcc = base.airAcc
  def arcs = base.arcs

  def width = 30
  def height = 40

  val img = images(id).copy
  def update(delta: Long) = img.update(delta)
  def draw() = {
    val (x, y) = topLeftCoord
    img.draw(x, y)
  }
}

trait EnemyType {
  def difficulty: Int
  def id: Int
  def maxHp: Float
  val xVel: Float = 1
  val yVel: Float = 0

  def shotType: Int = ArrowID
  def shotInterval: Int = 60
  def numShot = 1
  def shotDelay = 0
  def shotVel = 5.0f
  def groundAcc = 80
  def airAcc = 50
  def arcs = false
}

trait Shield {
  def ang: Float
  def dur: Float
  def size: Float
}


object Knight extends EnemyType {
  val id = KnightID
  val maxHp = 1.0f
  val difficulty = 1
}

class Knight(x: Float, y: Float) extends Enemy(Knight, x, y) {

}

object Archer extends EnemyType {
  val id = ArcherID
  val maxHp = 1.0f
  val difficulty = 2

  override val shotInterval = 59 * 5
  override val shotType = ArrowID
  override val groundAcc = 75
  override val airAcc = 45
}

class Archer(xc: Float, yc:Float) extends Enemy(Archer,xc,yc) with Shooter{
  import lib.util.rand
  val stopDist = 550 + rand(100)
  override def move() = {
    if (x > stopDist) {
      x = x - xVel
    }
    if (y + yVel > Ground - 20) {
      yVel = 0
      y = Ground - 20
    } else {
      yVel = yVel + 0.4f
      y = y + yVel
    }
  }
}

object Catapult extends EnemyType {
  val id = CatapultID
  val maxHp = 2.0f
  val difficulty = 4
  override val shotInterval = 59*8
  override val shotType = RockID
  override val groundAcc = 25
  override val airAcc = 0
  override val arcs = true
  override val shotVel = 10.0f
}
class Catapult(xc: Float, yc: Float) extends Enemy(Catapult, xc, yc) with Shooter {
  import lib.util.rand
  val stopDist = 650 + rand(50)
  override def move() = {
    if (x > stopDist) {
      x = x - xVel
    }
    y + yVel
  }
}
