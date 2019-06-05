Author:  Bill Homan
Date:    6/4/2019

This repository implements the requirements of the Tech Challenge requested by WSECU for a Backend developer position

The requirements for this challenge are as follows:

Build a RESTful service using java and the spring framework to manage users. You can use any sort of persistence including in memory for storing the user data.
A user model must have at least these fields:
    - username
    - name
    - email
 
The solution be able to meet the following needs:
    - Find a user by username
    - Create a user
    - Delete a user
    - Update a user's email and name

Not included in the requirements is anything dealing with security, so none has been included.

The project is built with Maven and the Spring Tool Suite version 3.9.8.  

SPECIAL NOTES
The requirement was to use "any sort of persistence" for storing data.  I attempted to use the H2 database and was successful in getting
the schema built and data inserted.  However, when I tried to use the JdbcTemplate to access the data, I consistently recieved a
NullPointerException.  I was unable to figure out the cause.

The code that accesses the H2 database still exists in the UserService class.  However, this is untested due to the reasons above.

Instead, there is a UserService_Temp class that is actually used by the application.  This is a simple list of User object and is
added to/removed from/updated based on the HTTP requests.  If the H2 database is enabled or another database put in its place, 
this Temp class can be deleted and the service definition in the constructor for UserController can be changed to use the
UserService class.
