package se.gigurra.glasciia

import ApplicationEvent._
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix4
import se.gigurra.glasciia.conf.{CameraConf, GlConf, ScaleType, WindowConf}
import se.gigurra.glasciia.impl.LwjglImplementation
import se.gigurra.math.Vec2

/**
  * Created by johan on 2016-09-26.
  */
object OpenWindowTest {

  def main(args: Array[String]): Unit = {

    val initialWindowConf = WindowConf(
      position = Vec2(100,100),
      size = Vec2(640, 480),
      resizable = false,
      maximized = false,
      title = "Test Window"
    )

    val initialCameraConf = CameraConf(
      pos = Vec2(0.0f, 0.0f),
      size = 2.0f,
      scaleType = ScaleType.Conformal
    )

    val initialGlConf = GlConf(
      vsync = true,
      msaa = 4,
      foregroundFpsCap = None,
      backgroundFpsCap = Some(30)
    )

    val window = new Window(initialWindowConf, initialCameraConf, initialGlConf) with LwjglImplementation

    implicit lazy val canvas = Canvas() // Needs to be lazy because of libgdx sillyness (cannot create resources before Init event is fired)

    window.handleEvents {
      case Render =>
        canvas.drawFrame(background = Color.GRAY) {
        }
      case event => // mouse, kb, resize, ..
        println(event)
    }

  }
}