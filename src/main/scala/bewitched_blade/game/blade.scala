package com.github.fellowship_of_the_bus
package bewitched_blade
package game

import scala.math._
import org.newdawn.slick.{Graphics}
import ui.Drawable


class Blade (xc : Float, yc : Float) {
    var x: Float = xc
    var y: Float = yc
    var ang: Float = 0
    var xVel: Float = 0
    var yVel: Float = 0
    var angVel: Float = 0
    def id = IDMap.bladeID

    val hiltLen = 20
    val hiltWidth = 20
    val bladeLen = 60
    val bladeWidth = 20
    val accel = 0.75f
    val angAccel = 1f // 60 per sec

    private var bx1 = x + 70.0
    private var bx2 = x + 70.0
    private var bx3 = x + -10.0
    private var bx4 = x + -10.0
    private var by1 = y + 10*cos(ang)
    private var by2 = y + -10*cos(ang)
    private var by3 = y + 10*cos(ang)
    private var by4 = y + -10*cos(ang)

    def move(mx : Float, my : Float) = {
   	  var xVec = mx - x
      var yVec = my - y
      var mTheta = atan2(yVec, xVec)
      mTheta = toDegrees(mTheta)

      var dTheta = ((mTheta - ang) + 360) % 360
      if (dTheta < 180) {
    	angVel = min(10.0f, floor(dTheta).asInstanceOf[Float])
      } else {
    	angVel = max(-10.0f, ceil(dTheta - 360).asInstanceOf[Float])
      }

      ang =  (ang + angVel) % 360
      val cAng = cos(toRadians(ang))
	  val sAng = sin(toRadians(ang))
	  bx1 = x + 70*cAng - 10*sAng
	  bx2 = x + 70*cAng + 10*sAng
	  bx3 = x + -10*cAng - 10*sAng
	  bx4 = x + -10*cAng + 10*sAng
	  by1 = y + 70*sAng + 10*cAng
	  by2 = y + 70*sAng - 10*cAng
	  by3 = y + -10*sAng + 10*cAng
	  by4 = y + -10*sAng - 10*cAng

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
    	if (xAcc * xVel < 0) { xAcc *= 1.6 }

    	if (yAcc * yVel < 0) { yAcc *= 1.6 }
    	xVel += xAcc.asInstanceOf[Float]
    	if (abs(xVel) > abs(xVec) && xVel * xVec > 0) {
    	 	xVel = xVec + 0.75f
    	}
    	yVel += yAcc.asInstanceOf[Float]
    	if (abs(yVel) > abs(yVec) && yVel * yVec > 0) {
    		yVel = yVec + 0.75f
    	}

    	x += xVel
    	y += yVel
      
        // if (y >590 ) {
        //   y = 590
        // }

        
      }
    }

    def draw(g: Graphics, i: Drawable) {

        i.setCenterOfRotation(10,10)
        i.setRotation(ang)
        i.draw(x-10, y-10)
        // g.fillOval(bx1.asInstanceOf[Float],by1.asInstanceOf[Float],10,10)
        // g.fillOval(bx2.asInstanceOf[Float],by2.asInstanceOf[Float],10,10)
        // g.fillOval(bx3.asInstanceOf[Float],by3.asInstanceOf[Float],10,10)
        // g.fillOval(bx4.asInstanceOf[Float],by4.asInstanceOf[Float],10,10)
        // g.drawImage(i, x-10, y-10)
    }


    def slope(x1: Double, y1: Double, x2: Double, y2:Double) = {
    	val xDiff = x1 - x2
    	val yDiff = y1 - y2

    	yDiff / xDiff
    }

    def side(x1: Double, y1: Double, x2: Double, y2:Double, x3: Double, y3: Double) = {
    	if(x1 == x2) {
    		x3 - x1
    	} else {
    		((x3 - x1) * slope(x1, y1, x2, y2)) + (y1 - y3)
    	}
    }

    def sameSide(x1: Double, y1: Double, x2: Double, y2:Double, x3: Double, y3: Double, x4: Double, y4: Double) = {
    	(side(x1, y1, x2, y2, x3, y3) * side(x1, y1, x2, y2, x4, y4)) >= 0
    }

    def inRect(x1: Double, y1: Double, x2: Double, y2:Double, x3: Double, y3: Double, x4: Double, y4: Double, x5: Double, y5: Double) = {
    	sameSide(x1,y1,x2,y2,x3,y3,x5,y5) && sameSide(x2,y2,x3,y3,x4,y4,x5,y5) && sameSide(x3,y3,x4,y4,x1,y1,x5,y5) && sameSide(x4,y4,x1,y1,x3,y3,x5,y5)
    }

    def nearSideAng(x1: Double, y1: Double, x2: Double, y2:Double, x3: Double, y3: Double, x4: Double, y4: Double) = {
    	if (abs(x1 - x3) < abs (x2 - x3)) {
    		if (abs(y1 - y3) < abs (y2 - y3)) {
    			atan2(y4 - y1, x4 - x1)
    		} else {
    			atan2(y4 - y2, x4 - x1)
    		}
    	} else {
    		if (abs(y1 - y3) < abs (y2 - y3)) {
    			atan2(y4 - y1, x4 - x2)
    		} else {
   				atan2(y4 - y2, x4 - x2) 			
    		}
    	}
    }

    def power() = {
    	((sqrt((xVel * xVel) + (yVel * yVel)) + abs(angVel)) / 4).asInstanceOf[Float]
    }

    def collision(e: Enemy) {
    	val eSize = max(e.width, e.height)
    	var xVec: Double = e.x - x
      	var yVec: Double = e.y - y
      	var norm = sqrt((xVec * xVec) + (yVec * yVec))
      	if (norm - eSize < 70) {
      		val ex1 = e.x-e.width/2
      		val ex2 = e.x+e.width/2
      		val ey1 = e.y-e.height/2
      		val ey2 = e.y+e.height/2

      		var collide = false
      		var resist: Double = 0
      		if (inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,ex1,ey1)) {
      		 	resist = e.hit(power(), atan2(e.y - ey1, e.x - ex1).asInstanceOf[Float])
      		 	xVec = ex1 - x
      		 	yVec = ey1 - y
      		 	collide = true
      		} else if (inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,ex1,ey2)) {
      			resist = e.hit(power(), atan2(e.y - ey2, e.x - ex1).asInstanceOf[Float])
      			xVec = ex1 - x
      			yVec = ey2 - y
      			collide = true
      		} else if (inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,ex2,ey1)) {
      			resist = e.hit(power(), atan2(e.y - ey1, e.x - ex2).asInstanceOf[Float])
      			xVec = ex2 - x
      			yVec = ey1 - y
      			collide = true
      		} else if (inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,ex2,ey2)) {
      			resist = e.hit(power(), atan2(e.y - ey2, e.x - ex2).asInstanceOf[Float])
      			xVec = ex2 - x
      			yVec = ey2 - y
      			collide = true
      		} 

      		if (collide) {
      			norm = sqrt((xVec * xVec) + (yVec * yVec))
      			var theta = toDegrees(atan2(yVec, xVec))
      			var dTheta = theta - ang
      			ang -= floor(cos(theta) * norm * resist).asInstanceOf[Float]
      			xVec = xVec * sin(theta)
      			yVec = yVec * sin(theta)
      		}

      		if (inRect(ex1,ey1,ex2,ey1,ex2,ey2,ex1,ey2,bx1,by1)) {


      			resist = e.hit(power(), nearSideAng(ex1, ey1, ex2, ey2, bx1, by1, e.x, e.y).asInstanceOf[Float])
      			xVec = bx1 - x
      			yVec = by1 - y
      			collide = true
      		} else if (inRect(ex1,ey1,ex2,ey1,ex2,ey2,ex1,ey2,bx2,by2)) {

      			resist = e.hit(power(), nearSideAng(ex1, ey1, ex2, ey2, bx2, by2, e.x, e.y).asInstanceOf[Float])
      			xVec = bx2 - x
      			yVec = by2 - y
      			collide = true
      		} else if (inRect(ex1,ey1,ex2,ey1,ex2,ey2,ex1,ey2,bx3,by3)) {

      			resist = e.hit(power(), nearSideAng(ex1, ey1, ex2, ey2, bx3, by3, e.x, e.y).asInstanceOf[Float])
      			xVec = bx3 - x
      			yVec = by3 - y
      			collide = true
      		} else if (inRect(ex1,ey1,ex2,ey1,ex2,ey2,ex1,ey2,bx4,by4)) {
      			resist = e.hit(power(), nearSideAng(ex1, ey1, ex2, ey2, bx4, by4, e.x, e.y).asInstanceOf[Float])
      			xVec = bx4 - x
      			yVec = by4 - y
      			collide = true
      		}

      		if (collide) {
      			norm = sqrt((xVec * xVec) + (yVec * yVec))
      			xVel -= (resist * xVec / norm).asInstanceOf[Float]
      			yVec -= (resist * yVec / norm).asInstanceOf[Float]
      		}
      	}
    }


    def collision(p: Projectile) {
      val pSize = max(p.width, p.height)
      var xVec: Double = p.x - x
      var yVec: Double = p.y - y
      var norm = sqrt((xVec * xVec) + (yVec * yVec))
      if (norm - pSize < 70) {
        val px1 = p.x-p.width/2
        val px2 = p.x+p.width/2
        val py1 = p.y-p.height/2
        val py2 = p.y+p.height/2

        var collide = false
        if (inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,px1,py1)
         || inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,px1,py2)
         || inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,px2,py1)
         || inRect(bx1,by1,bx2,by2,bx3,by3,bx4,by4,px2,py2)
         || inRect(px1,py1,px2,py1,px2,py2,px1,py2,bx1,by1)
         || inRect(px1,py1,px2,py1,px2,py2,px1,py2,bx2,by2)
         || inRect(px1,py1,px2,py1,px2,py2,px1,py2,bx3,by3)
         || inRect(px1,py1,px2,py1,px2,py2,px1,py2,bx4,by4)) {
          collide = true
        }

        if (collide) {
          p.xVel *= -1 
          p.yVel *= -1
        }
      }
    }


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
