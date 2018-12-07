package com.test

import cats.Applicative
import cats.effect.Sync
import io.circe.{Decoder, Encoder}
import org.http4s.{EntityDecoder, EntityEncoder, circe}

object implicits {

  implicit def jsonEntityDecoder[F[_]: Sync, A](implicit decoder: Decoder[A]): EntityDecoder[F, A] =
    circe.jsonOf[F, A]

  implicit def jsonEntityEncoder[F[_], A](implicit encoder: Encoder[A], F: Applicative[F]): EntityEncoder[F, A] =
    circe.jsonEncoderOf[F, A](EntityEncoder.stringEncoder[F], F, encoder)

}
