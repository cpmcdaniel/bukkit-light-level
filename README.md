# Bukkit Light Level

Minecraft [Bukkit](https://bukkit.org) server plugin for showing light levels on nearby blocks.
This can be very helpful for lighting up an area to prevent mobs from spawning. 
With this plugin a user simply has to sneak while holding a torch (or other light-emitting material) and particles
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
[INFO] Installing D:\...\bukkit-light-level\target\light-level-1.0-SNAPSHOT.jar to C:\...\.m2\repository\org\kowboy\bukkit\light-level\1.0-SNAPSHOT\light-level-1.0-SNAPSHOT.jar
[INFO] Installing D:\...\bukkit-light-level\pom.xml to C:\...\.m2\repository\org\kowboy\bukkit\light-level\1.0-SNAPSHOT\light-level-1.0-SNAPSHOT.pom
```

Copy the jar file to your server `plugins` directory and restart your server.

## Usage

To enable the feature (it is off by default):

```/light-level on```

Make sure your player is holding a light-emitting object (such as a torch) and hold down the key to sneak/crouch. This 
is `shift` by default on Java Edition. You should now see particles displayed on the top of visible blocks within an 
8-block square radius from the player. It should look something like this:

![screenshot](screenshot.png)

To turn the feature off again:

```/light-level off```

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
