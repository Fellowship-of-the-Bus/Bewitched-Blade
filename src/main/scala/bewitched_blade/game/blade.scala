package com.github.fellowship_of_the_bus.bewitched_blade.game
import scala.math.{atan2, toDegrees, sqrt, abs, min, floor,ceil, max}
import org.newdawn.slick.{Graphics, Image}

class Blade (xc : Float, yc : Float) {
    var x: Float = xc
    var y: Float = yc
    var ang: Float = 0
    var xVel: Float = 0
    var yVel: Float = 0
    var angVel: Float = 0
    def id = 1

    val hiltLen = 20
    val hiltWidth = 20
    val bladeLen = 60
    val bladeWidth = 20
    val accel = 0.75f
    val angAccel = 1f // 60 per sec

    def move(mx : Float, my : Float) = {
   	  var xVec = mx - x
      var yVec = my - y
      var mTheta = atan2(yVec, xVec)
      mTheta = toDegrees(mTheta)

      var dTheta = ((mTheta - ang) + 360) % 360
      if (dTheta < 180) {
    	angVel = min(10.0f, floor(dTheta).asInstanceOf[Float])
      } else {
    	angVel = max(-10.0f, ceil(dTheta).asInstanceOf[Float])
      }

      ang =  (ang + angVel) % 360

      var norm = sqrt((xVec * xVec) + (yVec * yVec)) 
      if (norm < 0.01) {
          norm = 0.1
      }
      if (norm < 70) {
         xVel = 0
         yVel = 0
      } else {
    	var xAcc = xVec / norm * accel
    	var yAcc = yVec / norm * accel
    	if (xAcc * xVel < 0) { xAcc *= 2 }

    	if (yAcc * yVel < 0) { yAcc *= 2 }
    	xVel += xAcc.asInstanceOf[Float]
    	if (abs(xVel) > abs(xVec)) {
    		xVel = xVec + 1
    	}
    	yVel += yAcc.asInstanceOf[Float]
    	if (abs(yVel) > abs(yVec)) {
    		yVel = yVec + 1
    	}

    	x += xVel
    	y += yVel
      
        if (y >590 ) {
          y = 590
          yVel = 0
        }
      }
    }

    def draw(g: Graphics, i: Image) {

        i.setCenterOfRotation(10,10)
        i.setRotation(ang)
        g.drawImage(i, x-10, y-10)
    }

    // def topLeftCoord() = (x-width/2, y-height/2)
    // def bottomRightCoord() = (x+width/2, y+height/2)

    // def collision(cand: GameObject) = if (active && cand.active) {
    //     val (x1, y1) = topLeftCoord
    //     val (x2, y2) = bottomRightCoord
    //     val (cx1, cy1) = cand.topLeftCoord
    //     val (cx2, cy2) = cand.bottomRightCoord
        
    //     def inRange(v: Float, min: Float, max: Float) = {
    //       (v >= min) && (v <= max)
    //     }

    //     val xOver = inRange(cx1,x1,x2) || inRange(x1, cx1, cx2)
    //     val yOver = inRange(cy1,y1,y2) || inRange(y1, cy1, cy2)

    //     xOver && yOver
    // } else false
}
