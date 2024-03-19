# Social Media Blog API

## Background 

This project is a backend-only API of a social media app. This project leverages Spring, a popular Java web application framework. Spring allows for automatic data injection and configuration, data persitence, endpoints, and conventional CRUD operations.

## Tables 

The following tables are initialized in the project's database upon startup using configuration details within application.properties and a provided SQL script.

### Account
```
account_id integer primary key auto_increment,
username varchar(255) not null unique,
password varchar(255)
```

### Message
```
message_id integer primary key auto_increment,
posted_by integer,
message_text varchar(255),
time_posted_epoch long,
foreign key (posted_by) references Account(account_id)
```

# Requirements

Project:
- Has valid application.properties and valid database entities
- Leverages Spring Boot framework

All user stories are satisfied

## Leverages Spring Boot Framework

Project leverages the Spring framework, dependency injection, autowire functionality and/or Spring annotations.

In addition to functional test cases, "SpringTest" will verify that you have leveraged the Spring framework, Spring Boot, Spring MVC, and Spring Data.

SpringTest.java verifies
 - there are beans for AccountService, MessageService, AccountRepository, MessageRepository, and SocialMediaController classes
 - that AccountRepository and MessageRepository are working JPARepositories based on their corresponding Account and Message entities
 - MVC is leveraged by checking for Spring's default error message structure

## User Stories

### 1: New User Registrations

As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register.

The registration is successful if and only if:
- the username is not blank
- the password is at least 4 characters long
- an Account with that username does not already exist

For successful registrations:
- the response body contains a JSON representation of the Account, including its account_id
- the response status is the default 200 OK
- the new account is persisted to the database

For unsuccessful registrations due to a duplicate username:
- the response status is 409 (Conflict)

For all other unsuccessful registrations:
- the response status is 400 (Client error)

### 2: User Logins

As a user, I should be able to verify my login on the endpoint POST localhost:8080/login.

The login is successful if and only if:
- the username provided in the request body matches an existing account
- the password provided in the request body matches the existing account having provided username referenced above

For successful logins:
- the response body contains a JSON representation of the account, including its account_id
- the response status is the default 200 OK

For unsuccessful logins:
- the response status is 401 (Unauthorized)


### 3: Creation of New Messages

As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages.

The creation of the message is successful if and only if:
- the message_text is not blank
- the message_text is not over 255 characters
- posted_by refers to a real, existing user

For successful new message creations:
- the response body contains a JSON representation of the message, including its message_id
- the response status is the default 200 OK
- the new message is persisted to the database

For unsuccessful message creations:
- the response status is 400 (Client error)

### 4: Retrieve All Messages

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.

- The response body should contain a JSON representation of a list containing all messages retrieved from the database. It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default.

### 5: Retrieve a Given Message By Its message_id

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.

- the response body contains a JSON representation of the message identified by the provided message_id
- if there is no such message, the response body is empty
- the response status is always the default 200 OK

### 6: Delete a Given Message By Its message_id

As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.

For successfully deleted messages:
- the matching message having the provided message_id is removed from the database
- the response body contains the number of rows updated (1)
- the response status is the default 200 OK

For unsuccessfully deleted messages:
- if the message did not exist:
    - the response status is 200
    - the response body is empty

### 7: Update a Given Message's message_text By Its message_id

As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}.
The request body contains new message_text values to replace the message identified by message_id.
The request body is not guaranteed to contain any other information.

The update of a given message's message_text is successful if and only if:
- the provided message_id exists
- the new values of message_text are not blank
- message_text does not exceed 255 characters

For successful updates:
- the response body contains the number of rows updated (1)
- the response status is the default 200 OK
- the message existing on the database reflects the updated message_text

For all unsuccessful message_text updates:
- the response status is 400 (Client error)

### 8: Retrieve All Messages Belonging to account_id

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.

If there are messages:
- the response body contains a JSON representation of a list containing all messages posted by a particular user retrieved from the database.
- the response status is the default 200 OK

If there are no messages:
- the returned list is empty
- the response status is the default 200 OK