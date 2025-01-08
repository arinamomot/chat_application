# Chat App

## Overview
A small application developed using JavaFX to provide a graphical user interface (GUI) for a chat application.

### Description
This chat application allows users to:
- Communicate by sending messages and stickers.
- Create groups for conversations involving more than two people.
- View information about other users based on the data they have provided.

The application is cross-platform and supports desktop computers running macOS, Windows, and Linux.

---

## Used Technologies

### GUI
- The application uses a GUI interface where users interact using a mouse for intuitive control.

### Development Framework
- The project was built using the **Maven** framework for dependency management and project configuration.

### Database
- **Postgresql** - Data about users, groups, and messages is stored in a database for persistence.

### Architecture
- Implemented using **client-server architecture**:
  - Multiple client devices communicate with a central server.
  - Clients connect to the server using a specific port number.

### Protocol
- A **custom protocol** based on JSON is used for communication between the client and server.

### Multithreading
- The application supports multithreaded communication to process client requests in parallel, ensuring efficient performance.

---

## Implementation Details

### Server-Side Programming
1. The server runs an **infinite loop** to continuously accept incoming requests.
2. Each request spawns a **new thread** to handle communication with the client independently.
3. The server keeps track of connected clients by storing their IDs in an array.

### Client-Side Programming
1. The client connects to the server using the specified port number.
2. The client sends their data (email and password) to the server.
3. In response, the server returns a **token** that uniquely identifies the client on the network.

### Communication
- Clients send messages to the server using Java's `OutputStream`.
- The server distributes messages to other clients as needed.

---

This chat app demonstrates robust design principles such as multithreading, secure client-server communication, and user-friendly GUI interfaces, making it an efficient and scalable messaging platform.
