package com.github.fellowship_of_the_bus
package bewitched_blade
package game
import IDMap._
import BewitchedBlade.{Width}

object Enemy {
  def apply (eid: Int) = {
    eid match {
      case KnightID => new Knight(Width, 450-20)
      case ArcherID => new Archer(Width, 450-20)
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

  def width = 30
  def height = 40
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

  override val shotInterval = 60* 5
  override val shotType = ArrowID
}

class Archer(xc: Float, yc:Float) extends Enemy(Archer,xc,yc) with Shooter{
  //import scala.util.Random
  //private val random = new Random
  //def rand(i:Int) = random.nextInt(i)
  import lib.util.rand
  val stopDist = 550 + rand(100)
  override def move() = {
    if (x > stopDist) {
      x = x - xVel
    }
    y = y + yVel
  }
}

