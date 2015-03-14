package com.github.fellowship_of_the_bus
package bewitched_blade
package game

import org.newdawn.slick.{Graphics}
import IDMap._
import BewitchedBlade.{Width,Height}
import ui.{Image}

trait ProjectileType {
  def id: Int
  val xVel: Float = 1
  val yVel: Float = 1
  def width: Int
  def height: Int
}

object Projectile {
  def apply(id: Int, x: Float, y: Float, xv: Float, yv: Float) = {
     id match {
       case ArrowID => new Arrow(x,y, xv, yv)
       case RockID => new Rock(x,y,xv, yv)
     }
  }
}

abstract class Projectile(base: ProjectileType, xc: Float, yc: Float, xv: Float, yv: Float) extends GameObject(xc, yc) {
  val width = base.width
  val height = base.height

  protected var xVel = xv
  protected var yVel = yv
  
  var ang: Float = 0

  val id = base.id

  override def move() {
    ang = math.toDegrees(math.atan2(yv, xv)).asInstanceOf[Float]
    x = x + xVel
    y = y + yVel
    if (x < -width || x > Width+width) {
      inactivate
    }
    if (y < -height || y > Height+height) {
      inactivate
    }
  }

  def draw(g: Graphics) {
    val i = IDMap.images(base.id)
    i.setCenterOfRotation(10,10)
    i.setRotation(ang)
    i.draw(math.floor(x-10).asInstanceOf[Int], math.floor(y-10).asInstanceOf[Int])
  }
}

object Arrow extends ProjectileType{
  def height = 10
  def width = 10
  def id = ArrowID
}
class Arrow (x: Float, y: Float, xv: Float, yv:Float) extends Projectile ( Arrow ,x,y, xv,yv) {

}

object Rock extends ProjectileType {
  def height = 10
  def width = 10
  def id = RockID
}

class Rock(xc:Float, yc:Float, xv:Float, yv:Float) extends Projectile(Rock,xc,yc,xv,yv) {
  override def move() {
    ang = math.toDegrees(math.atan2(yv, xv)).asInstanceOf[Float]
    x = x + xVel
    yVel = yVel + 0.1f
    y = y + yVel
    if (x < -width || x > Width+width) {
      inactivate
    }
    if (y < -height || y > Height+height) {
      inactivate
    }
  }


}
