package org.http4s.example

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult
import scala.concurrent.ExecutionContext.Implicits.global
import org.http4s.rho.swagger.syntax.io._

class HelloWorldSpec extends org.specs2.mutable.Specification {
  "HelloWorld" >> {
    "return 200" >> {
      uriReturns200()
    }
    "return hello world" >> {
      uriReturnsHelloWorld()
    }
  }
  val middleware = createRhoMiddleware()

  private[this] val retHelloWorld: Response[IO] = {
    val getHW = Request[IO](Method.GET, Uri.uri("/hello/world"))
    PetRoutes.petRoute[IO].flatMap(_.toService(middleware).orNotFound(getHW)).unsafeRunSync()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retHelloWorld.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsHelloWorld(): MatchResult[String] =
    retHelloWorld.as[String].unsafeRunSync() must beEqualTo("{\"message\":\"Hello, world\"}")
}
