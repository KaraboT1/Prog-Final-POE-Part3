# Prog-Final-POE-Part3
Java application implementing user registration, secure login, and a full messaging system. Includes input validation, message storage, search, delete, reporting, and JSON support. Built for PROG5121 assessment requirements.


  Features
- User Registration and login
  - Username validation (contains underscore, max 5 characters)
  - Password complexity rules (min 8 chars, uppercase, digit, special character)
  - South African cell number validation (`+27` or `0` format)
  - Secure authentication

- Message Management
  - Unique 10-digit Message ID
  - Message Hash generation for tracking
  - Max length limit: 250 characters
  - Options: Send, Store, or Disregard messages
  - Separate storage arrays for sent, stored, and disregarded messages

- Stored Message tools
  - View sender and recipient details
  - Find the longest message
  - Search by Message ID or Recipient number
  - Delete messages using their hash
  - Generate full system report
  - JSON-formatted message storage

- Testing
  - JUnit 5 unit tests covering all functionality
  - Clear error and success messages as per requirements

Technologies used
- Java SE
- NetBeans IDE
- JUnit 5 for testing
