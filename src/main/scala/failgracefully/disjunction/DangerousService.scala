package failgracefully.disjunction

import java.lang.Exception

/* Different ways to fail gracefully and or exposing exceptions */

object DangerousService {

  val source = Math.round(Math.random*100)

  // Exception
  def queryNextNumber: Long = {
    if (source <= 60) source
    else throw new Exception("The generated number is too big!")
  }
  // Option
  def queryNextNumber1: Option[Long] = {
    if (source <= 60) Some(source)
    else None
  }

  def queryNextNumberStupidTry: Long  = {
    try {
      if (source <= 60) source
      else throw new Exception("The generated number is too big!")
    } catch {
      case _=> 0
    }
  }
  /*
   * Option v/s Try
   * Where Option[A] is a container for a value of type A that may be present or not, Try[A] represents a computation that may result in a value of type A, if it is successful, or in some Throwable if something has gone wrong.
   * Instances of such a container type for possible errors can easily be passed around between concurrently executing parts of your application.
   *
   * There are two different types of Try:
   * 1. an instance of Try[A] represents a successful computation, it is an instance of Success[A], simply wrapping a value of type A.
   * 2. on the other hand, it represents a computation in which an error has occurred, it is an instance of Failure[A], wrapping a Throwable, i.e. an exception or other kind of error.
   */

  /* Either v/s Option / Try
   *
   * val x = Option("String")
   * x.isDefined
   * x.isEmpty
   * x.flatMap()
   * x.fold()
   * x.foldLeft()
   * x.foldRight()
   *
   * val y :Either[String, Int]= Left("Not Int but String")
   * y.isLeft
   * y.isRight
   * y.fold()
   * y.left.map()
   *
   * You can ask an instance of Either if it isLeft or isRight
   *
   * You cannot, at least not directly, use an Either instance like a collection, the way you are familiar with from Option and Try.
   * This is because Either is designed to be unbiased.
   *
   * Try / Option is success-biased: it offers you map, flatMap and other methods
   * that all work under the assumption that the Try is a Success, and if that’s not the case, they effectively don’t do anything, returning the Failure as-is.
   *
   * The fact that Either is unbiased means that you first have to choose whether you want to work under the assumption that it is a Left or a Right.
   * By calling left or right on an Either value, you get a LeftProjection or RightProjection, respectively, which are basically left- or right-biased wrappers for the Either.
   */


  def queryNextNumber2: Either[Exception, Long] = {
    if (source <= 60) Right(source)
    else Left(new Exception("The generated number is too big!"))
  }

  //Entering into ScalaZ

  import scalaz._, Scalaz._

  //Disjunction
  def queryNextNumber3: Exception \/ Long = {
    if (source <= 60) \/.right(source)
    else \/.left(new Exception("The generated number is too big!"))
  }
  //.fromTryCatchNonFatal in disjunction
  def queryNextNumber4: Throwable \/ Long = \/.fromTryCatchNonFatal {
    if (source <= 60) source
    else throw new Exception("The generated number is too big!")
  }

  /* .fromTryCatchThrowable works in a similar way as of .fromTryCatchNonFatal,
   * but since it returns a custom Throwable subtype,
   * it also requires that a NonNothing implicit value is defined in the scope
   */
  class GenerationException(number: Long, message: String) extends Exception(message)

  implicit val geNotNothing = NotNothing.isNotNothing[GenerationException]

  def queryNextNumber5: GenerationException \/ Long = \/.fromTryCatchThrowable {
    if (source <= 60) source
    else throw new GenerationException(source, "The generated number is too big!")
  }
}