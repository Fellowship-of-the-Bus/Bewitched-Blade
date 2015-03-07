package com.github.fellowship_of_the_bus.bewitched_blade
package game
import IDMap._
import BewitchedBlade.{Width}

trait ProjectileType {
  def id: Int
  val xVel: Float = 1
  val yVel: Float = 1
  def width: Int
  def height: Int
}

abstract class Projectile(base: ProjectileType, xc: Float, yc: Float) extends GameObject(xc, yc) {
  val width = base.width
  val height = base.height

  protected var xVel = base.xVel
  protected var yVel = base.yVel

  val id = base.id
}
