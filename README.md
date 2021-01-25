# Bukkit Light Level

Minecraft [Bukkit](https://bukkit.org) server plugin for showing light levels on nearby blocks.
This can be very helpful for lighting up an area to prevent mobs from spawning. 
With this plugin a user simply has to hold a torch (or other light-emitting material) and particles
will appear on nearby blocks showing relative light levels. Red particles indicate a light level of 7 or below, and
such blocks are spawnable for most mob types.

## Installation

If you just want to run the plugin, simply download the latest plugin version from the [BukkitLightLevel](https://dev.bukkit.org/projects/bukkitlightlevel)
project on dev.bukkit.org and place it in your server's `plugins` directory.

## Building from source

Building requires Maven, installation of which will not be covered here. See the [Maven](http://maven.apache.org/) 
project for details on how to set that up.

Building the jar file:

```shell
$ mvn install
```

You should see output similar to:

```
[INFO] --- maven-install-plugin:2.4:install (default-install) @ light-level ---
[INFO] Installing D:\...\bukkit-light-level\target\light-level-1.1.jar to C:\...\.m2\repository\org\kowboy\bukkit\light-level\1.1\light-level-1.1.jar
[INFO] Installing D:\...\bukkit-light-level\pom.xml to C:\...\.m2\repository\org\kowboy\bukkit\light-level\1.1-SNAPSHOT\light-level-1.1.pom
```

Copy the jar file to your server `plugins` directory and restart your server.

## Usage

Make sure your player is holding a light-emitting object (such as a torch). Sneaking/crouching will extend the radius a 
bit (depending on configuration). This is `shift` by default on Java Edition. You will see particles displayed on the 
top of visible blocks near the player. It should look something like this:

![screenshot](screenshot.png)

## Config

```yaml
# Setting these to 0 will disable the plugin
sneak-radius: 8
standing-radius: 6
```
There are two different radii that can be configured. The max radius while standing is 8 and the max radius while 
sneaking is 16. The intent here is to limit the amount of server lag on multi-player servers. Since sneaking while 
holding a light is a relatively rare occurance, we can extend the range without bringing the server to a crawl (unless
a whole bunch of players are holding a light and sneaking at the same time). These limits are arbitrary. Performance will
vary based on server resources and number of players online.

## License

```
MIT License

Copyright (c) 2018 Mariell Hoversholm

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
