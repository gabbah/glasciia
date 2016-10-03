package se.gigurra.glasciia.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import se.gigurra.math.Vec2

/**
  * Created by johan on 2016-10-03.
  */
case class Pov4W(left: Int = Keys.LEFT,
                 right: Int = Keys.RIGHT,
                 up: Int = Keys.UP,
                 down: Int = Keys.DOWN) {

  private def value(dir: Int): Int = if (Gdx.input.isKeyPressed(dir)) 1 else 0

  def dir: Vec2[Int] = Vec2(
    x = value(right) - value(left),
    y = value(up) - value(down)
  )
}