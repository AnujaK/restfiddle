RESTFiddle
==========

[![Build Status](https://travis-ci.org/ranjan-rk/restfiddle.svg?branch=master)](https://travis-ci.org/ranjan-rk/restfiddle)

An Enterprise-grade API Management Tool for Teams. RESTFiddle helps you design, develop, test and release APIs.

This project is under active development. Version 1.0 Coming Soon!

Why RESTFiddle?
==========

We all usually work as a team- be it software development or testing. What consumes most of your time while working in a group? Collaboration. You need to share stuff with your colleague. It is a painful exercise with a lot of limitations and doesn't work seamlessly.

How does RESTFiddle help you? If you are developing or testing REST APIs, it will help you do that with ease. It will let a team or multiple teams work together effortlessly. You just have to focus on your APIs. Rest all is taken care by the tool.

Time is valueable, so productivity is important. We have crafted RESTFiddle to avoid you fiddling with sharing or collaboration work and increase your productivity!

Building From Source
==========

* JDK 7 or later

* Maven 3.0+

* MySQL

* mvn clean install

* mvn spring-boot:run


Note : To avoid java.lang.OutOfMemoryError: PermGen space, use the following command:

```
MAVEN_OPTS="-XX:PermSize=256m -XX:MaxPermSize=512m" mvn spring-boot:run 
```

##### MySQL configuration:

Go to *src/main/resources/application.properties* and update database url, username and password. Here is how the sample configuration looks like:

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


Initial Design
==========

![alt text](https://raw.githubusercontent.com/ranjan-rk/restfiddle/gh-pages/images/rf_screenshot1.png "Initial Design")

Release Date
==========

Version 1.0 is expected to be released on 15th June 2014.

Copyright and License
==========

Copyright 2014 Ranjan Kumar

Licensed under Apache License, Version 2.0