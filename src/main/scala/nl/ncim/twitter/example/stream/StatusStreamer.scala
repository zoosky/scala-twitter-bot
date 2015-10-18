package nl.ncim.twitter.example.stream

import twitter4j.TwitterStreamFactory

object StatusStreamer {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.sample
    Thread.sleep(2000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}