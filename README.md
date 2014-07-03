RESTFiddle
==========

[![Build Status](https://travis-ci.org/ranjan-rk/restfiddle.svg?branch=master)](https://travis-ci.org/ranjan-rk/restfiddle)

An Enterprise-grade API Management Tool for Teams. RESTFiddle helps you design, develop, test and release APIs.

This project is under active development. Version 1.0 Coming Soon!

Why RESTFiddle?
==========

We all usually work as a team- be it software development or testing. What consumes most of your time while working in a group? Collaboration. You need to share stuff with your colleague. It is a painful exercise with a lot of limitations and doesn't work seamlessly.

How does RESTFiddle help you? If you are developing or testing REST APIs, it will help you do that with ease. It will let a team or multiple teams work together effortlessly. You just have to focus on your APIs. Rest all is taken care by the tool.

Time is valuable, so productivity is important. We have crafted RESTFiddle to avoid you fiddling with sharing or collaboration work and increase your productivity!

Initial Design
==========

![alt text](https://raw.githubusercontent.com/ranjan-rk/restfiddle/gh-pages/images/rf_screenshot2.jpg "Initial Design")


Building From Source
==========

##### Prerequisites

* JDK 7 or later

* Maven 3.0+

* MySQL (optional)

##### Build

```
mvn clean install
```

##### Run

```
mvn spring-boot:run
```

##### Access

```
Default username/password : rf/rf
```

##### Debug

```
mvnDebug spring-boot:run
```

Note : To avoid java.lang.OutOfMemoryError: PermGen space, use the following command:

```
MAVEN_OPTS="-XX:PermSize=256m -XX:MaxPermSize=512m" mvn spring-boot:run 
```

##### MySQL configuration:

Go to *src/main/resources/env-development.properties* and update database url, username and password. Here is how the sample configuration looks like:

```
database.driver=com.mysql.jdbc.Driver

database.url=jdbc:mysql://localhost:3306/restfiddle

database.username=root

database.password=
```

To use *Hsqldb* as in-memory database, use the following configuration:

```
database.driver=org.hsqldb.jdbcDriver

database.url=jdbc:hsqldb:mem:restfiddle

database.username=sa

database.password=
```


Technology Stack
==========

* Spring (http://spring.io/)

* Hibernate (http://hibernate.org/)

* HttpComponents (http://hc.apache.org/)

* Logback (http://logback.qos.ch/)

* jQuery (http://jquery.com/)

* Bootstrap (http://getbootstrap.com/)

* Backbone (http://backbonejs.org/)

* MySQL (http://www.mysql.com/)

* Tomcat (http://tomcat.apache.org/)


Contribute
==========

You're interested in contributing to RESTFiddle? AWESOME. Here are the basic steps:

- Make sure you have a [GitHub Account](https://github.com/signup/free)
- Fork RESTFiddle from here : https://github.com/ranjan-rk/restfiddle/
- Clone your fork  
- Make your changes
- Make sure everything is working fine
- Format your code (see below)
- Submit a pull request

##### Code formatting :

- If you're an Eclipse user, use the following code formatter : https://github.com/ranjan-rk/restfiddle/blob/master/tools/restfiddle_code_formatter.xml
- You should also activate automatic formatting and organizing imports on save.

##### GitHub help : 

- Forking a repo - https://help.github.com/articles/fork-a-repo
- Creating a pull request - https://help.github.com/articles/creating-a-pull-request
- Syncing a fork - https://help.github.com/articles/syncing-a-fork
 
Release Date
==========

Version 1.0 is coming soon. Here is the current status of the project:

Login/Logout working (default username/password : rf/rf).

Creating and switching between Workspaces working.

Creating and showing Projects working.

GET/POST/PUT/DELETE operation with JSON data is supported for now.

Sample API to test:

http://localhost:8080/api/workspaces

For more details go through the Wiki pages.

Copyright and License
==========

Copyright 2014 Ranjan Kumar

Licensed under Apache License, Version 2.0
