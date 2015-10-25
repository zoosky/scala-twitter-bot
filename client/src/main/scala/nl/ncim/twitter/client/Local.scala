package nl.ncim.twitter.client

import java.net.URL

import akka.actor._
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.{XmlReader, SyndFeedInput}
import nl.ncim.common.TwitterActor.TweetLine
import nl.ncim.shortener.UrlShortener


object Local extends App {

  implicit val system = ActorSystem("LocalSystem")
  val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")  // the local actor
  localActor ! "START"                                                     // start the action

}

class LocalActor extends Actor {

  // create the remote actor
  //TODO check the ip adress of the remote server
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@127.0.0.1:2552/user/TwitterActor")
  var counter = 0

  def receive = {
    case "START" =>
      remote ! "Hello from the LocalActor"
      //TODO get feeds
      //TODO send message to remote sever

      try {
        val urls = List("http://www.nasa.gov/rss/dyn/breaking_news.rss")

        urls.foreach(url => {
          val feed = new SyndFeedInput().build(new XmlReader(new URL(url))).getEntries
          val parsedFeeds = List(feed.toArray(new Array[SyndEntry](0)) : _*)

          parsedFeeds.foreach(entry =>{
            println(entry.getTitle)
            println(UrlShortener.shortenUrlWithTinyUrl(entry.getLink))
            remote ! TweetLine(entry.getTitle, UrlShortener.shortenUrlWithTinyUrl(entry.getLink))
            remote ! "COUNT"
            Thread sleep 10000
            remote ! "COUNT"
          })

        })
      } catch {
        case e:RuntimeException => throw new RuntimeException(e)
      }

    case msg: String =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        //TODO make a custom reply message
        sender ! "Hello back to you"
        counter += 1
      }
  }
}