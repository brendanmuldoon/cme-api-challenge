![Contributors](https://github.com/brendanmuldoon/cme-api-challenge.git)

# CME Group API Challenge
A REST API that accepts a username and text value, and returns whether that value is a palindrome. A palindrome is a word, number, phrase, or other sequence of characters which reads the same backward as forward, such as madam or kayak.

# Assumptions

  * The API stores all VALID values processed regardless of whether or not it is a palindrome. Requests that do not pass validation are not stored
  * *For performance reasons the API response should not be dependent/blocked by the completion of the permanent persistence* - it was assumed that this meant using messaging for async commnication


# Base Path
http://localhost:8080

# Resources
POST - /api/isPalindrome

# Request & Response
### Valid Request
```json
{
    "username": "username",
    "textValue": "kayak"
}
```
### Successful Response
```json
{
    "code": 200,
    "httpStatus": "OK",
    "data": "isPalindrome: true"
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
