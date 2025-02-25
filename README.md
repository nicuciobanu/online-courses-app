## Online Courses Application Demo

The application should allow us to register instructors with instructor details and courses.
Each course can have multiple students, and a student can be subscribed to various courses. 
The student must be available to leave a reviews.

API routes must be role-restricted, meaning that delete operations are performed by users with the ```ADMIN``` role, 
and create and update operations must be performed by users with the ```MANAGER``` and manager roles.
Users with the ```EMPLOYEE``` role can only access the view action for the instructor, student, and the other entities mentioned above.

Every action of create, update, and delete events must be sent to a Kafka topic, which will be used furthermore for the creation of metrics/statistics

#### Technologies
Java, Spring Boot(Hibernate, JPA, REST, Security), MySQL, Postman, Kafka, Docker.

### Setting up kafka

Start kafka container:
```
docker compose up
```

Create kafka topic:
```
docker-compose exec kafka kafka-topics.sh --create --topic online-courses-events --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
```

Read kafka messages:
```
docker-compose exec kafka kafka-console-consumer.sh --topic online-courses-events --from-beginning --bootstrap-server localhost:9092
```

### Setting up the DB

Install MySQL Database Server:
```
https://dev.mysql.com/downloads/mysql/
```

Install MySQL Workbench:
```
https://dev.mysql.com/downloads/workbench/
```

- Go to the MySQL Workbench and login in to the root, with the password you selected at the installation, 
after that, select the ‘File’ -> Open SQL script,  then select and execute ‘001-create-mysql-user.sql’ script.

- Add a new connection with the ‘mysql’ user and ‘mysql’ password, and run ‘002-create-online-courses-schema.sql’ and ‘003-create-users-and-roles.sql’ files.

Scripts location: src/main/resources/scripts

#### Notice
```
Change the login details in the table and make sure you use them correctly in Postman.
```


#### Get all instructors
```
curl --location 'http://localhost:8080/api/courses/instructors' \
--header 'Authorization: Basic Yn....' \
--header 'Cookie: JSESSIONID=ECD18EEA68EC531C3D6CA239AA5D3A6B'
```

#### Get instructor by id
```
curl --location 'http://localhost:8080/api/courses/instructors/2' \
--header 'Authorization: Basic Yn....' \
--header 'Cookie: JSESSIONID=ECD18EEA68EC531C3D6CA239AA5D3A6B'
```

#### Create instructor
```
curl --location 'localhost:8080/api/courses/instructors' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic Yn....' \
--header 'Cookie: JSESSIONID=ECD18EEA68EC531C3D6CA239AA5D3A6B' \
--data-raw '{
    "firstName": "John",
    "lastName": "Smith",
    "email": "johns@gmail.com"
}'
```

#### Update instructor
```
curl --location --request PUT 'localhost:8080/api/courses/instructors' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic c3...' \
--header 'Cookie: JSESSIONID=ECD18EEA68EC531C3D6CA239AA5D3A6B' \
--data-raw '{
    "id": 2,
    "firstName": "Timothy",
    "lastName": "Patterson",
    "email": "timp@gmail.com"
}'
```

#### Remove instructor by id
```
curl --location --request DELETE 'http://localhost:8080/api/courses/instructors/1' \
--header 'Authorization: Basic c3...' \
--header 'Cookie: JSESSIONID=ECD18EEA68EC531C3D6CA239AA5D3A6B'
```

#### Find instructor detail by id
```
curl --location 'http://localhost:8080/api/courses/instructors/instructor-details/3' \
--header 'Authorization: Basic Yn...'
```

#### Create instructor detail
```
curl --location 'http://localhost:8080/api/courses/instructors/instructor-details' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic c3...' \
--data '{
    "youtubeChannel": "https://www.youtube.com/watch?v=eIrMbAQSU34&t=106s&ab_channel=ProgrammingwithSusanPublic",
    "hobby": "Programming"
}'
```

#### Update instructor detail
```
curl --location --request PUT 'http://localhost:8080/api/courses/instructors/instructor-details' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic c3...' \
--header 'Cookie: JSESSIONID=6EF6057A0C56C9FFBA953CA861E06C22' \
--data '{
    "youtubeChannel": "https://www.youtube.com/watch?v=eIrMbAQSU34&t=106s&ab_channel=ProgrammingwithSusanPublic",
    "hobby": "Programming"
}'
```

#### Remove instructor detail
```
curl --location --request DELETE 'http://localhost:8080/api/courses/instructors/instructor-details/4' \
--header 'Authorization: Basic c3...' \
--header 'Cookie: JSESSIONID=6EF6057A0C56C9FFBA953CA861E06C22'
```

#### Assign instructor detail to instructor
```
curl --location --request PUT 'http://localhost:8080/api/courses/instructors/instructor-details/2/1' \
--header 'Authorization: Basic cG..' \
--header 'Cookie: JSESSIONID=6EF6057A0C56C9FFBA953CA861E06C22'
```

#### Create course
```
curl --location 'localhost:8080/api/courses' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic c3...' \
--data '{
    "title": "Learn Java and Spring Boot"
}'
```

#### Get courses by id
```
curl --location 'localhost:8080/api/courses/10' \
--header 'Authorization: Basic Yn...'
```

#### Update course
```
curl --location --request PUT 'localhost:8080/api/courses' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic cGV...' \
--data '{
    "id": 10,
    "title": "Learn Java and Spring Boot 3.6"
}'
```

#### Remove course by id
```
curl --location --request DELETE 'localhost:8080/api/courses/11' \
--header 'Authorization: Basic c3...'
```

#### Assign course to instructor
```
curl --location --request PUT 'localhost:8080/api/courses/2/10' \
--header 'Authorization: Basic c3R...'
```

#### Add review to course
```
curl --location 'localhost:8080/api/courses/reviews/10' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic c3R...' \
--data '{
    "comment": "Well done, great course!"
}'
```

#### Create student
```
curl --location 'localhost:8080/api/courses/students' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic c3...' \
--data-raw '{
    "firstName": "John",
    "lastName": "Whick",
    "email": "john.whick@gmaile.com"
}'
```

#### Get student by id
```
curl --location 'localhost:8080/api/courses/students/1' \
--header 'Authorization: Basic c3Rl...'
```

#### Update student
```
curl --location --request PUT 'localhost:8080/api/courses/students' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic c3...' \
--data-raw '{
    "id": 2,
    "firstName": "John",
    "lastName": "Wick",
    "email": "johnw@gmail.com"
}'
```

#### Delete student
```
curl --location --request DELETE 'localhost:8080/api/courses/students/2' \
--header 'Authorization: Basic c3...'
```

#### Assign student to course
```
curl --location --request PUT 'localhost:8080/api/courses/students/10/1' \
--header 'Authorization: Basic c3Rl...'
```
