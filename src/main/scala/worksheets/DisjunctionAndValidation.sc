

import scalaz._, Scalaz._
val testList = List("23","SS","5","25","2s","855")
def toInts2(maybeInts : List[String])= maybeInts map ( s =>
    Validation.fromTryCatchNonFatal(s.toInt))

def toInts3(maybeInts : List[String])= {
 maybeInts map (s =>
    \/.fromTryCatchNonFatal(s.toInt))
}

def toInts4(maybeInts : List[String]): ValidationNel[Throwable, List[Int]] = {
  val validationList = maybeInts map { s =>
    Validation.fromTryCatchNonFatal(s.toInt :: Nil).toValidationNel
  }
  validationList reduce (_ +++ _)
}

toInts2(testList)
toInts3(testList)
toInts4(testList)
def toInts(maybeInts : List[String]) = maybeInts map (_.toInt)
def toInt(maybeInt : String) = maybeInt.toInt
List(
\/.fromTryCatchNonFatal(toInt("5")),
\/.fromTryCatchNonFatal(toInt("xx")),
\/.fromTryCatchNonFatal(toInt("55")),
\/.fromTryCatchNonFatal(toInt("yy"))
).map(println)


List(
 Validation.fromTryCatchNonFatal(toInt("5")),
 Validation.fromTryCatchNonFatal(toInt("xx")),
 Validation.fromTryCatchNonFatal(toInt("55")),
 Validation.fromTryCatchNonFatal(toInt("yy"))
).map(println)


for{
  _ <- \/.fromTryCatchNonFatal(toInts(testList))
  _ <- \/.fromTryCatchNonFatal(toInts(List("2")))
} yield ("done")


for{
  _ <- Validation.fromTryCatchNonFatal(toInts(testList))
  _ <- Validation.fromTryCatchNonFatal(toInts(List("2")))
} yield ("done")


(Validation.fromTryCatchNonFatal("SS".toInt).toValidationNel |@| Validation.fromTryCatchNonFatal("ABCD".toInt).toValidationNel) { _ + _}
