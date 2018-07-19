package actors

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem}
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source, Tcp}
import akka.util.ByteString
// import cores.Sender.{bytesPerSecondActor, logger}

object Start

class ClientActor (implicit system :ActorSystem , implicit val stateActor: ActorRef) extends Actor with ActorLogging
{
  override def preStart() = {
    //context.system.scheduler.schedule(1.second, 1.second, self, Tick)
  }

  override def receive = {
    case Start =>{
      val serverConnection = Tcp().outgoingConnection("localhost", 9182)
      stateActor ! NewConn
      val getLines = () => scala.io.Source.fromFile("/home/elhaloui/Work/atos/testCodes/reactive-akka-pres/data/2008.csv").getLines()
      val linesSource = Source(getLines).map { line =>
        ByteString(line + "\n")
      }
      val logCompleteSink = Sink.onComplete(r =>
        {
          stateActor ! EndConn
          log.info("Completed with: " + r)
        }
      )
      val flow = linesSource
        .via(serverConnection)
        .to(logCompleteSink)
      implicit val mat = ActorFlowMaterializer()
      flow.run()
    }

  }



}