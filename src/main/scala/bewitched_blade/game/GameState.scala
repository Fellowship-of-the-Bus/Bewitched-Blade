package com.github.fellowship_of_the_bus.bewitched_blade
package game
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

import BewitchedBlade.{Height,Width}

class GameState extends BasicGameState {
  var gameState = new Game

  def reset() = gameState = new Game

  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    implicit val input = gc.getInput
    gameState.update(gc, game, delta)
  }

  def render(gc: GameContainer, game: StateBasedGame, g: Graphics) = {
    gameState.blade.draw(g, IDMap.images(gameState.blade.id))
  }

  def init(gc: GameContainer, game: StateBasedGame) = {
    
  }

  def getID() = Mode.GameID
}
