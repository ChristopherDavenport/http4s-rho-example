package org.http4s.example

import cats.Applicative
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

object Models {

  final case class ResponseMessage(message: String)
  object ResponseMessage{
    implicit val responseMessageEnc: Encoder[ResponseMessage] = deriveEncoder[ResponseMessage]
    implicit def responseMessageEncoder[F[_]: Applicative]: EntityEncoder[F, ResponseMessage] = jsonEncoderOf[F, ResponseMessage](EntityEncoder[F, String], Applicative[F], responseMessageEnc)
  }

  case class Pet(id: String, age: Int)
  object Pet {
    implicit val petDec : Decoder[Pet] = deriveDecoder[Pet]
    implicit val petEnc : Encoder[Pet] = deriveEncoder[Pet]
  }
}
