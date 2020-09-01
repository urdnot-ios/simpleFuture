package com.urdnot.utils

import com.urdnot.utils.RunFuture.structuredString
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class FuturesSuite extends AsyncFlatSpec {

  val rawDataString = List("parse, me, by, csv,0", "parse, me, by, csv, 1", "parse, me, by, csv, 2")
  val structuredDataString = List(
    structuredString("parse", " me", " by", " csv",0),
    structuredString("parse", " me", " by", " csv",1),
    structuredString("parse", " me", " by", " csv",2)
  )
  val structuredDataStringEither: List[Either[String, structuredString]] = List(
    Right(structuredString("parse", " me", " by", " csv",0)),
    Left("couldn't parse: parse, me, by, csv, 1"),
    Left("couldn't parse: parse, me, by, csv, 2"))
//  valid entry structuredString(parse, me, by, csv,0)
//  invalid entry: couldn't parse: parse, me, by, csv, 1
//  invalid entry: couldn't parse: parse, me, by, csv, 2

  behavior of "parseStringFuture"

  it should "Parse out the strings into case classes" in {
    val futureSum: Future[List[RunFuture.structuredString]] = FutureMethods.parseStringFuture(rawDataString)
    // You can map assertions onto a Future, then return
    // the resulting Future[Assertion] to ScalaTest:
    futureSum map { x =>
      assert(x == structuredDataString)
    }
  }

  behavior of "parseStringEither"
  it should "Parse out the strings into case classes but return errors for the invalid entries" in {
    val futureSum:  Future[List[Either[String, structuredString]]] = FutureMethods.parseStringEither(rawDataString)
    // You can map assertions onto a Future, then return
    // the resulting Future[Assertion] to ScalaTest:
    futureSum map { x =>
      assert(x == structuredDataStringEither)
    }
  }
}
