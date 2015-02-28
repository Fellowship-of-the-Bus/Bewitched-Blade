package spaceInvader
import java.util.logging.{Level, Logger}
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}
import gameObject.IDMap._
import SpaceInvader.{Height,Width}

class SpaceInvader(gamename: String) extends StateBasedGame(gamename) {
  import gameObject._
  import Mode._
    
  def initStatesList(gc: GameContainer) = {
    gc.setShowFPS(true)
    addState(Menu)
    addState(SpaceInvader.game)
  }

  // override def update(gc: GameContainer, delta: Int) = {
  //   implicit val input = gc.getInput

  //   mode = mode match {
  //     case MenuID => Menu.update(gc, delta)
  //     case GameID | GameOverID => game.update(gc, delta) 
  //   }
  // }
  // override def render(gc: GameContainer, g: Graphics) = {
  //   mode match {
  //     case MenuID => Menu.render(gc, g)
  //     case GameID | GameOverID => game.render(gc, g)
  //   }
  // }
}

object SpaceInvader extends App {
  def makeImg(loc: String) = new Image(loc)
  def drawCentred(s: String, h: Int, g: Graphics) = {
    g.drawString(s, Width/2 - s.length()*5, h)
  }
  val Width = 800
  val Height = 600
  val FrameRate = 60
  val game = new Game

  try {
    println("Library path is: " + System.getProperty("java.library.path"))
    val appgc = new AppGameContainer(new SpaceInvader("Simple Slick Game"))
    appgc.setDisplayMode(Width, Height, false)
    appgc.setTargetFrameRate(FrameRate)
    appgc.setVSync(true)
    appgc.start()
  } catch {
    case ex: SlickException => Logger.getLogger(SpaceInvader.getClass.getName()).log(Level.SEVERE, null, ex)
    case _: Throwable => println("Library path is: " + System.getProperty("java.library.path"))
  }
}
