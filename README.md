UserProfileMgr

UserProfileMgr is a user profile management microservice that provides functionality for managing user profiles within an application or system. The project is built using Java and the Spring Boot framework, and it utilizes a MySQL database for storing user data.
Technologies Used

    Java
    Spring Boot
    MySQL

Installation

Before running the application, you must have Docker and IntelliJ installed on your machine. Here are the steps to install and run the application:

    Clone the repository: git clone https://github.com/[username]/UserProfileMgr.git
    Navigate to the project directory: cd UserProfileMgr
    Run the Docker Compose file to start the MySQL container: docker-compose up
    Open the project in IntelliJ and run the UserProfileMgrApplication class



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
