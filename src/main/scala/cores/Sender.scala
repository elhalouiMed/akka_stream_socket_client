package cores

import actors._
import akka.actor.{ActorSystem, Props}

object Sender extends App with Logging {
  implicit val system = ActorSystem()
  implicit val stateActor = system.actorOf(Props(new StateActor()))
  var actorNumbers = 5000
  for(i<- 0 to actorNumbers){
    var clientActor = system.actorOf(Props(new ClientActor()))
    clientActor ! Start
  }


}

