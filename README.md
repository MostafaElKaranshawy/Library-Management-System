# Library Management System
A Library Management System CLI Application.

## System Functionalities

- Admin Authentication
- Admin
  - Register Users
  - Add, modify, remove Books from the library
  - See available Books

- User
  - Borrows Books
  - Return Borrowed Books
  - See list of available books
  - See list of Borrowed Books

- Database Management
  - File System database
  - MySQL Database.

## Database Schema

<img width="790" height="625" alt="image" src="https://github.com/user-attachments/assets/862a0e47-6b65-4aa2-96f1-d175483b31ff" />

## Entities Class Diagram

<img width="952" height="625" alt="image" src="https://github.com/user-attachments/assets/81fd0a6b-14db-46c2-86bf-b821327ec405" />


## How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/MostafaElKaranshawy/Library-Management-System.git
cd ./Library-Management-System.git
```

### 2. Install Dependencies

- Java JDK (23).
- MySQL Connector.
  - Add the .jar file into your project structure.
- io.github.cdimascio.dotenv.java
- codenameone.sqlite.jdbc
- mindrot.jbcrypt

### 3. Run the Code

```bash
javac -d out src/*.java
java -cp out src/Main.java
```

### Now Your Program Is Ready To Use.


## System Snapshots 

- Main Menu
  
  <img width="362" height="154" alt="image" src="https://github.com/user-attachments/assets/d0da8be9-49c2-4611-aadf-e6e4e1594ae3" />

- Admin Menu
  
  <img width="256" height="243" alt="image" src="https://github.com/user-attachments/assets/a1c62885-db0f-453a-839f-482ca732a17b" />

- Books List
  
  <img width="701" height="173" alt="image" src="https://github.com/user-attachments/assets/f5642663-a49a-43b8-812c-928f167dfb99" />

- Users List

  <img width="344" height="155" alt="image" src="https://github.com/user-attachments/assets/aa2e0636-98af-49a6-9847-124113737c06" />
