package actors

import akka.actor.Actor

import scala.concurrent.duration._

class StateActor extends Actor {
  override def preStart() = {
    import context.dispatcher
    context.system.scheduler.schedule(1.second, 1.second, self, Tick)
  }

  private var bytes = 0
  var connxNumbers = 0

  override def receive = {
    case NewConn => {
      connxNumbers += 1
      println(s"new connexion ${connxNumbers}")
    }
    case EndConn => {
      connxNumbers -=1
      println(s"end connexion ${connxNumbers}")
    }
    case Tick =>{
      println(s"their is ${connxNumbers} alive ")

    }


  }
}

object Tick
object NewConn
object EndConn