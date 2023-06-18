# CME Group API Challenge
A REST API that accepts a username and text value, and returns whether that value is a palindrome. The application will process the request against the pre-determined validation supplied in the `application.yml`.

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
