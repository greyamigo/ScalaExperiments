package futures

import scala.concurrent.{Await, Future, Promise}
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


/*
 FROM : https://stackoverflow.com/a/41657239/1414151
 */
/**
  * Simple class to encapsulate work, the important element here is the future
  * you can ignore the rest
  */
case class Work(id:String, workTime:Long = 100) {
  def doWork(): Future[String] = Future {
    println(s"Starting $id")
    Thread.sleep(workTime)
    println(s"End $id")
    s"$id ready"
  }
}

/**
  * SimpleSequencer is the one by one execution, the promise is the element
  * who allow to the sequencer to work, pay attention to it.
  *
  * Exceptions are ignore, this is not production code
  */
object SimpleSequencer {
  private def sequence(works:Seq[Work], results:Seq[String], p:Promise[Seq[String]]) : Unit = {
    works match {
      case Nil => p.tryComplete(Try(results))
      case work::tail => work.doWork() map {
        result => sequence(tail, results :+ result, p)
      }
    }
  }

  def sequence(works:Seq[Work]) : Future[Seq[String]] = {
    val p = Promise[Seq[String]]()
    sequence(works, Seq.empty, p)
    p.future
  }
}

/**
  * MultiSequencer fire N works at the same time
  */
object MultiSequencer {
  private def sequence(parallel:Int, works:Seq[Work], results:Seq[String], p:Promise[Seq[String]]) : Unit = {
    works match {
      case Nil => p.tryComplete(Try(results))
      case work =>
        val parallelWorks: Seq[Future[String]] = works.take(parallel).map(_.doWork())
        Future.sequence(parallelWorks) map {
          result => sequence(parallel, works.drop(parallel), results ++ result, p)
        }
    }
  }

  def sequence(parallel:Int, works:Seq[Work]) : Future[Seq[String]] = {
    val p = Promise[Seq[String]]()
    sequence(parallel, works, Seq.empty, p)
    p.future
  }

}


object Sequencer {

  def main(args: Array[String]): Unit = {
    val works = Seq.range(1, 10).map(id => Work(s"w$id"))
    val p = Promise[Unit]()

    val f = MultiSequencer.sequence(4, works) map {
      resultFromMulti =>
        println(s"MultiSequencer Results: $resultFromMulti")
        SimpleSequencer.sequence(works) map {
          resultsFromSimple =>
            println(s"MultiSequencer Results: $resultsFromSimple")
            p.complete(Try[Unit]())
        }
    }

    Await.ready(p.future, Duration.Inf)
  }
}
