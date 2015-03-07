package com.github.fellowship_of_the_bus.bewitched_blade
package game
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input, Image}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

import IDMap._
import BewitchedBlade.{Height,Width}

class Game {
  val blade = new Blade(300,400)

  var enemies: List[Enemy] = List()

  /** removes inactive game objects */
  def cleanup() = {
    enemies = enemies.filter(_.active)
  }

  var counter = 0
  private val spawnTimer = 120
  private var enemyPower = 5
  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    implicit val input = gc.getInput
    blade.move(input.getMouseX,input.getMouseY)

    for (e <- enemies; if (e.active)) {
      e.move()
    }

    counter = counter + 1
    // periodically spawn move enemies
    if (counter == spawnTimer) {
      // periodically remove inactive objects
      cleanup
      var curEP = enemyPower
      var startMod = 0
      // var startMod: Int = math.min(
      //                   math.max(math.floor(math.log10(score)-4).asInstanceOf[Int], 0),
      //                   2)
      // var eTypeFrame = EnemyEnd-EnemyStart
      var eTypeFrame = 1
      while (curEP > 0) {
        // val e = Enemy(math.min(rand(eTypeFrame)+ EnemyStart + startMod, EnemyEnd - 1))
        val e = Enemy(KnightID)
        curEP -= e.difficulty
        enemies = e :: enemies
      }

      enemyPower += 1
      counter = 0
    }
  }

  var isGameOver = false
  def gameOver() = {
    // endTimer = 0
    // finalScore = score * (1+1.0*numHit/numShot)
    isGameOver = true

  }
}
