package com.test

import cats.effect.{Effect, IO}
import fs2.{Stream, StreamApp}
import org.http4s.dsl.impl.Root
import org.http4s.server.blaze.BlazeBuilder

object TestWebService extends TestWebServiceApp[IO]

class TestWebServiceApp[F[_]](implicit val F: Effect[F]) extends StreamApp[F] {

  override def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, StreamApp.ExitCode] =
    BlazeBuilder[F]
      .bindHttp(10001, "localhost")
      .mountService(TestApi().service, Root.toString())
      .serve(F, scala.concurrent.ExecutionContext.global)
}
