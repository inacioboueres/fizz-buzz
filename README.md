Fizz Buzz Game
================================

Requirements
------------
* [Java Platform (JDK) 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)

Quick start
-----------
1. 'mvn clean package'
2. 'mvn spring-boot:run'
3. 'mvn test'

Description
-----------
Fizz Buzz game solver with Springboot and angular
The player designated to go first says the number "1", and each player thenceforth counts one number in turn. However, any number divisible by three is replaced by the word fizz and any divisible by five by the word buzz. Numbers divisible by both become fizz buzz.

For example, a typical round of fizz buzz would start as follows:

1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, Fizz Buzz, 16, 17, Fizz, 19, Buzz, Fizz, 22, 23, Fizz, Buzz, 26, Fizz, 28, 29, Fizz Buzz, 31, 32, Fizz, 34, Buzz, Fizz, ...
(Wikipedia)

A custom game mode has been added, it will provide the chance to add or change existing rules, like (7 - Woof), so it will return "Woof" to every number divisible by "7", was added an ordination option, to provide or not an ordered output, and at last 2 new game modes, besides the classic "Divisible" game mode, was added the "Containing" mode that will return the "Word" when the tested number contains the number key, for instance, 73 will be replaced by "Fizz Woof" because contains 7 and 3, and the last mode is "Both" that is the union of "Divisible" and "Containing" modes.

Hope this code help other developers. 
You can try it at http://fizzbuzz-inaciofizzbuzz.rhcloud.com/index.html#/classic, please have fun.
