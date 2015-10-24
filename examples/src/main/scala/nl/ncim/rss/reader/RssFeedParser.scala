package nl.ncim.rss.reader

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.{XmlReader, SyndFeedInput}
import java.net.URL

import nl.ncim.shortener.UrlShortener

/**
 * FeedParser, I read the rss feed from given url and print the contents of each entry, I also shorten the url.
 */
object RssFeedParser {

  def main(args: Array[String]): Unit = {
    try {
      val urls = List("http://www.computerweekly.com/rss/Internet-technology.xml")

      urls.foreach(url => {
        val feed = new SyndFeedInput().build(new XmlReader(new URL(url))).getEntries
        val parsedFeeds = List(feed.toArray(new Array[SyndEntry](0)) : _*)

        parsedFeeds.foreach(entry =>{
          println(entry.getTitle)
          println(UrlShortener.shortenUrl(entry.getLink))
        })

      })
    } catch {
      case e:RuntimeException => throw new RuntimeException(e)
    }

  }

}