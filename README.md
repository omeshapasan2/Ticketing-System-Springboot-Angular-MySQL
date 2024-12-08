# Ticketing-System-Springboot-Angular-MySQL

![image](https://github.com/user-attachments/assets/a8368d8f-f859-4dca-adaa-443c6f2383ab)



## Introduction

This is a **Ticketing System** designed to simulate a real-time ticket booking process. The system includes:

- **Vendor threads** that add tickets to the pool.
- **Customer threads** that book tickets from the pool.
- **WebSocket integration** for real-time updates on ticket status.
- **User Authentication** for Admins (Register and Login).

---

## Technologies Used

- **Backend**: Java 17, Spring Boot, Spring Security (for authentication), WebSockets
- **Frontend**: Angular , WebSocket
- **Database**: MySQL
- **Build Tools**: Maven, Node.js, npm

---

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17 or higher** (for the backend ticketing logic)
- **Node.js 14.x or higher** (for the Angular frontend)
- **Maven** (for building and managing Java project dependencies)
- **Visual Studio Code**
- **IntelliJ Idea**

---

## Setup Instructions

### 1. Clone the Repository

Clone the repository and navigate to the project folder.

```bash
git clone https://github.com/omeshapasan2/Ticketing-System-Springboot-Angular-MySQL.git
cd ticketing-system
```

### 2. Build the Backend (Spring Boot Application)

Navigate to the root directory of your Spring Boot application and use Maven to build it:

```bash
cd Springboot
mvn clean install
```

### 3. Run the Backend Application

After building the application, you can start the Spring Boot backend using the following command:

```bash
mvn spring-boot:run
```

This will start the backend on `http://localhost:8080`.

### 4. Frontend (Angular or React Application)

If you're using Angular for the frontend, follow these steps:

1. Navigate to the frontend directory:
   ```bash
   cd Angular
   ```

2. Install the required dependencies:
   ```bash
   npm install
   ```

3. Start the Angular development server:
   ```bash
   ng serve
   ```
    or 
      ```bash
       npm start
      ```
This will start the frontend on `http://localhost:4200`.

---

## Authentication Features

### Register

Users can register to the system by creating an account. The **Register** form collects the following information:

- **Username**
- **Email**
- **Password**

When successfully registered, users will be redirected to the **Login** page.

### Login

After registering, users can log in using their **Username** and **Password**. Upon successful login, they will be directed to the **Admin Dashboard**.

---

## Routes and Endpoints

### 1. Frontend Routes:

- `/home`: Displays information about the system with links.
- `/register`: Displays the registration form where users can create an account.
- `/login`: Displays the login form to authenticate users.
- `/admin-dashboard`: The Admin Dashboard (accessible only after successful login).
- `/logout`: Redirects user back to login screen (accessible only after successful login).
  
### 2. Backend Routes (Spring Boot):

- `POST /auth/register`: Endpoint to register a new user.
- `POST /auth/login`: Endpoint to authenticate the user and generate a JWT token.
- `GET /api/ticketing/logs`: Endpoint for recieving add/remove ticket logs from backend.
  
---

## Usage Instructions

### Register a New User

1. **Go to** `/register` (Frontend page).
2. Fill in the **Username**, **Email**, and **Password**.
3. Click **Register** to create a new account.
4. After a successful registration, you'll be redirected to the **Login** page.

### Login

1. **Go to** `/login` (Frontend page).
2. Enter your **Username** and **Password**.
3. After successful login, you'll be redirected to the **Admin Dashboard** where you can configure and monitor the ticketing process.

#####  JWT Token Generation

- The `JwtTokenProvider` class will handle the generation and validation of JWT tokens for user authentication, creating tokens upon successful login.

### Admin Dashboard

Once logged in, you can:

- Configure the ticket pool (Total Tickets, Ticket Release Rate, Customer Retrieval Rate, Maximum Ticket Capacity).
- Start and stop the ticketing process.
- Monitor real-time logs of how vendors release tickets and how customers purchase them.


---
Home Page
![image](https://github.com/user-attachments/assets/cebc68dc-1546-4b9f-a41b-a552c32b3d56)

Register Page
![image](https://github.com/user-attachments/assets/7ba032a0-81b7-41e3-9e54-9e5e5438c82b)

Login Page
![image](https://github.com/user-attachments/assets/32335a7a-e80c-4a7e-874c-57ce2c70a55b)

Dashboard Page
![image](https://github.com/user-attachments/assets/2992115b-f56c-4a32-8d8a-5cd7c7e21bf3)




