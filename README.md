API Endpoints

Here are the available endpoints for accessing the application's functionality:
Sign Up

This endpoint allows users to create a new account.

    URL: /api/users/signup/all
    HTTP Method: POST
    Request Body:

perl

{
    "firstName": "esther",
    "lastName": "po",
    "email": "po@example.com",
    "password": "po@123"
}

    Response Body:

perl

{
    "firstName": "esther",
    "lastName": "po",
    "email": "po@example.com",
    "password": "po@123"
}

Get User By ID

This endpoint allows users to retrieve their account information by ID.

    URL: api/users/{id}
    HTTP Method: GET
    Response Body:

perl

{
    "id": 1,
    "firstName": "esther",
    "lastName": "po",
    "email": "po@example.com"
}



Conclusion

Thank you for checking out the UserProfileMgr microservice. If you have any questions or feedback, please don't hesitate to contact me.
