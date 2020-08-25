package com.urdnot.utils

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object RunFuture extends App {
  /*
  First, setup a sample list of strings to be parsed. This would normally come from a file or a Kafka topic
   */
  val dataString = List("parse, me, by, csv,0", "parse, me, by, csv, 1", "parse, me, by, csv, 2")

  /*
  The target case class of the structured data
   */
  case class structuredString(
                               firstWord: String,
                               secondWord: String,
                               thirdWord: String,
                               fourthWord: String,
                               fifthWord: Int
                             )

  /*
  call the object methods with the data and print the reply.
  Because we're using Future the order of the prints is not guaranteed
   */
  FutureForErrorChecking.parseStringFuture(dataString).onComplete {
  case Success(x) => {
    println(s"Future: ${x}")
  }
  case Failure(e) => println("Failed to run the future: " + e.printStackTrace())
  }

  FutureForErrorChecking.parseStringEither(dataString).onComplete {
    case Success(x) => x.map {
      case Right(valid) => println("valid entry " + valid)
      case Left(invalid) => println("invalid entry: " + invalid)
    }
    case Failure(e) => println("Failed to run the future: " + e.printStackTrace())
  }
}
