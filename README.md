# Requirements
1. Spigot Server(s) 
2. [Nats](https://nats.io) Server
3. Some kind of loadbalancer (e.g [BungeeCord](https://github.com/SpigotMC/BungeeCord) or [Velocity](https://velocitypowered.com/)) would be useful.

# Installation
Place the plugin jar in the plugins folder.
change the configuration if needed:
```yml
nats:
  host: localhost # nats server host
  port: 4222 # nats server port

board:
  size: 64 # size of the board
  cooldown: 120 # cooldown in seconds between each block place
  kick-after-place: true # if true, the player will be kicked after placing a block

world:
  disable-auto-save: true # if true, the plugin will turn off the world auto save
```
Replace the world with a void map.

# Compiling
1. Clone the repository
2. Run `gradlew shadowJar` to compile the plugin
3. The compiled jar will be in `.out` directory, named `rPlace-plugin.jar`