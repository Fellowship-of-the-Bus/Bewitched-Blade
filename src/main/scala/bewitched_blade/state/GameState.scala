package com.github.fellowship_of_the_bus.bewitched_blade
package state
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

import BewitchedBlade.{Height,Width}
import game._
import game.IDMap._

class GameState extends BasicGameState {
  var gameState = new Game

  def reset() = gameState = new Game

  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    implicit val input = gc.getInput
    gameState.update(gc, game, delta)
  }

  def render(gc: GameContainer, game: StateBasedGame, g: Graphics) = {
    val lightBlue = new Color(150,150,255,0)
    g.setBackground(lightBlue)
    g.drawImage(images(CausewayID), 0, 450)

    def drawAll(objs: List[Enemy]*): Unit =
      for {
        xs <- objs
        o <- xs
        if (o.active)
        (x,y) = o.topLeftCoord
      } g.drawImage(images(o.id), x, y)

    drawAll(gameState.enemies)

    gameState.blade.draw(g, IDMap.images(gameState.blade.id))
    
    for (p <- gameState.enemyProjectiles; if (p.active)) {
      p.draw(g)
    }

//    var i: Image = IDMap.images(gameState.blade.id)
//    for (j <- 0 to 10) {
//      i.setCenterOfRotation(10,10)
//      i.setRotation(-j*10)
//      g.drawImage(i, j*50, j*50)
//      g.fillOval(j*50, j*50, 10,10)
//    }
  }

  def init(gc: GameContainer, game: StateBasedGame) = {
    
  }

  def getID() = Mode.GameID
}