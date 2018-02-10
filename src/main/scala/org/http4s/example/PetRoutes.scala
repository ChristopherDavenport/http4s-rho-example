package org.http4s.example

import cats.Applicative
import cats.effect.Effect
import org.http4s.rho.{PathBuilder, RhoService}
import org.http4s.rho.swagger.SwaggerSyntax
import org.http4s.Uri
import scala.concurrent.ExecutionContext
import cats.syntax.functor._
import cats.syntax.flatMap._
import org.http4s.example.Models.ResponseMessage
import shapeless._

//import cats.implicits._ // VERY BROKEN DO NOT IMPORT AT THE MOMENT

object PetRoutes {

  def getMeToCompileRoute[F[_] : Effect]: RhoService[F] = {

    val swaggerSyntax = new SwaggerSyntax[F] {}
    import swaggerSyntax._

    new RhoService[F] {
      "We don't want to have a real 'root' route anyway... " **
        GET |>> TemporaryRedirect(Uri(path = "/swagger-ui"))(Applicative[F])
    }
  }



  def petRoute[F[_]](implicit F: Effect[F], ec: ExecutionContext): F[RhoService[F]] = for {
    counter <- fs2.async.refOf[F, Int](0)(F)
  } yield {
    val swaggerSyntax = new SwaggerSyntax[F] {}
    import swaggerSyntax._

    new RhoService[F]{


      val hello : PathBuilder[F, HNil] = GET / "hello"

      "Simple hello world route" **
        hello |>> Ok(ResponseMessage("Hello world!"))

      "Returns Hellow Based on Request" **
        hello / pathVar[String] |>> { name: String => Ok(ResponseMessage(s"Hello, $name"))}

      val pet : PathBuilder[F, HNil] = GET / "pet"

      "A variant of the hello route that takes an Int param" **
        pet / pathVar[Int] |>> { i: Int => Ok(ResponseMessage(s"You returned $i")) }

      "This gets a simple counter for the number of times this route has been requested" **
        GET / "counter" |>> {
        counter.modify(_ + 1).map(_.now).flatMap(i => Ok(ResponseMessage(s"The number is $i")))
      }
    }

  }

}
