# Project Brief: Bank Account Management System

## Students: Carles Sanahuja / Gerard Borràs

This project is a bank account management system that allows creating different types of bank accounts such as checking accounts, savings accounts, and credit cards. The system is developed using Spring Boot and utilizes a MySQL database.

## Main Features

- Allows creating different types of bank accounts:
    - Checking accounts
    - Savings accounts
    - Credit cards
- Management of account holders (primary and secondary)
- Transfers between accounts
- Application of interest and penalties depending on the account type
- Status system to manage the active or blocked state of accounts

## Project Structure

### Models

--> Models represent the data structure of the application. We will define different classes with their attributes and methods. Models represent the tables in the database.
- **Account**: Abstract class representing the common structure of all bank accounts.
- **AccountHolders**: Class representing account holders.
- **Checking**: Subclass of Account for managing checking accounts.
- **Savings**: Subclass of Account for managing savings accounts.
- **CreditCard**: Subclass of Account for managing credit cards.
- **StudentChecking**: Subclass of Account for managing student accounts.

### Services

--> Allows us to execute logic when a controller is activated.

- **AccountService**: Handles the creation and management of all bank accounts.

### Repositories

--> We have different interfaces to interact with the database, which allows us to send SQL code.

- **AccountHoldersRepository**: Interface for managing account holders.
- **AccountRepository**: Interface for managing all bank accounts.
- **CheckingRepository**: Specific interface for managing checking accounts.
- **CreditCardRepository**: Specific interface for managing credit cards.
- **SavingsRepository**: Specific interface for managing savings accounts.
- **StudentCheckingRepository**: Specific interface for managing student accounts.
- **AdminsRepository**: Interface for managing system administrators.
- **ThirdPartyRepository**: Interface for managing third-party users.
- **UserRepository**: Interface for managing system users.

### Controllers

--> Allows us to read the URLs

- **AccountController**: Handles operations related to bank accounts.

## Main Functionalities

1. Creation of different types of bank accounts.
2. Management of primary and secondary account holders.
3. Transfers between accounts.
4. Application of interest and penalties depending on the account type.
5. Management of account status (active/blocked).

## Technologies Used

- Intellij as the development environment for working with Java.
- Java Spring Boot framework to facilitate and accelerate development.
- DBeaver for database management.
- Lombok to reduce boilerplate code.
- JPA/Hibernate for object-relational mapping.

## Configuration

The project uses a YAML-based configuration for Spring Boot. The main configuration is located in the `application.properties` file. This file contains information about the database connection, Hibernate settings, and other important parameters.

## Database Relationships

The database structure is designed to represent relationships between different types of bank accounts and their holders. Here are the main relationships:

1. An account has a primary owner (AccountHolders).
2. An account can have up to two secondary owners (AccountHolders).
3. Each account type (Checking, Savings, CreditCard, StudentChecking) inherits from Account and has its own specific tables.
4. Transactions are recorded in a separate table called Transaction, related to each account type.
5. System administrators are represented by a separate table called Admins.
6. Third-party users and system users are represented by a separate table called Users.

## Database Tool Used

DBeaver was used as a database management tool to interact with the MySQL database. DBeaver provides an intuitive graphical interface that facilitates viewing and manipulating tables, as well as executing SQL queries.

## Detailed Project Explanation

The Brief project is a complete bank account management system that allows managing different types of bank accounts. Let's explore in more detail how this system works:

1. **Data Models**:
    - The Account model is the abstract base class that defines the common structure of all bank accounts.
    - Each account type (Checking, Savings, CreditCard, StudentChecking) inherits from Account and adds specific fields relevant to that account type.
    - AccountHolders represent the owners of the accounts, including personal information and contact details.

2. **Services and Repositories**:
    - AccountService handles the creation and management of all bank accounts.
    - Each account type has its own specific service and repository (e.g., CheckingService and CheckingRepository).
    - This allows great flexibility and independent maintenance of each account type.

3. **Controllers**:
    - AccountController acts as the gateway for REST operations related to bank accounts.
    - It uses the appropriate services and repositories to process requests and update the database.

4. **Main Features**:
    - Account Creation: Allows creating new accounts of different types, specifying owners and other necessary details.
    - Owner Management: Allows adding, modifying, or removing primary and secondary owners of an account.
    - Transfers: Implements the ability to transfer money between accounts of different customers.
    - Interest and Penalties: Calculates and applies monthly compound interest for savings accounts and penalties for insufficient minimum balance in checking accounts.
    - Account Status: Allows marking an account as active or blocked.

5. **Security**:
    - The system includes a basic security layer using JWT (JSON Web Tokens) for user authentication.
    - Administrators have access to additional functions to manage the system.

6. **Transactions**:
    - All operations that modify an account's balance are recorded in a separate transactions table.
    - This allows auditing and tracking of financial movements.

7. **Third-Party Integration**:
    - The system includes a layer to integrate with external services, allowing functionalities like online payments or balance verification.

# Conclusion

The Brief project represents a robust and flexible bank account management system, designed to meet the needs of a modern financial institution. Some key strengths of this system include:

- Modularity: The inheritance and polymorphism-based structure allows easy extensibility and maintenance of different types of bank accounts.
- Security: It incorporates basic security measures such as JWT authentication and permissions management for administrators.
- Flexibility: It allows managing different types of accounts (checking, savings, credit, student) with specific functionalities for each one.
- Scalability: It uses common design patterns and SOLID principles, making it easier to expand in the future.
- Database Integration: It is designed to work efficiently with MySQL, using Spring Data JPA for seamless integration.

This project provides a solid foundation for developing more complex financial applications. Its modular structure and object-oriented development practices make it easy to add new features or adapt it to the specific needs of a bank or financial institution.

We have successfully managed, organized, and developed the entire project.
