package se.gigurra.glasciia

import se.gigurra.math.Vec2

/**
  * Created by johan on 2016-09-19.
  */
case class CameraConf(pos: Vec2[Float],
                      size: Float,
                      scaleType: ScaleType)

sealed trait ScaleType
object ScaleType {
  case object Conformal extends ScaleType
  case object Window extends ScaleType
}
