# JWTProjectSpringBoot

##Setup:

1. Download an IDE for running the project.
2. Go to Spring Initializer (start.spring.io) and create a project.
3. Choose Maven project , Java 17 , and Spring Version as 2.7.6 .
4. Add dependency for :
   a. Spring data JPA
   b. Spring Security
   c. Spring Validation
   d. Spring Web
   e. H2 database
   f. Lombok
5. Initialize the project.
6. A zip file will be downloaded .
7. Unzip that and open in an IDE .
8. Alternative way for this open STS and intialize the project.

9. Now Clean the porject using command "MVN clean install -DskipTest=true to resolve the dependency
10. Build the project using command "MVN build"
11. Now run the project.
12. It will run on the port 8088:

13. Go to postman hit the URL : "http://localhost:8088/api/createUser" to create an user.
14. In Resquest body u have to provide (name , role , username , password) as raw in Json Format for Creating User.
15. Now hit the URL : "http://localhost:8088/api/auth/signin" .
16. In Resquest body u have to provide (username , password) as raw in Json Format for signin.
17. It will genrate the Bearer token.
18. You will get the the Bearer Token in the cookies.
19  The URL : "http://localhost:8088/api/admin/acc" will be work when you will be having the admin acess then only you will get message as "Only admit can Acesses".
20. In this we have to provide JWT Token in bearer token section from this it will validate the role.
20. The URL : "http://localhost:8088/api/user/acc" will be work when you will be having the user acess then only you will get message as "Only Users can Acesses".
21. n this we have to provide JWT Token in bearer token section from this it will validate the role.
22. The URL : "http://localhost:8088/api/all/acc" will work all the user "Only Users can Acesses".
23. NO JWT required in this.

24. Now go to "http://localhost:8088/h2-console" to open database.
25. In that you can able to see the database named as authentication.
26. In authentication table users will be created on running the project.

27. On creating user it will save the user details.
