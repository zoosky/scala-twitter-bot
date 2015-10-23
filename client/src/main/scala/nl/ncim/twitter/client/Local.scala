package nl.ncim.twitter.client

import akka.actor._

object Local extends App {

  implicit val system = ActorSystem("LocalSystem")
  val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")  // the local actor
  localActor ! "START"                                                     // start the action

  //Step 1) Sent some messages to the server
  //Step 2) Create a case msg to receive rss feeds from the server
  //Step 3) Get some

}

class LocalActor extends Actor {

  // create the remote actor
  //TODO check the ip adress of the remote server
  val remote = context.actorSelection("akka://HelloRemoteSystem@127.0.0.1:5150/user/RemoteActor")
  var counter = 0

  def receive = {
    case "START" =>
      remote ! "Hello from the LocalActor"
    case msg: String =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
  }
}