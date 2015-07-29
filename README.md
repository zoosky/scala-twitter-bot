# twitterbot


This twitterbot uses twitter4j, a library for the Twitter API.

The twitterbot is written with scala 2.11.7 and built with sbt version 0.13.8

To be able to read the firehose, you will need first to create a Twitter application at http://apps.twitter.com, get your credentials.
Provide the given credentials in the TwitterInstance trait. See example below:

```scala
trait TwitterInstance {
  val twitter = new TwitterFactory().getInstance
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("xxxx", "xxxx")
  twitter.setOAuthAccessToken(new AccessToken("xxxx", "xxxx"))
}
```

This project contains three runable main classes. If your start sbt and give the run command, you will see the following:

Multiple main classes detected, select one to run:

 * [1] nl.ncim.rss.reader.RssFeedParser
 * [2] nl.ncim.rss.reader.TwitterBot
 * [3] nl.ncim.rss.reader.TwitterBotWithActors
 
# Description 

 * 1 RssFeedParser - Just gets the feeds from the given url and shortens the url
 * 2 TwitterBot- The twitterbot
 * 3 TwitterBotWithActors- The twitterbot with akka actors