package nl.ncim.twitter.server

import akka.actor._
import nl.ncim.common.TwitterActor.TweetLine

import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

trait TwitterInstance {
  val twitter = new TwitterFactory().getInstance
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("", "")
  twitter.setOAuthAccessToken(new AccessToken("", ""))
}

object HelloRemote extends App {
  val system = ActorSystem("HelloRemoteSystem")
  val tweet = system.actorOf(Props[TwitterActor], name = "TwitterActor")
  var counter = 0

  tweet ! "The RemoteActor is alive"

  class TwitterActor extends Actor with TwitterInstance {
    def receive = {
      case TweetLine(title, shortUrl) =>
        println("got: " + title + " with url: " + shortUrl)
        val length = title.length() + shortUrl.length()
        if (length < 140) {
          twitter.updateStatus(title + " #it " + shortUrl)
          counter += 1
        }
        sender() ! "Your message was published"
        Thread sleep 10000

      case msg: String =>
        println(s"TwitterActor received message '$msg'")
        sender() ! "Hello from the TwitterActor"

      case "COUNT" =>
        sender() ! "tweet counter is currently: " + counter
    }
  }

}
