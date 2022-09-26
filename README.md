# TwitchMC

A plugin for Twitch utils in spigot

Support: [![Discord Server](https://discordapp.com/api/guilds/663175330512764948/embed.png?style=banner2)](https://discord.gg/RpyTXgQhru)

Important:

To obtain a `clientID` and `clientSecret` to populate in `config.yml`, you can create an app [here](https://dev.twitch.tv/console/apps/create).
Alternatively, you can provide `oauthToken`, which can be generated [here](https://www.twitchtokengenerator.com/) if you do not wish to host the authentication process yourself.
At least one of these must be provided to interact with the Helix API, which is used to track follows and stream state.
To read chat, no token is necessary, but in order to send messages to chat, an `oauth_token` with the `chat:edit` scope *is*.

Looking for more info? [visit the wiki](https://github.com/Fer-Jg/TwitchMC/wiki)!

---
## Features
* Send real time twitch messages in minecraft to the streamer.


---
### Planned features
* New follower notification + reward system.
* Cheer notification + reward system.
* New Subscriber notification + reward system.
* Gifted subs notification + reward system.
* Streamer<->viewer interaction commands (+ cooldowns).
* Server-related streamer stats.
* idk, I guess more if this gets good enough :)

---
### Possible features
* Discord integration
  * Discord<->Twitch chat
  * Discord commands (streamer/viewer/channel stats, viewer interactions, etc.)
