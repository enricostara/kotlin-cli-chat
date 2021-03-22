# kotlin-cli-chat

A personal Kotlin guide for Java developers.

This project implements a simple command-line interface chat just to collect what I found effective in the Kotlin
language.

## Motivation 🎯

As an old Java developer with a few projects behind me, I wanted to take a picture of my first impressions of using
Kotlin and write this project as a guide/reference for myself and other developers who might find it useful.

Probably the Kotlin I wrote will look still boring to the seasoned Kotlin programmers. However, it could represent what
a Java programmer might find interesting to use Kotlin without sacrificing code readability when used in a small or
medium-sized team.

For this reason, I will try to use some idiomatic innovations brought by Kotlin only in the cases and ways in which I
truly believe they deserve to be exploited.

## Implementation 🔩️

A chat application is an opportunity to explore the Kotlin language by implementing different use cases with fun.

This chat uses a simple command-line interface to perform all required actions, such as create/join a topic, read
messages from a topic, send a message, etc. Chat is elementary and will not implement any security such as user
authentication and authorization, much less authoritative server-side validation regarding user and topic handling. All
these features and others are beyond the scope of this exercise but could be implemented by forking the project.

The implementation will purposely have no dependencies on third-party libraries. It will attempt to solve common
problems (such as command-line argument mapping)
using only the language's expressiveness (like using first-class functions).

The code, the comments, and the tests will be the only documentation provided by this project. I have found that Kotlin
supports very well in writing unit tests because it is concise. As an exercise, someone could add more tests to increase
coverage, but the missing ones should be implemented using a stubbing technique, such as the [MockK](https://mockk.io)
library.

To be effective, I'll keep the code as simple as possible, only commenting on the most significant parts. It might be
better to clone the repository or download the zip, open the project with an appropriate IDE
(like IntelliJ IDEA Community), and go through the code with the natural flow of execution
(starting point is `io.kcc.KotlinCliChat`).

Nevertheless, I put all the very basic operational details in this README to [compile and run](#how-to-run-) the
project.

## Communication Protocol ☎️

The chat default implementation uses a simple file-based communication protocol; therefore, users could use a network
file system in local scenarios.

It may also be that you decide, as an exercise, to fork this project and provide a more suitable server-based protocol.
This could be easy as I will keep the default protocol implementation behind an interface and bound only to the `file`
schema. For example, a new protocol could be added and implemented by calling an HTTP server using rest calls and
associated with the `http` schema instead.

## How to run 🚀

Build the project with gradle by running the following command in the root directory of your local copy of the project:

```
./gradlew build
```

If the java runtime is already set in the path, run the program by executing the following:

```
./kcc
```

You should get the following:

```
usage:
    kcc user
    kcc host
    kcc topic
    kcc </topic> <msg>
    kcc </topic>
    kcc -h | --help
    kcc --version
    
options:
    -h --help            Show this screen
    --version            Show version
```

### Create the user

Start using the chat by accessing the help of the `user` submenu:

```
./kcc user -h
```

The user sub-menu help:

```
usage:
    kcc user
    kcc user new <name>
    kcc user ren <name>
    kcc user del
    kcc user -h | --help
    
options:
    -h --help   Show this screen
```

Then, create the user by running the following:

```
./kcc user new <your_name>
```

Verify that the user was created correctly by running:

```
./kcc user
```

You should get the following:

```
user:
  name: #your_name                
  topics: no /topics
```