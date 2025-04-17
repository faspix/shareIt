# ShareIt

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/66e33c8b4d6340ed8627ebcf531896f3)](https://app.codacy.com/gh/faspix/shareIt/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/66e33c8b4d6340ed8627ebcf531896f3)](https://app.codacy.com/gh/faspix/shareIt/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)

## Overview
This web application allows users to rent items.  
User can add an item he wants to share, find the desired item and rent it for specific period. The service allows user to make a request to book an item for a specific period of time, which can be confirmed or rejected by the owner of the item. After the end of the booking user can leave a feedback.  
For adding or booking items it is necessary to be authorised, the system is represented by both session authentication and OAuth2 token.
Unauthorised user can view the list of available items, search through it, as well as view detailed information about a particular item.  
Owner of the item can also view information about the previous and next booking.
## Technologies
* REST API
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* JUnit
* Liquibase 
* Docker
## Available Endpoints:
### All endpoints are available at `/swagger-ui/index.html`
`POST /items` - add item  
`PATCH /items/{itemId}` - edit item  
`GET /items/{itemId}` - view information about a specific item  
`DELETE /items/{itemId}` - delete item  
`GET /items` - view all items owned by the user  
`GET /items/search?text={query}` - search for items by name or description  
`POST /items/{itemId}/comment` - leave a review about an item

`POST /users` - create a user account  
`PATCH /users` - update user information  
`DELETE /users` - delete user  
`GET /users/{userId}` - view information about user  
`GET /users` - list all users  

`POST /bookings` - leave a booking request  
`PATCH /bookings/{bookingId}?approved=true/false` - confirm/reject booking request  
`GET /bookings/{bookingId}` - view information about booking  
`GET /bookings?state={booking state}` - view all bookings of the current user  
`GET /bookings/owner?state={booking state}` - view all bookings for the current user's items
