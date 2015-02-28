package com.github.fellowship_of_the_bus.bewitched_blade
import org.newdawn.slick.{GameContainer, Graphics, Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

object Menu extends BasicGameState {
  val choices = List("Play Game", "Options", "Quit")
  var curChoice = 0
  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    import Mode._
    val input = gc.getInput

    // if (input.isKeyDown(Input.KEY_P)) {
    //   BewitchedBlade.game.reset()
    //   game.enterState(Mode.GameID)
    // }
  }

  def render(gc: GameContainer, game: StateBasedGame, g: Graphics) = {
    import BewitchedBlade.{Width, Height}
    //g.drawImage(background.....)
    //g.drawImage(logo......)
    var counter = 0
    for ( item <- choices ) {
      BewitchedBlade.drawCentred(item, 200+counter*30,g)
      counter += 1
    }
    g.drawString("Space Invader", 0, 0)
    g.drawString("Press 'P' to play!", 0, 100)
  }

  def init(gc: GameContainer, game: StateBasedGame) = {
    
  }

  def getID() = Mode.MenuID
}

//object Option extends BasicGameState {
  //
//}
