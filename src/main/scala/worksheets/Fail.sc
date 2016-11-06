import scalaz._, Scalaz._

"event 1 ok".success[String]
("event 1 ok".success[String] |@|
  "event 2 failed!".failure[String] |@|
  "event 3 failed!".failure[String]) {_ + _ + _}

def toInts(maybeInts : List[String]): ValidationNel[Throwable, List[Int]] = {
  val validationList = maybeInts map { s =>
    Validation.fromTryCatchNonFatal(s.toInt :: Nil).toValidationNel
  }
  validationList reduce (_ +++ _)
}

def toInts2(maybeInts : List[String])= maybeInts map ( s =>
    Validation.fromTryCatchNonFatal(s.toInt))

toInts2(List("SS","5","25","2s","855"))
