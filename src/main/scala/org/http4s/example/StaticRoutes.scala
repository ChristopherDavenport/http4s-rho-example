package org.http4s.example

import cats.effect._
import cats.implicits._
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.http4s.StaticFile
import org.http4s.{Request, Response}

object StaticRoutes {

  def service[F[_]: Effect]: HttpService[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._

    val swaggerUiDir = "/swagger-ui"

    def fetchResource(path: String, req: Request[F]): F[Response[F]] = {
      StaticFile.fromResource(path, Some(req)).getOrElseF(NotFound())
    }

    HttpService[F]{
      // Swagger User Interface
      case req @ GET -> Root / "css" / _       => fetchResource(swaggerUiDir + req.pathInfo, req)
      case req @ GET -> Root / "images" / _    => fetchResource(swaggerUiDir + req.pathInfo, req)
      case req @ GET -> Root / "lib" / _       => fetchResource(swaggerUiDir + req.pathInfo, req)
      case req @ GET -> Root / "swagger-ui"    => fetchResource(swaggerUiDir + "/index.html", req)
      case req @ GET -> Root / "swagger-ui.js" => fetchResource(swaggerUiDir + "/swagger-ui.min.js", req)
    }

  }

}
