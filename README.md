# Meetroom
## Introduction
Testing project for booking meeting
## System requirements:
* Java 8
* Maven
* Postgre SQL
## Start application
To use the app, you must complete the following steps
### Create the Database
* `psql -U postgres`
* `postgres=# create database meetroom;`
* `postgres=# create user admin with encrypted password '1q2a3z';`
### Restore a dump of the database
* `psql -U postgres meetroom < db/meetroom_dump`
### Run application
* `mvn clean package`
* `java -jar target/meet-room-0.0.1-SNAPSHOT.jar`
## Usage
Open link in a browser: http://localhost:8080/. You need to log in <br />
Database has next users:
login | password
--- | ---
alexei.stratonov | 123
alexei.filipov | 123
dmitrii.kuznetsov | 123

If you want reserve meeting room click a date in the header table and
on the opened page insert a start time, duration, title, guest username (login of another user) and description.
If you want to see a full detail you need click on the event card. Also you can update or delete object on detail page.


## Сonstraints
* One meeting room
* Event duration can be 00:30 - 23:59
* Events can't overlap
