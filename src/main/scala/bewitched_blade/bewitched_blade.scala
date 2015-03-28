package com.github.fellowship_of_the_bus
package bewitched_blade
import java.util.logging.{Level, Logger}
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

import state._
import game._
import lib.util.Native
import lib.game.GameConfig

class BewitchedBlade(gamename: String) extends StateBasedGame(gamename) {    
  def initStatesList(gc: GameContainer) = {
    gc.setShowFPS(true)
    addState(Menu)
    addState(BewitchedBlade.game)
    addState(Options)
  }
}

object BewitchedBlade extends App {
  def makeImg(loc: String) = new Image(loc)

  GameConfig.Width = 800
  GameConfig.Height = 600
  GameConfig.FrameRate = 60
  val Ground = 450
  lazy val game = new GameState

  try {
    import GameConfig._
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
