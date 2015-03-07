package com.github.fellowship_of_the_bus.bewitched_blade
package ui

import org.newdawn.slick.{Graphics, Color, Input}
import org.newdawn.slick.util.InputAdapter
import org.newdawn.slick.gui.MouseOverArea
import org.newdawn.slick.state.{StateBasedGame}
import BewitchedBlade.drawCentred

object Button {
  val width = 150
  val height = 20

  def apply(text: String, x: Float, y: Float, action: ()=>Unit)(implicit input: Input, state: Int, game: StateBasedGame) = {
    val b = new Button(text, x, y, width, height, () => if (game.getCurrentStateID == state) action())
    b.setInput(input)
    b
  }
}

class Button(text: String, x: Float, y: Float, width: Float, height: Float, action: () => Unit)
 extends InputAdapter {
  object ButtonMode {
    val NORMAL = 0
    val MOUSE_OVER = 1
    val MOUSE_DOWN = 2
  }
  import ButtonMode._
  
  private var mode = NORMAL

  def inButton(newx: Int, newy: Int): Boolean = 
    x < newx && newx < x+width && y < newy && newy < y+height

  override def setInput(input: Input) = {
    input.addMouseListener(this)
  }

  override def mouseMoved(oldx: Int, oldy: Int, newx: Int, newy: Int): Unit = {
    if (inButton(newx, newy)) {
      mode = MOUSE_OVER
    } else {
      mode = NORMAL
    }
  }

  val LEFT = 0
  override def mousePressed(button: Int, x: Int, y: Int): Unit = {
    if (mode == MOUSE_OVER && button == LEFT) {
      mode = MOUSE_DOWN
    }
  }

  override def mouseReleased(button: Int, x: Int, y: Int): Unit = {
    val in = inButton(x, y)
    if (mode == MOUSE_DOWN && button == LEFT && in) {
      action()
    }
    if (in) mode = MOUSE_OVER
    else mode = NORMAL
  }

  // def mouseClicked(button: Int, x: Int, y: Int, clickCount: Int): Unit
  // def mouseDragged(oldx: Int, oldy: Int, newx: Int, newy: Int): Unit = ???
  // def mouseWheelMoved(change: Int): Unit = ???

  def render(g: Graphics) = {
    var textColor = Color.red
    var bgColor = Color.white

    if (mode == MOUSE_OVER){
      bgColor = Color.lightGray
    }

    g.setColor(bgColor)
    g.fillRoundRect(x, y, width, height, 5)
    g.setColor(textColor)
    drawCentred(text, y, g)
  }
}
