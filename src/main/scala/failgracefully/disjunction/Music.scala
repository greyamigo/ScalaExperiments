package failgracefully.disjunction

import java.time.{LocalDate, Period}

import scala.util.{Failure, Success, Try}
import scalaz.Validation

trait Classification

case object PercussionInstrument extends Classification

case object Keyboard extends Classification

abstract class Instrument(val classification: Classification)

case object Drums extends Instrument(PercussionInstrument)

case object Patta extends Instrument(PercussionInstrument)

case object Piano extends Instrument(Keyboard)

case class Band(name: String)

case class MemberOfBand(from: LocalDate, membership: Period)

case class Musician(
                     name: String,
                     born: LocalDate,
                     instruments: Seq[Instrument],
                     currentBand: Option[Band] = None,
                     formerBands: Seq[MemberOfBand] = Nil)


object Music {
  def validInstrument(instruments: Seq[Instrument]) = ???
  def validCurrentBand(band: Option[Band]) = ???
  def validName(name: String): Try[String] = ???
  def validateAge(born: LocalDate): Try[LocalDate] = {
    if (born.isAfter(LocalDate.now().minusYears(12)))
      Failure(new IllegalArgumentException("too young"))
    else Success(born)
  }
  // Validate Using Option
  def validateOption(musician: Musician): Option[Musician] = {
    for {
      band <- validCurrentBand(musician.currentBand)
      name <- validName(musician.name)
      born <- validateAge(musician.born)
      instruments <- validInstrument(musician.instruments)
    } yield musician
  }

  def validateTry(musician: Musician): Try[Musician] = {
    for {
      band <- validCurrentBand(musician.currentBand)
      name <- validName(musician.name)
      born <- validateAge(musician.born)
      instruments <- validInstrument(musician.instruments)
    } yield musician
  }
  type StringValidation[T] = Validation[String, T]

  def validateValidation(musician: Musician): StringValidation[Musician] = {
    import scalaz._
    import scalaz.Scalaz._

    def validCurrentBand(band: Option[Band]): StringValidation[Option[Band]] = ???

    def validName(name: String): StringValidation[String] = ???

    def validateAge(born: LocalDate): StringValidation[LocalDate] =
      if (born.isAfter(LocalDate.now().minusYears(12))) "too young".failure
      else born.success

    def validInstrument(instruments: Seq[Instrument]): StringValidation[Seq[Instrument]] = ???

    (validName(musician.name)
      |@| atLeast12(musician.born)
      |@| validInstrument(musician.instruments)
      |@| validCurrentBand(musician.currentBand))(_ => musician)
  }

  def main(args: Array[String])={
    val parra = Band("ParraBoys")
    val mikael = Musician(
      name = "Jishnu Pachakulam",
      born = LocalDate.parse("1988-04-17"),
      instruments = List(Patta, Drums),
      currentBand = Option(parra))

    val badMikael = mikael.copy(born = LocalDate.now.minusYears(2))
      .copy(instruments = Nil)

    validateTry(mikael)
    // -> Success(Musician(Jishnu Pachakulam,1988-04-17, List(Patta, Drums),Some(Band(ParraBoys)),List()))

    validateTry(badMikael)
    // -> Failure(java.lang.IllegalArgumentException: too young)

    validateValidation(mikael)
    // -> Success(Musician(Jishnu Pachakulam,1988-04-17, List(Patta, Drums),Some(Band(ParraBoys)),List()))

    validateValidation(badMikael)
    // -> Failure(too youngat least one instrument)

    //NEL validation
   // Failure(NonEmptyList(too young, at least one instrument))
  }
}
