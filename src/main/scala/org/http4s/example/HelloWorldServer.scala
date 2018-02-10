package org.http4s.example

import cats.effect.IO
import fs2.Stream
import fs2.StreamApp
import io.circe._
import org.http4s._
import org.http4s.circe._
import cats.implicits._
import org.http4s.rho.swagger.syntax.io._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.CORS

import scala.concurrent.ExecutionContext.Implicits.global

object HelloWorldServer extends StreamApp[IO] with Http4sDsl[IO] {


  def stream(args: List[String], requestShutdown: IO[Unit]) : Stream[IO, StreamApp.ExitCode] = for {
    rhoService <- Stream.eval(PetRoutes.petRoute[IO])
    middleware = createRhoMiddleware()

    service = CORS(rhoService.toService(middleware))


    out <- BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(service, "/")
      .serve
  } yield out

}
