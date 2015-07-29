package nl.ncim.rss.reader

import java.net.{MalformedURLException, URLEncoder, URL}

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.{XmlReader, SyndFeedInput}

import twitter4j.{TwitterStreamFactory, TwitterFactory}
import twitter4j.auth.AccessToken


trait TwitterInstance {
  val twitter = new TwitterFactory().getInstance
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("", "")
  twitter.setOAuthAccessToken(new AccessToken("", ""))
}

object TwitterBot extends TwitterInstance {

  def main(args: Array[String]): Unit = {

    //val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    //twitterStream.addListener(Util.simpleStatusListener)

    try {
      val sfi = new SyndFeedInput()

      val urls = List("http://www.computerweekly.com/rss/Internet-technology.xml")
      urls.foreach(url => {

        val i = new SyndFeedInput().build(new XmlReader(new URL(url))).getEntries
        val b = List(i.toArray(new Array[SyndEntry](0)): _*)

        b.foreach(entry => {
          println(entry.getTitle)
          val title = entry.getTitle
          val url = shortenUrl(entry.getLink)

          if (title.length() > 100) {
            val tweet = title.substring(0, 100) + " " + url
            twitter.updateStatus(tweet)
          }
          else {
            val tweet = title + " #it " + url
            twitter.updateStatus(tweet)
          }
          Thread sleep 45000
        })

      })
    } catch {
      case e:RuntimeException => throw new RuntimeException(e)
    }

  }

  def shortenUrl(url: String) {
    val line = io.Source.fromURL("http://url.k47.cz/api/get/?url=" + URLEncoder.encode(url, "UTF8")).getLines.next
    if (line startsWith "ERROR") throw new MalformedURLException
    line
  }
}
