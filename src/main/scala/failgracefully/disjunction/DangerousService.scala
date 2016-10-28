package failgracefully.disjunction

/* Different ways to fail gracefully and or exposing exceptions */

object DangerousService {
  // Exception
  def queryNextNumber: Long = {
    val source = Math.round(Math.random * 100)
    if (source <= 60) source
    else throw new Exception("The generated number is too big!")
  }
  // Option
  def queryNextNumber1: Option[Long] = {
    val source = Math.round(Math.random * 100)
    if (source <= 60) Some(source)
    else None
  }

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
    val source = Math.round(Math.random * 100)
    if (source <= 60) Right(source)
    else Left(new Exception("The generated number is too big!"))
  }

  //Entering into ScalaZ

  import scalaz._, Scalaz._

  //Disjunction
  def queryNextNumber3: Exception \/ Long = {
    val source = Math.round(Math.random * 100)
    if (source <= 60) \/.right(source)
    else \/.left(new Exception("The generated number is too big!"))
  }
  //.fromTryCatchNonFatal in disjunction
  def queryNextNumber4: Throwable \/ Long = \/.fromTryCatchNonFatal {
    val source = Math.round(Math.random * 100)
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
    val source = Math.round(Math.random * 100)
    if (source <= 60) source
    else throw new GenerationException(source, "The generated number is too big!")
  }
}