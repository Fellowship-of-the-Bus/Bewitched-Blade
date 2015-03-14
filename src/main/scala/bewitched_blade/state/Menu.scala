package com.github.fellowship_of_the_bus
package bewitched_blade
package state

import ui.Button
import game.IDMap._
import BewitchedBlade.{Width, Height}

import org.newdawn.slick.{GameContainer, Graphics, Color, Input, KeyListener}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}


object Menu extends BasicGameState {
  val centerx = Width/2-Button.width/2
  implicit val id = getID

  implicit var input: Input = null
  implicit var SBGame: StateBasedGame = null

  lazy val choices = List(
    Button("Play Game", centerx, 200, () => SBGame.enterState(Mode.GameID)),
    Button("Options", centerx, 200+30, () => SBGame.enterState(Mode.OptionsID)), 
    Button("Quit", centerx, 200+60, () => System.exit(0)))

  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    import Mode._
    implicit val input = gc.getInput
  }

  def render(gc: GameContainer, game: StateBasedGame, g: Graphics) = {
    import BewitchedBlade.{Width, Height}
    //g.drawImage(background.....)
    val fotb = images(FotBLogoID)
    fotb.draw(Width/2-fotb.getWidth/2, 3*Height/4)
    for ( item <- choices ) {
      item.render(g)
    }
  }

  def init(gc: GameContainer, game: StateBasedGame) = {
    input = gc.getInput
    SBGame = game
    gc.getGraphics.setBackground(Color.cyan)
  }

  def getID() = Mode.MenuID
}

object Options extends BasicGameState {
  import Menu.{centerx, SBGame, input}

  implicit val id = getID

  lazy val choices = List(
    Button("Back", centerx, 200, () => SBGame.enterState(Mode.MenuID)))

  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    import Mode._
    val input = gc.getInput
  }

  def render(gc: GameContainer, game: StateBasedGame, g: Graphics) = {
    for ( item <- choices ) {
      item.render(g)
    }
  }

  def init(gc: GameContainer, game: StateBasedGame) = {
    
  }

  def getID() = Mode.OptionsID
}
