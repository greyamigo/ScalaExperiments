import scalaz._ , Scalaz._
val yes = 3.14.successNel[String]

val doh = "Error".failureNel[Double]

val y = 3.14.right[String]
val n = "Wrong".left[Double]
y

(yes |@| yes){_ + _}
(yes |@| doh){_ + _}
(doh |@| yes){_ + _}
(doh |@| doh){_ + _}
