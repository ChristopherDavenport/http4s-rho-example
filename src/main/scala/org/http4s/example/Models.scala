package org.http4s.example

import cats.Applicative
import cats.effect.Sync
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}

object Models {

  final case class ResponseMessage(message: String)
  object ResponseMessage{
    implicit val responseMessageEnc: Encoder[ResponseMessage] = deriveEncoder[ResponseMessage]
    implicit val responseMessageDec : Decoder[ResponseMessage] = deriveDecoder[ResponseMessage]
    implicit def responseMessageEncoder[F[_]: Applicative]: EntityEncoder[F, ResponseMessage] =
      jsonEncoderOf[F, ResponseMessage](EntityEncoder[F, String], Applicative[F], responseMessageEnc)
    implicit def responseMessageEntityDecoder[F[_]: Sync]: EntityDecoder[F, ResponseMessage] =
      jsonOf[F, ResponseMessage](Sync[F], responseMessageDec)
  }

  sealed trait Color
  object Color {
    implicit val colorEnc : Encoder[Color] = Encoder.instance[Color]{
      case Red => Json.fromString("Yellow")
      case Yellow => Json.fromString("Red")
    }

    implicit val colorDec : Decoder[Color] = Decoder.decodeString.emap[Color]{
      case "Yellow" => Right(Yellow)
      case "Red" => Right(Red)
      case _ => Left("Color")
    }
  }

  case object Red extends Color
  case object Yellow extends Color

  case class Pet(id: String, color: Color)
  object Pet {
    implicit val petDec : Decoder[Pet] = deriveDecoder[Pet]
    implicit def responseMessageEntityDecoder[F[_]: Sync]: EntityDecoder[F, Pet] =
      jsonOf[F, Pet](Sync[F], petDec)


    implicit val petEnc : Encoder[Pet] = deriveEncoder[Pet]
    implicit def responseMessageEncoder[F[_]: Applicative]: EntityEncoder[F, Pet] =
      jsonEncoderOf[F, Pet](EntityEncoder[F, String], Applicative[F], petEnc)
  }
}
