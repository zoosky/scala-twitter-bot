package nl.ncim.rss.reader

import java.net.{MalformedURLException, URLEncoder, URL}

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.{XmlReader, SyndFeedInput}

import twitter4j.{TwitterFactory}
import twitter4j.auth.AccessToken

trait TwitterInstance {
  val twitter = new TwitterFactory().getInstance
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("", "")
  twitter.setOAuthAccessToken(new AccessToken("", ""))
}

object TwitterBot extends TwitterInstance {

  def main(args: Array[String]): Unit = {

    try {
      val urls = List("http://www.computerweekly.com/rss/Internet-technology.xml")
      urls.foreach(url => {

        val feed = new SyndFeedInput().build(new XmlReader(new URL(url))).getEntries
        val parsedFeed = List(feed.toArray(new Array[SyndEntry](0)): _*)

        parsedFeed.foreach(entry => {
          println(entry.getTitle)
          val title = entry.getTitle
          val url = shortenUrl(entry.getLink)
          println(url)

          if (title.length() > 100) {
            val tweet = title.substring(0, 100) + " #technology " + url
            twitter.updateStatus(tweet)
          }
          else {
            val tweet = title + " #technology "+ url
            twitter.updateStatus(tweet)
          }
          Thread sleep 45000
        })

      })
    } catch {
      case e: RuntimeException => throw new RuntimeException(e)
    }

  }

  def shortenUrl(url: String): String ={
    val line = io.Source.fromURL("http://tinyurl.com/api-create.php?url=" + URLEncoder.encode(url, "UTF8")).getLines.next
    if (line startsWith "ERROR") throw new MalformedURLException
    else line
  }
}
