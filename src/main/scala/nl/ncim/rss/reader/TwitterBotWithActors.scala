package nl.ncim.rss.reader

import java.net.{MalformedURLException, URLEncoder, URL}

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.{XmlReader, SyndFeedInput}

import nl.ncim.rss.reader.FeedParserActor.{TweetLine, ParseFeed}
import nl.ncim.rss.reader.GetFeedActor.{ShortenUrl, GetFeed}

import twitter4j.auth.AccessToken
import twitter4j._

import akka.actor._

case object StartMessage
case object StopMessage
case object ParseMessage


object GetFeedActor {
  case class GetFeed(url: String)
  case class ShortenUrl(title: String, url: String)
}

object FeedParserActor {
  case class ParseFeed(feed: List[SyndEntry])
  case class TweetLine(title: String, shortUrl: String)
}

trait TwitterInstance2 {
  val twitter = new TwitterFactory().getInstance
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("", "")
  twitter.setOAuthAccessToken(new AccessToken("", ""))
}

/**
 * Tweetbot with Akka actors
 */
object TwitterBotWithActors extends TwitterInstance2 {

  def main(args: Array[String]) {
    val system = ActorSystem("TwitterStreamSystem")
    val parse = system.actorOf(Props[FeedParserActor], name = "parse")
    val feed = system.actorOf(Props(new GetFeedActor(parse)), name = "feed")

    feed ! GetFeedActor.GetFeed("http://www.computerweekly.com/rss/Internet-technology.xml")
    feed ! GetFeedActor.GetFeed("http://feeds.feedburner.com/TechCrunch/")
  }

  class GetFeedActor(parse: ActorRef) extends Actor with ActorLogging {
    def receive: Receive = {
      case GetFeed(url) =>
        log.info(s"I was greeted by $url.")
        val i = new SyndFeedInput().build(new XmlReader(new URL(url))).getEntries
        val rssFeedEntries = List(i.toArray(new Array[SyndEntry](0)): _*)
        parse ! FeedParserActor.ParseFeed(rssFeedEntries)

      case ShortenUrl(title, url) =>
        println("title: " + title)
        val line = io.Source.fromURL("http://url.k47.cz/api/get/?url=" + URLEncoder.encode(url, "UTF8")).getLines.next
        if (line startsWith "ERROR") throw new MalformedURLException
        println("line: "+ line)
        parse ! FeedParserActor.TweetLine(title, line)
    }
  }

  class FeedParserActor() extends Actor {
    def receive: Actor.Receive = {
      case ParseFeed(rssFeedEntries) =>
        println("FeedParser Actor!")
        rssFeedEntries.foreach(entry => {
          sender() ! GetFeedActor.ShortenUrl(entry.getTitle(), entry.getLink)
        })

      case TweetLine(title, shortUrl) =>
        println("got: " + title)
        if (title.length() > 100) {
          val tweet = title.substring(0, 100) + " " + shortUrl
          twitter.updateStatus(tweet)
        }
        else {
          val tweet = title + " #java " + shortUrl
          twitter.updateStatus(tweet)
        }
        Thread sleep 45000
    }
  }

}


