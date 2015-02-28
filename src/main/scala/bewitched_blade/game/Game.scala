package com.github.fellowship_of_the_bus.bewitched_blade
package game
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

import BewitchedBlade.{Height,Width}

class Game {
  var blade = new Blade(300,400)
  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    //implicit val input = gc.getInput
    blade.move(10,10)
  }
}
