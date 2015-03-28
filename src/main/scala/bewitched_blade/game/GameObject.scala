package com.github.fellowship_of_the_bus
package bewitched_blade
package game
import IDMap._
import lib.game.GameConfig.{Width}

abstract class GameObject(xc: Float, yc: Float) {
  var x = xc
  var y = yc

  def id: Int
  protected def xVel: Float
  protected def yVel: Float
  def width: Int
  def height: Int

  private var isActive = true
  def active = isActive
  def inactivate = isActive = false

  def move()

  def topLeftCoord = (x-width/2, y-height/2)
}
