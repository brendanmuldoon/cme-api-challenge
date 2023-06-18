![Contributors](https://img.shields.io/github/contributors/brendanmuldoon/cme-api-challenge)
![Issues](https://img.shields.io/github/issues/brendanmuldoon/cme-api-challenge)
[![Java 11](https://img.shields.io/badge/Java-11-blue.svg)](https://java.oracle.com)
[![Spring Boot 2.7.12](https://img.shields.io/badge/Spring%20Boot-2.7.12-yellow.svg)](https://spring.io/projects/spring-boot)
[![Version](https://img.shields.io/badge/Version-1.0.0--SNAPSHOT-purple.svg)](https://your-project-url)


# CME Group API Challenge
A REST API that accepts a username and text value, and returns whether that value is a palindrome. A palindrome is a word, number, phrase, or other sequence of characters which reads the same backward as forward, such as madam or kayak.

# Assumptions

  * The API stores all VALID values processed regardless of whether or not it is a palindrome. Requests that do not pass validation are not stored
  * *For performance reasons the API response should not be dependent/blocked by the completion of the permanent persistence* - it was assumed that this meant using messaging for async commnication
  * For the purposes of data consitency, all processed values are stored in lowercase
  * Specification mentions a username in the request but it is not clear what has to be done with it. The current storage of processed values is in thhe following format: `String: Boolean`, ie, `"kayak": true`. If required, then the solution could be changed to use the username as the key, and the value could be a `HashMap` like currently


# Base Path
http://localhost:8080

# Resources
POST - /api/isPalindrome

# Request & Response
### Valid Requests
```json
{
    "username": "username",
    "textValue": "kayak"
}
```
```json
{
    "username": "username",
    "textValue": "test"
}
```
### Successful Responses
```json
{
    "code": 200,
    "httpStatus": "OK",
    "data": "isPalindrome: true"
}
```
```json
{
    "code": 200,
    "httpStatus": "OK",
    "data": "isPalindrome: false"
}
```
### Error Responses
```json
{
    "code": 400,
    "httpStatus": "BAD_REQUEST",
    "message": "Validation error : Request textValue cannot be blank"
}
```
```json
{
    "code": 400,
    "httpStatus": "BAD_REQUEST",
    "message": "Validation error : Request textValue cannot be null"
}
```
```json
{
    "code": 400,
    "httpStatus": "BAD_REQUEST",
    "message": "Validation error : Request textValue contains invalid characters"
}
```
# Future Extension
### Messaging
The api currently makes use of the in-memory message system `ActiveMQ`. To add your own messaging implementation you can
add a new config in the `com.cme.palindromeapi.config` folder and configure it to your requirements. Then, you can implement the `Publish` &
`Listener` interfaces to define your own implementation.
### Data Storage
The data storage currently adds data to a text file, `processed-values-db.txt`. To add your own data storage implementation, simply implement the
`PalindromeRepository` interface and configure your own code. You will also need to add your configurations in the 
`application.yml`.
### Pattern Validation
To easily add further validations over time simply update the `application.yml` like so:
```
properties:
  patterns:
    - "\\s|\\d"
    - "new pattern1"
    - "new pattern2"
    ...
```
