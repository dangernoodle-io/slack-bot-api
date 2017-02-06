# slack-bot-api

<img src="https://img.shields.io/maven-central/v/io.dangernoodle/slack-bot-api.svg">
[![Build Status](https://travis-ci.org/dangernoodle-io/slack-bot-api.svg?branch=master)](https://travis-ci.org/dangernoodle-io/slack-bot-api)
[![Coverage Status](https://coveralls.io/repos/github/dangernoodle-io/slack-bot-api/badge.svg?branch=master)](https://coveralls.io/github/dangernoodle-io/slack-bot-api?branch=master)

The `slack-bot-api` is a `java 8` library that can used to interface with [Slack](https://slack.com)'s
[Real Time Messaging API](https://api.slack.com/rtm) to create your own bot.

## Getting Started

### WebSocket Clients

Two different `WebSocket` client implementations are available for use.

**Project Tyrus**

```xml
<dependency>
  <groupId>io.dangernoodle</groupId>
  <artifactId>slack-bot-api-tyrus</artifactId>
  <version>${slack-bot-api.version}</version>
</dependency>
```

**Jetty**

```xml
<dependency>
  <groupId>io.dangernoodle</groupId>
  <artifactId>slack-bot-api-jetty</artifactId>
  <version>${slack-bot-api.version}</version>
</dependency>
```

## Usage

### Basic

```java

SlackClientSettings settings = new SlackClientSettings(AUTH_TOKEN);
SlackClient slackClient = SlackClientBuilder.createClient(settings);

SlackObserverRegistry regsitry = slackClient.getObserverRegistry();

registry.addChannelJoinedObserver((event, client) -> {
    logger.debug("channel joined event: " + event);
});

registry.addChannelLeftObserver((event, client) -> {
    logger.info("channel joined event: " + event);
});

...

slackClient.connect();
```

### Sending Messages

**Simple Messages**

Simple text messages can be sent directly over the websocket connection.

```java
slackClient.send("simple text message");
```

**Complex Messages**

Details to follow...

## Special Acknowledgement

This library was inspired by the excellent [simple-slack-api](https://github.com/Ullink/simple-slack-api)
library. If you are looking to create a slack bot and are unable to use `java 8`, the `simple-slack-api`
is highly recommend as an alternative.

