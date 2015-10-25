package nl.ncim.twitter.server

import akka.actor._

import nl.ncim.twitter.server.TwitterActor.TweetLine

import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken


object TwitterActor {
  case class TweetLine(title: String, shortUrl: String)
}

trait TwitterInstance {
  val twitter = new TwitterFactory().getInstance
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("4rRwPc7G49ZMhseyeaz1G717b", "lwihhBWQzkkvaIxnDaqEsn5fjDMgyrW82PCSzoXSstjyjxdwPm")
  twitter.setOAuthAccessToken(new AccessToken("2590200469-BjoRpSgZh7NqiyihkffxLQy8GDMjLPq66LvuxjK", "R01spRCW9eNXGhlvjoCH14wzRZUhvCcTrlLq17L32YghD"))
}

object HelloRemote extends App with TwitterInstance {
  val system = ActorSystem("HelloRemoteSystem")
  val remoteActor = system.actorOf(Props[RemoteActor], name = "RemoteActor")
  val tweet = system.actorOf(Props[TwitterActor], name = "TwitterActor")

  remoteActor ! "The RemoteActor is alive"


  class RemoteActor extends Actor {
    def receive = {
      case msg: String =>
        println(s"RemoteActor received message '$msg'")
        sender ! "Hello from the RemoteActor"
    }

  }

  class TwitterActor extends Actor {
    def receive: Actor.Receive = {
      case TweetLine(title, shortUrl) =>
        println("got: " + title)
        if (title.length() > 100) {
          val tweet = title.substring(0, 100) + " " + shortUrl
          twitter.updateStatus(tweet)
        }
        sender !  "Your message was sent"
        Thread sleep 45000

//      case SampleFireHose(clientHost) =>
//        println("Twitter firehose sample!")
//
//        sender() ! "Your message was sent"
    }
  }

}
