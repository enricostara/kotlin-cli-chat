# kotlin-cli-chat
A Kotlin reference for Java developers. A simple chat with command-line interface only to collect what I have found effective in Kotlin language

## Overview
A chat application is an opportunity to explore the Kotlin language by implementing different use cases with fun.

The implementation will have no dependencies on third-party libraries and will try to solve common problems (such as command-line argument mapping) using only the language's expressiveness.

The comments and code will be the only documentation provided by this project. To be effective, I'll keep the code as simple as possible, only commenting on the most significant part. 
In any case, I have put the simple operational details in this readme file to get the project compiled and running.

## The command-line Chat

This chat uses a simple command-line interface to perform all required actions, such as logging in, creating a topic, joining a topic already there, etc. 

The default implementation uses a filesystem-based communication protocol; therefore, a network file system could be used in a real-world scenario. 

It could also be that you decide as an exercise to fork this project to provide a more suitable server-based protocol. 
It might be easy as I will keep the protocol implementation hidden from the interfaces and completely detachable from the application.