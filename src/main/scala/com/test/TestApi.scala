package com.test

import cats.effect.Async
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.test.TestApi._
import com.test.implicits._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

final case class TestApi[F[_]]() extends Http4sDsl[F] {

  def service(implicit F: Async[F]): HttpService[F] = {

    val db: TestDb[F] = new TestDb()
    import db.{tran, toConnectionIOOps}

    HttpService[F] {
      case GET -> Root / "test" =>
        Ok(Result("returned"))

      case request @ POST -> Root / "test" =>
        for {
          req <- request.as[AddRequest]
          _   <- db.addEntries(req.keys.map { key => (req.bucket, key, req.timestamp) }).exec
          res <- Ok(Result(s"posted to '${req.bucket}'"))
        } yield res
    }
  }
}

object TestApi {

  case class AddRequest(bucket: String, keys: List[String], timestamp: String)

  object AddRequest {
    implicit val decoder: Decoder[AddRequest] = deriveDecoder
  }

  case class Result(message: String)

  object Result {
    implicit val encoder: Encoder[Result] = deriveEncoder
  }

}
