package org.http4s.example

import io.circe._
import io.circe.generic.semiauto._

object Models {

  final case class ResponseMessage(message: String)
  object ResponseMessage{
    implicit val responseMessageEnc: Encoder[ResponseMessage] = deriveEncoder[ResponseMessage]
  }

  case class Pet(id: String, age: Int)
  object Pet {
    implicit val petDec : Decoder[Pet] = deriveDecoder[Pet]
    implicit val petEnc : Encoder[Pet] = deriveEncoder[Pet]
  }
}
