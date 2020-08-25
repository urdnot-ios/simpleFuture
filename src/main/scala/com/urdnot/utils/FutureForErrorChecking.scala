package com.urdnot.utils

import com.urdnot.utils.RunFuture.structuredString
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FutureForErrorChecking {
  /*
  A simple Future: can you extract the data? There's no real error handling here, just
  a trim to remove unwanted spaces.
  This is non-blocking, but not resilient.
   */
  def parseStringFuture(stringList: List[String]): Future[List[structuredString]] = Future {
    stringList.map { x =>
      structuredString(firstWord = x.split(",")(0),
        secondWord = x.split(",")(1),
        thirdWord = x.split(",")(2),
        fourthWord = x.split(",")(3),
        fifthWord = x.split(",")(4).trim.toInt)
    }
  }

  /*
  Future with an Either thrown in. This will handle bad parsing by returning a string as a Fail/Left
  message. Non-blocking *and* resilient.
   */
  def parseStringEither(stringList: List[String]): Future[List[Either[String, structuredString]]] = Future {
    stringList.map { x: String =>
      try {
        Right(structuredString (
          firstWord = x.split(",")(0),
          secondWord = x.split(",")(1),
          thirdWord = x.split(",")(2),
          fourthWord = x.split(",")(3),
          fifthWord = x.split(",")(4).toInt
        ))
      } catch {
        case e: Exception => println(e); Left("couldn't parse: " + x)
      }
    }
  }
}
