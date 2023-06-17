# cme-api-challenge
A REST API that accepts a username and text value, and returns whether that value is a palindrome

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
### Response
```
{
    "code": 200,
    "httpStatus": "OK",
    "data": "isPalindrome: true"
}
```
