package disjunction

object DangerousService {
  def queryNextNumber: Long = {
    val source = Math.round(Math.random * 100)
    if (source <= 60) source
    else throw new Exception("The generated number is too big!")
  }

  def queryNextNumber1: Option[Long] = {
    val source = Math.round(Math.random * 100)
    if (source <= 60) Some(source)
    else None
  }
  def queryNextNumber2: Either[Exception, Long] = {
    val source = Math.round(Math.random * 100)
    if (source <= 60) Right(source)
    else Left(new Exception("The generated number is too big!"))
  }

  import scalaz._, Scalaz._

  def queryNextNumber3: Exception \/ Long = {
    val source = Math.round(Math.random * 100)
    if (source <= 60) \/.right(source)
    else \/.left(new Exception("The generated number is too big!"))
  }
  def queryNextNumber4: Throwable \/ Long = \/.fromTryCatchNonFatal {
    val source = Math.round(Math.random * 100)
    if (source <= 60) source
    else throw new Exception("The generated number is too big!")
  }
  class GenerationException(number: Long, message: String) extends Exception(message)
  implicit val geNotNothing = NotNothing.isNotNothing[GenerationException]

  def queryNextNumber5: GenerationException \/ Long = \/.fromTryCatchThrowable {
    val source = Math.round(Math.random * 100)
    if (source <= 60) source
    else throw new GenerationException(source, "The generated number is too big!")
  }
}