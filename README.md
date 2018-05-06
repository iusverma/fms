# This application is build on follwing
1. Java version 1.8
2. Maven version 3.5.2

# How to build
1. Clone repository to you local machine
2. Run mvn clean install inside fms folder

# How to run server
1. Run following command inside fms folder
java -jar target/fms-0.0.1-SNAPSHOT.jar

# Application URIs (all request response are inside fms/samples folder)

- NOTE:- Please make sure service is up and running (see section How to run server), before trigger PostMan calls.

1. Add a new friend
- URI - PUT - http://localhost:8080/friend
- Sample Request - friend-request.json
- Sample Response - friend-response.json

2. Get friends for an account
- URI - POST - http://localhost:8080/friends
- Sample Request - get-user-friends-request.json
- Sample Response - get-user-friends-response.json

3. Get common friends for two used
- URI - POST - http://localhost:8080/friends/common
- Sample Request - get-common-friend-request.json
- Sample Response - get-common-friend-response.json

4. Add a new subscriber
- URI - PUT - http://localhost:8080/subscriber
- Sample Request - subscriber-request.json
- Sample Response - subscriber-resposne.json

5. Block or delete a subscribtion
- URI - DELETE - http://localhost:8080/subscriber
- Sample Request - subscriber-request.json
- Sample Response - subscriber-resposne.json

6. Send update to subscription list
- URI - POST - http://localhost:8080/subscribers
- Sample Request - publish-message-request.json
- Sample Response - publish-message-response.json

7. Find all connection, friends and subscription with full details
- URI - GET - http://localhost:8080/friends
- Sample Request - NA
- Sample Response - get-all-connections-response.json

8. Delete a existing friend connetion
- URI - DELETE - http://localhost:8080/friend
- Sample Request - friend-request.json
- Sample Response - friend-response.json

# Alternatively you can also download the postman collection from here
https://www.getpostman.com/collections/6e3222a6836b405136dd
