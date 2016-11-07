import scalaz._ , Scalaz._


val yes = 3.14.successNel[String]
val doh = "Error".failNel[Double]

def addTwo(x: Double, y: Double) = x + y

(yes |@| yes)(addTwo) // Success(6.28)
(doh |@| doh)(addTwo) // Failure(NonEmptyList(Error, Error))

// or shorthand
(yes |@| yes){_ + _} // Success(6.28)
(yes |@| doh){_ + _} // Failure(NonEmptyList(Error))
(doh |@| yes){_ + _} // Failure(NonEmptyList(Error))
(doh |@| doh){_ + _} // Failure(NonEmptyList(Error, Error))

//http://johnkurkowski.com/posts/accumulating-multiple-failures-in-a-ValidationNEL/