# kotlin-cli-chat
A Kotlin opinionated reference for Java developers. 

A simple chat with command-line interface only to collect what I have found effective in Kotlin language

## the reasons
As a Java developer with several years of enterprise projects behind him, I wanted to picture my first impressions of using Kotlin. 

Probably the Kotlin I wrote will look still boring to the seasoned Kotlin programmer. 

Still, it could represent what an experienced Java programmer might find interesting to use Kotlin without sacrificing 
too much code readability and maintainability when used in big teams.


## the project
A chat application is an opportunity to explore the Kotlin language by implementing different use cases with fun.

The implementation will have no dependencies on third-party libraries and will try to solve common problems 
(such as command-line argument mapping) using only the language's expressiveness.

The code and the comments will be the only documentation provided by this project. 
To be effective, I'll keep the code as simple as possible, only commenting on the most significant part. 

In any case, I have put all operational details in this readme file to get the project compiled and running.

## the chat

This chat uses a simple command-line interface to perform all required actions, such as logging in, creating a topic, 
joining a topic already there, etc. 

The default implementation uses a filesystem-based communication protocol; 
therefore, a network file system could be used in a real-world scenario. 

It could also be that you decide as an exercise to fork this project to provide a more suitable server-based protocol. 
It might be easy as I will keep the protocol implementation hidden from the interfaces and completely detachable from the application.