package com.github.fellowship_of_the_bus.ui
import org.newdawn.slick.{Animation => SlickAnimation, Image => SlickImage, Graphics}

trait Drawable {
  def draw(x: Float, y: Float): Unit
  def getWidth: Float
  def getHeight: Float
  def setRotation(ang: Float) = ()
  def setCenterOfRotation(x: Float, y: Float) = ()
  def update(delta: Long) = ()
  def copy(): Drawable
}

case class Image(str: String) extends Drawable {
  val img = new SlickImage(str)
  private var centerOfRotation = (0.0f, 0.0f)
  private var rotation = 0.0f

  def draw(x: Float, y: Float) = {
    val (cx, cy) = centerOfRotation
    img.setCenterOfRotation(cx, cy)
    img.setRotation(rotation)
    img.draw(x, y)
  }
  override def setRotation(ang: Float) = rotation = ang
  override def setCenterOfRotation(x: Float, y: Float) = centerOfRotation = (x,y)
  def getWidth: Float = img.getHeight
  def getHeight: Float = img.getHeight
  def copy() = this
}

object Animation {
  // def apply(strs: Array[String]): Animation = Animation(strs.map(new SlickImage(_)))
  def apply(strs: Array[String]): Animation = {
    println(1000/strs.length)
    Animation(new SlickAnimation(strs.map(new SlickImage(_)), 1000/strs.length, false))
  }
}

case class Animation(anim: SlickAnimation) extends Drawable {
  // anim.start
  // val duration = 1000 / imgs.length
  // val anim = new SlickAnimation(imgs, duration, false)
// imgs: Array[SlickImage]
  def draw(x: Float, y: Float) = anim.draw(x, y)
  def getWidth: Float = anim.getWidth
  def getHeight: Float = anim.getHeight
  override def update(delta: Long) = {
    // println(s"update: $delta")
   anim.update(delta)
  }

  def copy() = new Animation(anim.copy)
}
