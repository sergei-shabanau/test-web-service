package com.test

import cats.effect.Async
import cats.instances.list._
import com.test.TestDb.ConnectionIOOps
import doobie._
import doobie.util.transactor.Transactor.Aux

import scala.language.implicitConversions

class TestDb[F[_]]()(implicit val F: Async[F]) {

  implicit val tran: Aux[F, Unit] = Transactor.fromDriverManager[F](
    "com.mysql.jdbc.Driver",
    "jdbc:mysql://localhost/manifest?",
    "root",
    "12345678"
  )

  private[test] val insertQuery: Update[(String, String, String)] =
    Update("INSERT INTO entries VALUES (?, ?, ?)")

  def addEntries(entries: List[(String, String, String)]): ConnectionIO[Int] = {
    insertQuery.updateMany(entries)
  }

  implicit def toConnectionIOOps[A](value: ConnectionIO[A]): ConnectionIOOps[F, A] = new ConnectionIOOps(value)

}

object TestDb {

  type Tran[F[_]] = Aux[F, Unit]

  final class ConnectionIOOps[F[_], A](val self: ConnectionIO[A]) extends AnyVal {

    def exec(implicit F: Async[F], T: Tran[F]): F[A] = T.trans.apply(self)
  }

}
