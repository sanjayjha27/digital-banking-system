# Digital Banking & Transaction Management System

Portfolio-grade banking simulation built with Java, Spring Boot, Spring Security/JWT, JPA and PostgreSQL.

## MVP included
- Registration + automatic bank account number
- Login + JWT authentication
- Deposit
- Withdrawal with insufficient-balance validation
- Account-to-account transfer
- Transaction history
- PostgreSQL persistence
- Pessimistic locking for account updates

## Run
1. Create PostgreSQL database: `digital_banking`.
2. Update `src/main/resources/application.properties` username/password if needed.
3. Run: `mvn spring-boot:run`
4. Backend: `http://localhost:8080`

Do not use real money, credentials or production banking data. This is a banking simulation for learning/portfolio use.
