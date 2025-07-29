# Library Management System
A Library Management System CLI Application.

## Table of Contents

- [Introduction](#introduction)
- [System Functionalities](#system-functionalities)
- [Database Schema](#database-schema)
- [Entities Class Diagram](#entities-class-diagram)
- [How to Run](#how-to-run)
  - [Clone the Repository](#1-clone-the-repository)
  - [Install Dependencies](#2-install-dependencies)
  - [Run the Code](#3-run-the-code)
- [How to Run (Docker Version)](#how-to-run-docker-version)
  - [Clone the Repository](#1-clone-the-repository-1)
  - [Install Docker for Your System](#2-install-docker-for-your-system)
  - [Check for Your .env File](#3-check-for-your-env-file)
  - [Run the Docker Compose to Build and Run the Containers](#4-run-the-docker-compose-to-build-and-run-the-containers)
  - [Access the Application](#5-access-the-application)
  - [Troubleshooting Tips](#troubleshooting-tips)
- [System Snapshots](#system-snapshots)

# Introduction

This is a simple Library Management System that allows users to borrow and return books, while providing an admin interface for managing users and books and a user UI to borrow or return books.

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

## How to Run (Docker Version)

### 1. Clone the Repository

```bash
git clone https://github.com/MostafaElKaranshawy/Library-Management-System.git
cd ./Library-Management-System.git
```

### 2. install docker for your system
- [Docker Installation Guide](https://docs.docker.com/get-docker/)

### 3. Check for your .env file 

```bash
DP_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DB_HOST=mysql
DB_URL=jdbc:mysql://mysql:3306/library_system
FILES_PATH=path_to_your_files
```


### 4. Run the Docker Compose to build and run the containers

- Make sure that the `app.jar` file is in the root directory of the project.
  ```bash
  docker compose up --build
  ```
- To check that the containers are running, you can use:

  ```bash
  docker ps
  ```
### 5. Access the Application.
- my_app:
  
  ```bash
  docker ps
  # Copy you my_app container ID
  docker attach <container_id>
  # you will see the application running in your terminal.
  ```

<hr>

- Adminer
  - open your browser and go to `http://localhost:8080`
  - set the server to `mysql`
  - set the username to your database username
  - set the password to your database password
  - set the database to `library_system`
  - login to see your database.
<hr>

### Troubleshooting Tips

- If you encounter issues with the database connection, ensure that your `.env` file is correctly configured with the right credentials and database URL.
- If you face any issues with Docker, make sure Docker is running and that you have the necessary permissions to run Docker commands (maybe you need to use `sudo` before docker commands).
- for any other issues, check the docker documentation or the issues section of this repository [Docker Installation Guide](https://docs.docker.com/get-docker/).
- If you need to stop the containers, you can use:

  ```bash
  docker compose down
  ```

## System Snapshots 

- Main Menu
  
  <img width="362" height="154" alt="image" src="https://github.com/user-attachments/assets/d0da8be9-49c2-4611-aadf-e6e4e1594ae3" />

- Admin Menu
  
  <img width="256" height="243" alt="image" src="https://github.com/user-attachments/assets/a1c62885-db0f-453a-839f-482ca732a17b" />

- Books List
  
  <img width="701" height="173" alt="image" src="https://github.com/user-attachments/assets/f5642663-a49a-43b8-812c-928f167dfb99" />

- Users List

  <img width="344" height="155" alt="image" src="https://github.com/user-attachments/assets/aa2e0636-98af-49a6-9847-124113737c06" />
