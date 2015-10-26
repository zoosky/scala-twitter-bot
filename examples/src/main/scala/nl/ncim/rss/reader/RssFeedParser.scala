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

      // http://www.computerweekly.com/rss/Internet-technology.xml
      // http://stackoverflow.com/feeds/tag/scala+and+java
      // http://www.drdobbs.com/rss/all
      // http://feeds.feedburner.com/TechCrunch/
      // https://news.ycombinator.com/rss
      // http://feeds.feedburner.com/heroku
      // https://github.com/blog.atom
      // https://devnet.jetbrains.com//community/feeds/tags/scala

      val urls = List("http://www.nasa.gov/rss/dyn/breaking_news.rss")

      urls.foreach(url => {
        val feed = new SyndFeedInput().build(new XmlReader(new URL(url))).getEntries
        val parsedFeeds = List(feed.toArray(new Array[SyndEntry](0)) : _*)

        parsedFeeds.foreach(entry =>{
          println(entry.getTitle)
          println(UrlShortener.shortenUrlWithTinyUrl(entry.getLink))
        })

      })
    } catch {
      case e:RuntimeException => throw new RuntimeException(e)
    }

  }

}