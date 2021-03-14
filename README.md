# kotlin-cli-chat
A personal Kotlin guide for Java developers. 

This project implements a simple command line interface chat just to collect what I found effective in the Kotlin language.

## the reasons
As an old Java developer with a few projects behind me, I wanted to take a picture of my first impressions of using Kotlin 
and write this project as a guide/reference for myself and other developers who might find it useful.

Probably the Kotlin I wrote will look still boring to the seasoned Kotlin programmers. 

Still, it could represent what a Java programmer might find interesting to use Kotlin without sacrificing 
code readability when used in a medium-sized team.

For this reason, I will try to use some idiomatic innovations brought by Kotlin only in the cases and ways in which 
I truly believe they deserve to be exploited.

## the project
A chat application is an opportunity to explore the Kotlin language by implementing different use cases with fun.

The implementation will purposely have no dependencies on third-party libraries. 
It will attempt to solve common problems (such as command-line argument mapping) using only the language's expressiveness.

The code, the comments, and the tests will be the only documentation provided by this project.
I have found that Kotlin supports very well in writing tests because it is expressive and concise.

To be effective, I'll keep the code as simple as possible, only commenting on the most significant parts the first time.
It might be better to clone the repository or download the zip, open the project with an appropriate IDE 
(like IntelliJ IDEA Community), and go through the code with the natural flow of execution 
(starting point is `io.kcc.KotlinCliChat`).

Nevertheless, I put all the very basic operational details in this readme file to compile and run the project.

## the chat

This chat uses a simple command-line interface to perform all required actions, such as logging in, creating a topic, 
joining a topic already there, etc.

The default implementation uses a filesystem-based communication protocol; 
therefore, a network file system could be used in an almost real scenario. 

It could also be that you decide, as an exercise, to fork this project to provide a more suitable server-based protocol. 
It might be easy as I will keep the protocol implementation behind the interfaces and completely detachable from the application.