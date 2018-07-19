package cores

import actors.BytesPerSecondActor
import akka.actor.{ActorSystem, Props}
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

/**
  * - source: iterator from file (lazy iterator)
  * - sink: just log
  * - server connnection: req -> resp flow
  * - *message driven*: materializer: actors
  */
object Sender extends App with Logging {
  implicit val system = ActorSystem()
  val bytesPerSecondActor = system.actorOf(Props[BytesPerSecondActor])
  val serverConnection = Tcp().outgoingConnection("localhost", 9182)

  val getLines = () => scala.io.Source.fromFile("/home/elhaloui/Work/atos/testCodes/reactive-akka-pres/data/2008.csv").getLines()

  val linesSource = Source(getLines).map { line =>
    bytesPerSecondActor ! line.length
    ByteString(line + "\n")
  }
  val logCompleteSink = Sink.onComplete(r => logger.info("Completed with: " + r))

  val flow = linesSource
    .via(serverConnection)
    .to(logCompleteSink)

  implicit val mat = ActorFlowMaterializer()
  flow.run()
}

