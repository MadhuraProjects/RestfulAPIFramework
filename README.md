An end to end implementation of RestfulAPI project to check whether a place with user defined attributes like name, address, latitude, longitude etc is successfully added to the demo server.
The steps performed are as follow: 
-> The user added a place in the demo server having json payload body and expected a status code 200 OK after successfull addition of the same.
-> After adding the place the user is expected to fetch the complete details of the place from the demo server through GET http request
-> The user can update/modify the details as per requirement using PUT http request
-> The user can also delete the place whenever required using DELETE http request
All the actions are performed by hitting the end points of the respective add/get/update/delete requests using Rest Assured coding standards in Cucumber BDD framework.
Concepts like: POJO class serialization deserialization is used to handle dynamic and complex json effectively.
Smart feature lines have been used for creating clean and robust framework and optimal java coding approaches using OOPS design pattern.
