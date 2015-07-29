# twitterbot


This twitterbot uses twitter4j, a library for the Twitter API.

To be able to read the firehose, you will need first to create a Twitter application at http://apps.twitter.com, get your credentials.
Provide the given credentials in the TwitterInstance trait. See example below:

trait TwitterInstance {
  val twitter = new TwitterFactory().getInstance
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("xxxx", "xxxx")
  twitter.setOAuthAccessToken(new AccessToken("xxxx", "xxxx"))
}

This project contains three runable main classes. If your start sbt and give the run command, you will see the following:

Multiple main classes detected, select one to run:

 [1] nl.ncim.rss.reader.RssFeedParser
 [2] nl.ncim.rss.reader.TwitterBot
 [3] nl.ncim.rss.reader.TwitterBotWithActors
 
 1 - Just gets the feeds from the given url and shortens the url
 2 - The twitterbot
 3 - The twitterbot with akka actors