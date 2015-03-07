package com.github.fellowship_of_the_bus
package bewitched_blade
import java.util.logging.{Level, Logger}
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}
import BewitchedBlade.{Height,Width}

import state._
import game._
import lib.util.Native

class BewitchedBlade(gamename: String) extends StateBasedGame(gamename) {    
  def initStatesList(gc: GameContainer) = {
    gc.setShowFPS(true)
    addState(Menu)
    addState(BewitchedBlade.game)
  }
}

object BewitchedBlade extends App {
  def makeImg(loc: String) = new Image(loc)
  def drawCentred(s: String, h: Float, g: Graphics) = {
    g.drawString(s, Width/2 - s.length()*5, h)
  }
  val Width = 800
  val Height = 600
  val FrameRate = 60
  val game = new GameState

  try {
    Native.loadLibraryFromJar()
    val appgc = new AppGameContainer(new BewitchedBlade("Bewitched Blade"))
    appgc.setDisplayMode(Width, Height, false)
    appgc.setTargetFrameRate(FrameRate)
    appgc.setVSync(true)
    appgc.start()
  } catch {
    case ex: SlickException => Logger.getLogger(BewitchedBlade.getClass.getName()).log(Level.SEVERE, null, ex)
    case t: Throwable => 
      println("Library path is: " + System.getProperty("java.library.path"))
      t.printStackTrace
  }
}
