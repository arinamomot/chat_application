## Chat app
*Author:* Arina Momot

---
A small application which using JavaFX for a GUI chat application.

---
### Description of the project.
This chat application enables users to communicate with each other by sending messages and stickers, create groups for communication for more than two people, and also view information about users that he provided.

This messaging app supports every platform across desktop computers (macOS, Windows and Linux).

**Used technologies:**
- The project is done using the **GUI**. People just use the mouse to directly manipulate the interface.

- The project was developed using the **Maven** framework.

- Data about users, groups and messages will be stored in the **database**.

- Chat application is implemented using **client-server architecture**. This type of architecture has one or more client computers connected to a central server.

- This chat app is **Multi-threaded chat Application**:
(indexing connected clients)

  A server listens for connection requests from clients across the network. Clients know how 
  to connect to the server via the port number. The client sends a message, the message is 
  sent to the server using OutputStream in java. 

   + Server side programming:
      1. The server runs an infinite loop to keep accepting incoming requests.
      2. When a request comes, it assigns a new thread to handle the communication part.
      3. The sever also stores the client id (indexing connected clients) into array, to keep a track of connected devices.
      
   + Client side:
      1. The client connects to the server.
      2. Sends his data (mail and password), in response receives a token that identifies him on the network.

- **Custom protocol based on JSON**.
---
**Project structure**

---
**Features of the program**:

- Registration and entry:
   1. User registration, you must fill out information about yourself: name, surname, valid email address, password, country of residence, date of birth.
   2. Logging into the application, you need to enter the email and password used during registration.

- Create group chats:..

- Sending messages:... text and stickers.

- View user information:..
