# Employee Salary Management System

This project is an **Employee Salary Management System** designed to manage and track employees' salary details within a company. The system handles CRUD operations for employees, maintains relationships among entities, validates input data, calculates employee salaries, transfers salaries, and provides summaries like salary sheets and account balances.

## Features

- **CRUD Operations**: Full CRUD functionality for managing each entity (such as employees, accounts, and salary records).
- **Entity Relationships**: Maintains appropriate relationships between entities (e.g., employee to salary, account to transactions).
- **Data Validation**: Provides robust input validation to ensure data integrity.
- **Salary Calculation**: Calculates individual employee salaries based on rank, role, and other defined parameters.
- **Salary Transfer**: Automates the transfer of salaries from the main company account to individual employee accounts.
- **Salary Sheet**: Generates a salary sheet showing the name, rank, and salary of each employee.
- **Account Summaries**: Displays the total paid salary and the remaining balance of the company's main account.

## Project Structure

- `Employee` - Entity representing an employee with details such as name, rank, and bank account.
- `Account` - Entity representing the main company account and individual employee accounts.
- `Salary` - Entity representing salary calculations, payment statuses, and salary records for each employee.
- `Transaction` - Entity that tracks transactions from the main account to employee accounts.

## Technologies

- Programming Language: [Java]
- Frameworks: [Spring Boot]
- Database: [MySQL]

## Getting Started

### Prerequisites

- Ensure you have [Database, e.g., MySQL] installed and configured.
- Install [Other Required Software/Dependencies].

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/employee-salary-management-system.git
   cd employee-salary-management-system
 
