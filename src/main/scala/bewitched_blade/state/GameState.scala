package com.github.fellowship_of_the_bus
package bewitched_blade
package state
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException, Color, Input}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

import BewitchedBlade.{Ground}
import game._
import game.IDMap._
import ui.Image
import lib.game.GameConfig.{Width,Height}

class GameState extends BasicGameState {
  var gameState = new Game

  def reset() = gameState = new Game

  var pauseTimer = 0
  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    implicit val input = gc.getInput

    if (pauseTimer == 0 && input.isKeyDown(Input.KEY_P)) {
      gc.setPaused(!gc.isPaused)
      pauseTimer = 15
    }

    if (! gc.isPaused) {
      gameState.update(gc, game, delta)
    }

    pauseTimer = Math.max(0, pauseTimer-1)
  }

  def render(gc: GameContainer, game: StateBasedGame, g: Graphics) = {
    val lightBlue = new Color(150,150,255,0)
    g.setBackground(lightBlue)
    images(CausewayID).draw(0, Ground)

    def drawAll(objs: List[Enemy]*): Unit =
      for {
        xs <- objs
        o <- xs
        if (o.active)
      } o.draw(g)

    drawAll(gameState.enemies)
    gameState.castle.draw(g)

    gameState.blade.draw(g, IDMap.images(gameState.blade.id))
    
    for (p <- gameState.enemyProjectiles; if (p.active)) {
      p.draw(g)
    }

    if (gameState.isGameOver) {
      g.setColor(new Color(255, 0, 0, (0.5 * 255).asInstanceOf[Int]))
      g.fillRect(0, 0, Width, Height)
      images(GameOverID).draw(0,0)
    }
  }

  def init(gc: GameContainer, game: StateBasedGame) = {
    
  }

  def getID() = Mode.GameID
}
