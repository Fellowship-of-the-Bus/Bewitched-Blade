package com.github.fellowship_of_the_bus.bewitched_blade.game
import scala.math.{atan2, toDegrees, sqrt}
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
    val accel = 1
    val angAccel = 16 // 960 per sec

    def move(mx : Float, my : Float) = {
    	var xVec = mx - x
    	var yVec = my - y
    	var mTheta = atan2(yVec, xVec)
    	mTheta = toDegrees(mTheta)

    	var dTheta = ((mTheta - ang) + 360) % 360
    	if (dTheta < 180) {
    		angVel += angAccel
    	} else {
    		angVel -= angAccel
    	}

        if (angVel >  dTheta) {
            ang = mTheta.asInstanceOf[Float]
        } else {
            ang =  (ang + angVel) % 360
        }

    	var xAcc = xVec / sqrt((xVec * xVec) + (yVec * yVec)) * accel
    	var yAcc = yVec / sqrt((xVec * xVec) + (yVec * yVec)) * accel
    	xVel += xAcc.asInstanceOf[Float]
    	yVel += yAcc.asInstanceOf[Float]
    	x += xVel
    	y += yVel
    }

    def draw(g: Graphics, i: Image) {
        i.setRotation(ang)
        g.drawImage(i, x, y)
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