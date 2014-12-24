[RESTFiddle](http://www.restfiddle.com/)
==========

[![Build Status](https://travis-ci.org/ranjan-rk/restfiddle.svg?branch=master)](https://travis-ci.org/ranjan-rk/restfiddle)

An Enterprise-grade API Management Platform for Teams. RESTFiddle helps you design, develop, test and release APIs.

Why RESTFiddle?
==========

We all usually work as a team- be it software development or testing. What consumes most of your time while working in a group? Collaboration. You need to share stuff with your colleague. It is a painful exercise with a lot of limitations and doesn't work seamlessly.

##### How does RESTFiddle help you? 

If you are developing or testing REST APIs, it will help you do that with ease. It will let a team or multiple teams work together effortlessly. You just have to focus on your APIs. Rest all is taken care by the tool.

Time is valuable, so productivity is important. We have crafted RESTFiddle to avoid you fiddling with sharing or collaboration work and increase your productivity!

##### How to Collaborate? 

Adding collaborators to work on a shared workspace is just a click away.

##### Cloud support?

As the output of the build is a war file so it can be deployed over any server and can also be used as a cloud based hosted web application.

Features
==========

RESTFiddle is not just a tool for API testing, it is an API Management "Platform". Some of the key features of this platform are:

1. Collaboration - Add as many collaborators as you want from your team and work together on a project.
2. Install it in your environment and own it completely.
3. Store everything in your database - Your data is always with you. MySQL, PostgreSQL, Oracle, MS SQL Server etc. supported.
4. Security - Runs over http/https.
5. Work offline - You can work together with your team in your private network.
6. Integration - RESTFiddle platform exposes APIs for everything it has. 
7. Unlimited history/activity log.
8. Scheduler - Run API projects on predefined time intervals.
9. Open Source - Fork it and build the features of your choice.
10. Access Control, Generate Reports, Notifications and much more.

A lot of powerful features coming soon!

Initial Design
==========

![alt text](https://raw.githubusercontent.com/ranjan-rk/restfiddle/gh-pages/images/rf_screenshot2.jpg "Initial Design")

Who Uses RESTFiddle
==========

##### The following is a list of companies and projects using RESTFiddle:

* BootSimply Solutions (http://bootsimply.com/)

Want to be added to this section? Email me at ranjan dot rk at gmail dot com.

Building From Source
==========

##### Prerequisites

* JDK 7 or later

* Maven 3.0+

* MySQL (optional)

Note : By default the application is configured to use Hsqldb. It is an in-memory database and maven downloads it during build time. Also, it automatically starts with the application. 

##### Build

```
mvn clean install
```

##### Run

```
mvn spring-boot:run
```

##### Access

The build file is configured to download and use an embedded Tomcat server. So the application should be up and running by using just two commands mentioned above. Once the server is started, the application can be accessed using http://localhost:8080. 

```
Default login email / password : rf@example.com / rf
```

##### Debug

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"
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

Deployment
==========

You can deploy the war file to Tomcat 7.0.52, Jetty, or any other container, as long as it supports servlet 3.0.


Technology Stack
==========

* Spring (http://spring.io/)

* Hibernate (http://hibernate.org/)

* HttpComponents (http://hc.apache.org/)

* Logback (http://logback.qos.ch/)

* MySQL (http://www.mysql.com/)

* Tomcat (http://tomcat.apache.org/)

* jQuery (http://jquery.com/)

* Bootstrap (http://getbootstrap.com/)

* Backbone (http://backbonejs.org/)

* Underscore (http://underscorejs.org/)

* RequireJS (http://requirejs.org/)

* https://github.com/mar10/fancytree/

* https://code.google.com/p/google-code-prettify/

* https://github.com/isagalaev/highlight.js/

* https://github.com/brianreavis/selectize.js/

* https://github.com/vitalets/x-editable/

* http://fortawesome.github.io/Font-Awesome/

* http://glyphicons.com/

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

##### Google Group : 

https://groups.google.com/forum/#!forum/restfiddle

##### Meetup Group : 

http://www.meetup.com/RESTFiddle

Releases
==========

https://github.com/ranjan-rk/restfiddle/releases

Copyright and License
==========

Copyright 2014 Ranjan Kumar

Licensed under Apache License, Version 2.0
