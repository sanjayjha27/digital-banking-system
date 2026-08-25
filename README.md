# Digital Banking & Transaction Management System

A full-stack banking simulation for portfolio/learning use.

## Stack
- Backend: Java 25, Spring Boot 4.1, Spring Security, JWT, Spring Data JPA
- Database: PostgreSQL
- Frontend: React + Vite + Axios

## Features in this first build
- Register/login
- JWT authentication
- Automatic account number
- Account balance
- Deposit/withdraw
- Account-to-account transfer
- Transaction history
- Basic responsive dashboard

## Run backend
Create a PostgreSQL database named `digital_banking`, then edit backend `application.properties` with your PostgreSQL username/password.

From `backend`:
`mvn spring-boot:run`

## Run frontend
From `frontend`:
`npm install`
`npm run dev`

Open the Vite URL shown in the terminal (normally http://localhost:5173).

This is a simulation and must not be used with real money, real banking credentials, or production financial data.
