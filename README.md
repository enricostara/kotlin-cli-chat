# kotlin-cli-chat

A personal Kotlin guide for Java developers.

This project implements a simple command-line interface chat just to collect what I found effective in the Kotlin
language.

## Motivation 🎯

As a Java developer, I wanted to take a picture of my first impressions of using Kotlin and write this project as a
guide/reference for myself and other developers who might find it useful.

Probably the Kotlin I wrote will look still boring to a seasoned Kotlin developer. However, it could represent what a
Java programmer might find interesting to use Kotlin without sacrificing code readability when used in a small or
medium-sized team.

For this reason, I will try to use some idiomatic innovations brought by Kotlin only in cases and in the manner that I
consider most effective.

## Implementation 🔩️

A chat application is an opportunity to explore the Kotlin language by implementing different use cases with fun.

This chat uses a simple command-line interface to perform all required actions, such as create/join a topic, read
messages from a topic, send a message, etc. Chat is elementary and will not implement any security such as user
authentication and authorization, much less an authoritative server-side validation regarding user and topic handling.
All these features and others are beyond the scope of this exercise but could be implemented by forking the project.

The implementation will purposely have no dependencies on third-party libraries. It will attempt to solve common
problems (such as command-line argument mapping) using only the language's expressiveness (like using first-class
functions).

The code, the comments, and the tests will be the only documentation provided by this project. I have found that Kotlin
supports very well in writing unit tests because it is concise. As an exercise, someone could add more tests to increase
coverage, and the missing ones could be implemented using a stubbing technique, such as the [MockK](https://mockk.io)
library.

To be effective, I'll keep the code as simple as possible, only commenting on the most significant parts. It might be
better to clone the repository or download the zip, open the project with an appropriate IDE
(like IntelliJ IDEA Community), and go through the code with the natural flow of execution
(starting point is the `io.kcc.KotlinCliChat` [here](./src/main/java/io/kcc/KotlinCliChat.kt)).

Nevertheless, I put all the very basic operational details in this README to [compile and run](#get-started-) the
project.

## Communication Protocol ☎️

The chat default implementation uses a simple file-based communication protocol; therefore, users could use a network
file system in local scenarios.

It may also be that you decide to fork this project and provide a more suitable server-based protocol. This could be
easy as I will keep the default protocol implementation behind an interface and bound only to the `file`
schema. For example, a new protocol could be added and implemented by calling an HTTP server using rest calls and
associated with the `http` schema instead.

## Get started 🚀

Build the project with gradle by running the following command from bash in the root directory of your local copy of the
project:

```
./gradlew build
```

If the java runtime is already set in the path, run the program by executing the following:

```
./kcc
```

You will get the following:

```
usage:
    kcc user
    kcc host
    kcc topic
    kcc </topic> <msg>
    kcc </topic>
    kcc </topic/#>
    kcc </topic/user>
    kcc </topic/user/#>
    kcc -h | --help
    kcc --version
    
options:
    -h --help            Show this screen
    --version            Show version

```

### Create the user 💁‍

Start using the chat by accessing the help of the `user` submenu:

```
./kcc user -h
```

The user submenu shows all the operations available to manage your account:

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

Create your user by running the following:

```
./kcc user new your_name

user 'your_name' has been created.
```

Verify that the user was created correctly by running:

```
./kcc user
 
user:
  name: #your_name                
  topics: no /topics
```

### Connect the host 📡

Show the help of the `host` submenu:

```
./kcc host -h

usage:
    kcc host
    kcc host new <url>
    kcc host del
    kcc host -h | --help
```

Register the host using a folder of your choice as the path (the default URL schema will be `file`)

```
mkdir kcc-db
./kcc host new ./kcc-db

host file:./kcc-db has been registered.
```

Show information about the host you just created:

```
./kcc host

host:
  url: file:./kcc-db
  schema: file
  authority: localhost 
  path: ./kcc-db
```

### Create a new topic 🧨

Show the help of the `topic` submenu:

```
./kcc topic -h

usage:
    kcc topic
    kcc topic new <name>
    kcc topic join <name>
    kcc topic leave <name>
    kcc topic -h | --help
```

Create and join a new topic by providing the name:

```
./kcc topic new jvm-lovers

topic /jvm-lovers has been created.
topic /jvm-lovers has been joined.
```

Show information about the topics you already joined:

```
./kcc topic

topics: 
    - /jvm-lovers       *
```

Again from user info:

```
./kcc user

user:
  name: 'your_name'                
  topics: 
    - /jvm-lovers   
```

### Read and send messages 🙊🙉

To send your first message to a topic you already joined, type the following:

```
./kcc /jvm-lovers Hello by a new kotlin user

/jvm-lovers/your_name > Hello by a new kotlin user
```

To read messages from a topic (the default shows the last 10 messages):

```
./kcc /jvm-lovers

/jvm-lovers/your_name > Hello by a new kotlin user
```

To show a specific number of most recent posts, for example only 1 (0 means all):

```
./kcc /jvm-lovers/1
```

To show posts coming from a specific user:

```
./kcc /jvm-lovers/your_name
```

To show posts from a specific user and with a specific number of most recent posts (e.g. 3):

```
./kcc /jvm-lovers/your_name/3
```

## About Windows Users 👥

The project is meant to run in a bash shell and if you are a Windows 10 user you can easily find a guide on 
how to install the bash shell in your operating system.

For other versions of Windows you can try running `kcc.bat`, but it was provided without any testing!

## License 💾
The project is released under the [MIT license](./LICENSE)