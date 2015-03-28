package com.github.fellowship_of_the_bus
package bewitched_blade
package game

import IDMap._
import BewitchedBlade.Ground
import lib.util.rand
import lib.game.GameConfig.{Width,Height}
import lib.game.Lifebar
import org.newdawn.slick.Graphics

object Enemy {
  def apply (eid: Int) = {
    eid match {
      case KnightID => new Knight(Width, Ground-20)
      case ArcherID => new Archer(Width, Ground-20)
      case CatapultID => new Catapult(Width, Ground -20) 
    }
    new Archer(Width, Ground -20) 
  }
}

abstract class Enemy (base: EnemyType, xc: Float, yc: Float) extends GameObject(xc, yc) with Lifebar {
  var hp = base.maxHp
  val id = base.id
  protected[game] var xVel = base.xVel
  protected[game] var yVel = base.yVel
  val difficulty = base.difficulty
  def numShot = base.numShot
  def shotDelay = base.shotDelay
  def shotType = base.shotType
  def shotInterval = base.shotInterval
  def shotVel = base.shotVel
  def groundAcc = base.groundAcc
  def airAcc = base.airAcc
  def arcs = base.arcs
  def maxHp = base.maxHp
  var stopDist = base.stopDist
  var xAcc = base.xAcc
  var inert = base.inert
  var thrown = base.thrown
  var stopped = base.stopped

  def width = 30
  def height = 40

  val img = images(id).copy
  def update(delta: Long) = img.update(delta)
  override def draw(g: Graphics) = {
    super.draw(g)
    val (x, y) = topLeftCoord
    img.draw(x, y)
  }
  override def move() = {
    if (x > stopDist) {
      xVel = xVel + xAcc
      if (xVel >1 ) {
        xVel = 1
      }
      x = x - xVel
    } else {
      stop
    }
    if (y + yVel > Ground - 21) {
      yVel = 0
      y = Ground - 20
    } else {
      yVel = yVel + 0.4f
      y = y + yVel
    }
  }
  def stop() = {
    img.stop
    stopped = true
  }
  def restart() = {
    stopDist = 0
    img.start
  }
  def hit(pow: Float, hitAng: Float): Float = {
    var kbPow = Math.min(pow, 5) - inert
    if (kbPow < 0) {
      kbPow = 0
    }
    val temp = (hitAng + 180) % 360
    xVel = xVel + kbPow*(math.cos(temp)).asInstanceOf[Float]
    yVel = yVel - kbPow*(math.sin(temp)).asInstanceOf[Float]
    hp = Math.max(hp - Math.sqrt(pow).asInstanceOf[Float], 0)
    if (hp <= 0) {
      inactivate
    }
    Math.sqrt(pow).asInstanceOf[Float]
  }
}

trait EnemyType {
  def difficulty: Int
  def id: Int
  def maxHp: Float
  val xVel: Float = 1
  val yVel: Float = 0
  var xAcc: Float = 0.5f

  def shotType: Int = ArrowID
  def shotInterval: Int = 60
  def numShot = 1
  def shotDelay = 0
  def shotVel = 5.0f
  def groundAcc = 80
  def airAcc = 50
  def arcs = false
  var stopDist = 100
  val inert = 1
  var thrown = false
  var stopped = false
}

trait Shield {
  def ang: Float
  def dur: Float
  def size: Float
}


object Knight extends EnemyType {
  val id = KnightID
  val maxHp = 30.0f
  val difficulty = 1
  override val inert = 2
}

class Knight(xc: Float, yc: Float) extends Enemy(Knight, xc, yc) {
  def kmove(ga: Game) = {
    if (x < 30) {
      if (!ga.isGameOver) {

        ga.castle.collapse
      } else {
        x = x - xVel
      }
    } else if (x > stopDist + 50 ) {
      xVel = xVel + xAcc
      if (xVel > 1) {
        xVel = 1
      }
      x = x - xVel
    } else if (x > stopDist && x <= stopDist + 50) {
       if (xVel > 0) {
         xVel = 5
         yVel = -2
       }
       x = x - xVel

    } else {
      x = stopDist + 1
      xVel = -10
      yVel = -3
      ga.castle.hit(20)
      x = x - xVel
      y = y - yVel
    }
    if (y + yVel > Ground - 20 && !thrown) {
      yVel = 0
      y = Ground - 20
    } else {
      yVel = yVel + 0.4f
      y = y + yVel
    }
    if (x < -30 || y > Height+30) {
      inactivate
    }
  }
}

object Archer extends EnemyType {
  val id = ArcherID
  val maxHp = 10.0f
  val difficulty = 2

  override val shotInterval = 59 * 5
  override val shotType = ArrowID
  override val groundAcc = 75
  override val airAcc = 45
  override val inert = 0
}

class Archer(xc: Float, yc:Float) extends Enemy(Archer,xc,yc) with Shooter{
  stopDist = 550 + rand(100)
}

object Catapult extends EnemyType {
  val id = CatapultID
  val maxHp = 100.0f
  val difficulty = 10
  override val shotInterval = 59*8
  override val shotType = RockID
  override val groundAcc = 25
  override val airAcc = 0
  override val arcs = true
  override val shotVel = 10.0f
  override val inert = 5
}
class Catapult(xc: Float, yc: Float) extends Enemy(Catapult, xc, yc) with Shooter {
  stopDist = 650 + rand(50)

  override def stop() = stopped = true
  override def restart() = stopDist = 0

  img.setCurrentFrame(1)

  img.setDuration(0, 2000)
  img.setDuration(1, 10000)

  override def update(delta: Long) = {
    if (stopped) img.update(delta)
  }

  override def shoot() = {
    if (stopped) {
      val shot = super.shoot
      shot match {
        case Some(_) =>
          img.setCurrentFrame(0)
          img.start
        case None => ()
      }
      shot
    } else {
      None
    }
  }
}
