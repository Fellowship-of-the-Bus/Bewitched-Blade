package com.github.fellowship_of_the_bus
package bewitched_blade
package game
import org.newdawn.slick.{AppGameContainer, GameContainer, Graphics, SlickException,Color, Input}
import org.newdawn.slick.state.{BasicGameState, StateBasedGame}

import IDMap._
import lib.game.GameConfig.{Height,Width}

class Game {
  val blade = new Blade(300,400)
  var towerHeight = 400
  val castle = new Castle

  var enemies: List[Enemy] = List()
  var enemyProjectiles: List[Projectile] = List()

  /** removes inactive game objects */
  def cleanup() = {
    enemies = enemies.filter(_.active)
    enemyProjectiles = enemyProjectiles.filter(_.active)
  }

  var counter = 0
  private val spawnTimer = 120
  private var enemyPower = 5
  def update(gc: GameContainer, game: StateBasedGame, delta: Int) = {
    implicit val input = gc.getInput
    blade.move(input.getMouseX,input.getMouseY)

    for (e <- enemies; if (e.active)) {
      blade.collision(e)
      e.move()
      e.update(delta)
      e match {
        case sh: Shooter =>
          val shot = sh.shoot
          shot match {
            case Some(s) => enemyProjectiles = s::enemyProjectiles
            case _ => ()
          }
          sh.tick()
        case _ => ()
      }
    }

    for (p <- enemyProjectiles; if (p.active)) {
      blade.collision(p)
      p.move()
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
        //val e = Enemy(KnightID)
        //val e = Enemy(ArcherID)
        import lib.util.rand
        val e = Enemy(EnemyStart + rand(EnemyEnd - EnemyStart))
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
