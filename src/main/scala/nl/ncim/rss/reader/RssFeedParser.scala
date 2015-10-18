package nl.ncim.rss.reader

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.{XmlReader, SyndFeedInput}
import java.net.{URLEncoder, MalformedURLException, URL}

/**
 * FeedParser, reads the rss feed from given url
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
          println(shortenUrl(entry.getLink))
        })

      })
    } catch {
      case e:RuntimeException => throw new RuntimeException(e)
    }

  }

  /**
   * I shorten the given url
   * @param url
   * @return shorten url
   */
  def shortenUrl(url: String) = {
    val line = io.Source.fromURL("http://url.k47.cz/api/get/?url=" + URLEncoder.encode(url, "UTF8")).getLines.next
    if (line startsWith "ERROR") throw new MalformedURLException
    line
  }
}