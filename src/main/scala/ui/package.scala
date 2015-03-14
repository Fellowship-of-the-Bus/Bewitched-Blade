package com.github.fellowship_of_the_bus

import org.newdawn.slick.{Graphics}
import bewitched_blade.BewitchedBlade.Width

package object ui {
  def drawCentred(s: String, h: Float, g: Graphics) = {
    g.drawString(s, Width/2 - s.length()*5, h)
  }
}
