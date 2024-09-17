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

## Project Statement

**1. The system must have 4 types of accounts: StudentChecking, Checking, Savings, and CreditCard.**

**1.1 Checking Accounts**

Checking accounts must have:

- A balance
- A secret key
- A primary owner
- An optional secondary owner
- A minimum balance
- A penalty fee
- A monthly maintenance fee
- A creation date
- A status (FROZEN, ACTIVE)

**1.2 Student Checking Accounts**

Student checking accounts are identical to checking accounts except they DO NOT have:

- A monthly maintenance fee
- A minimum balance

**1.3 Savings Accounts**

Savings accounts are identical to checking accounts except that:

- They do not have a monthly maintenance fee
- They have an interest rate

**1.4 Credit Card Accounts**

Credit card accounts have:

- A balance
- A primary owner
- An optional secondary owner
- A credit limit
- An interest rate
- A penalty fee

**2. The system must have 3 types of users: administrators, account holders, and third parties.**

**2.1 Account Holders**

Account holders must be able to access their own accounts, and only theirs, when they enter the correct credentials using basic authentication. Account holders have:

- A name
- Date of birth
- A primary address (which should be a separate address class)
- An optional mailing address

**2.2 Administrators**

Administrators only have a name.

**2.3 Third-Party Users**

Third-party accounts have a hashed key and a name.

**3. Administrators can create new accounts. When creating a new account, they can create checking, savings, or credit card accounts.**

**3.1 Savings Accounts**

- Savings accounts have a default interest rate of 0.0025.
- Savings accounts can be instantiated with a different interest rate, with a maximum rate of 0.5.
- Savings accounts should have a default minimum balance of 1000.
- Savings accounts can be created with a minimum balance below 1000 but not lower than 100.

**3.2 Credit Card Accounts**

- Credit card accounts have a default credit limit of 100.
- Credit card accounts can be created with a credit limit higher than 100 but not exceeding 100,000.
- Credit card accounts have a default interest rate of 0.2.
- Credit card accounts can be created with an interest rate lower than 0.2 but not lower than 0.1.

**3.3 Checking Accounts**

- When creating a new checking account, if the primary owner’s balance is less than 24, a student checking account should be created; otherwise, a regular checking account should be created.
- Checking accounts must have a minimum balance of 250 and a minimum monthly maintenance fee of 12.

**4. Interests and fees must be applied appropriately.**

**4.1 Penalty Fee**

- The penalty fee for all accounts must be 40.
- If any account falls below the minimum balance, the penalty fee must be automatically deducted from the balance.

**4.2 Interest Rate**

- Savings account interest is added to the account annually at the specified interest rate per year. This means that if I have 1,000,000 in a savings account with an interest rate of 0.01, 1% of 1 million is added to my account after 1 year. When accessing the balance of a savings account, the system should determine if 1 year or more has passed since the account was created or since the last time interest was added and should add the corresponding interest to the balance if necessary.

- Credit card interest is added to the balance monthly. If the interest rate is 12% (0.12), 1% interest will be added to the account monthly. When accessing the balance of a credit card account, the system should verify if 1 month or more has passed since the account was created or since interest was last added, and if so, add the corresponding interest to the balance.

**5. Account Access**

**5.1 Administrators**

- Administrators should be able to access and modify the balance of any account.

**5.2 Account Holders**

- Account holders must be able to access the balance of their own account.
- Account holders must be able to transfer money from any of their accounts to any other account (regardless of ownership). The transfer should only be processed if the account has sufficient funds. The user must provide the name of the primary or secondary owner and the account ID that should receive the transfer.

**5.3 Third-Party Users**

- There must be a way for third-party users to receive and send money to other accounts.
- Third-party users must be added to the database by an administrator.
- To receive and send money, third-party users must provide their encrypted key in the HTTP request header. They must also provide the amount, the account ID, and the account’s secret key.

**6. Technical Requirements of the Project**

- Include a Java/Spring Boot backend.
- Everything should be stored in MySQL database tables.
- Include at least 1 GET, POST, PUT/PATCH, and DELETE route.
- Include authentication with Spring Security.
- Include unit and integration tests.
- Include robust error handling.
- You must use the Money class for all currencies and BigDecimal for any other decimal or large number.


# Conclusion

The Brief project represents a robust and flexible bank account management system, designed to meet the needs of a modern financial institution. Some key strengths of this system include:

- Modularity: The inheritance and polymorphism-based structure allows easy extensibility and maintenance of different types of bank accounts.
- Security: It incorporates basic security measures such as JWT authentication and permissions management for administrators.
- Flexibility: It allows managing different types of accounts (checking, savings, credit, student) with specific functionalities for each one.
- Scalability: It uses common design patterns and SOLID principles, making it easier to expand in the future.
- Database Integration: It is designed to work efficiently with MySQL, using Spring Data JPA for seamless integration.

This project provides a solid foundation for developing more complex financial applications. Its modular structure and object-oriented development practices make it easy to add new features or adapt it to the specific needs of a bank or financial institution.

We have successfully managed, organized, and developed the entire project.
