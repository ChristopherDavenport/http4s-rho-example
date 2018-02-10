package org.http4s.example

import cats.effect.Sync
import cats.implicits._

object StaticRoutes {

  def service[F[_]: Sync]

}
