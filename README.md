# MovieReview :clapper: :movie_camera:

I built an application that stores movie information in PostgreSQL database and also stores reviews created by user.  

Visitors to the site can view reviews and movie data without being logged in, but need to log in to add a movie or review information to the database. Logged in users will see all movies but only their own reviews.

### Built with

* Maven
* Java 8 (JDK 1.8.131)
* Spring Boot
* Spring Security
* HTML5
* CSS3
* Thymeleaf 
* Bootstrap
* PostgresSQL
 

### To run locally

You will need to create a Postgres database 

`createdb [dbname]`

`psql [dbname]`

with the following schema:

```
CREATE TABLE movie (
    id SERIAL INTEGER PRIMARY KEY,
    title VARCHAR(255),
    genre VARCHAR(255),
    imdblink VARCHAR(255),
    releasedate DATE,
);

CREATE TABLE review (
    id SERIAL INTEGER PRIMARY KEY,
    rating VARCHAR(255),
    movie_id INTEGER REFERENCES movie(id),
    user_data_id INTEGER REFERENCES user_data(id)
);

CREATE TABLE role (
    id SERIAL INTEGER PRIMARY KEY, 
    name VARCHAR(20)
);

CREATE TABLE user_data (
    id SERIAL INTEGER PRIMARY KEY, 
    username VARCHAR(50),
    age INTEGER,
    gender CHAR,
    occupation VARCHAR(100),
    password VARCHAR(50),
    role_id INTEGER REFERENCES role(id)
);
```

Next we'll add information to the database for the role and user:

```
INSERT INTO role (name) VALUES ('ROLE_USER');

INSERT INTO user_data (username, age, gender, occupation, password, role_id) VALUES ('[your_name]', '[your_age]', '[m_or_f]', '[your_job]', '[your_password]', 1);

```

* Clone this repository and in your terminal type `git clone` and paste the url that you copied. 
* Open the application.properties file and replace **moviereviewscdc** in the `spring.datasource.url` with your database name, and update the username and password if applicable to your database. 
* Run `mvn clean package` from your project directory (this will build the project and create a jar file in the target directory).


To run the project type: `java -jar target/moviereviews-0.0.1-SNAPSHOT.jar`, then open a browser window and go to `localhost:8080`. Log in using the username and password you inserted into your database, and start adding and reviewing some movies!