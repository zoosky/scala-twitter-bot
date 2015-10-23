package nl.ncim.twitter.example.stream

import org.apache.log4j.Logger
import twitter4j._

import scala.collection.mutable.ListBuffer

/**
 * LogHelper is a trait you can mix in to provide easy log4j logging
 * for your scala classes.
 **/
trait LogHelper {
  val loggerName = this.getClass.getName
  lazy val logger = Logger.getLogger(loggerName)
}

object Util extends LogHelper {

    var listBufferOfUsers = new ListBuffer[String]()

    val config = new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey("")
      .setOAuthConsumerSecret("")
      .setOAuthAccessToken("")
      .setOAuthAccessTokenSecret("")
      .build

    def directMessage() {
      val a = listBufferOfUsers.toList
      logger.info(a.foreach(e => println(e)))
    }

    def simpleStatusListener = new StatusListener() {
      def onStatus(status: Status) {
        println(status.getUser().getScreenName() + " - " + status.getText());
        val item = (status.getUser().getScreenName())
        if (item != null) {
          listBufferOfUsers += item
        }
      }
      def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
      def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
      def onException(ex: Exception) {
        ex.printStackTrace
      }
      def onScrubGeo(arg0: Long, arg1: Long) {
      }
      def onStallWarning(warning: StallWarning) {}
    }
  }
