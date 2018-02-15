# Knowledge

[![Build Status](https://travis-ci.org/support-project/knowledge.svg?branch=master)](https://travis-ci.org/support-project/knowledge)


## About
- Free Knowledge Management System
- [landing page and Online manual](https://information-knowledge.support-project.org/)

## Support
- **日本語での質問は、以下のサイトに登録してください(海外からのアクセスが多くなったため)**
- **For questions in Japanese, please register below**
- https://service-knowledge.support-project.org/

## Live Demo
- [https://test-knowledge.support-project.org](https://test-knowledge.support-project.org)
- Demo users
   - [id/password] user1 / user1
   - [id/password] user2 / user2
   - [id/password] user3 / user3

## Deploy to Heroku

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/support-project/knowledge)

## How to initial set up
- Please show the [wiki page](https://github.com/support-project/knowledge/wiki)

## vulnerability testing
- vulnerability testing with VAddy
<a href="https://vaddy.net/" target="_blank">
<img alt="VAddy logo" src="https://raw.github.com/wiki/support-project/knowledge/assets/images/VAddy_logo_b.png" height="40" /></a>


-------


## How to development

- Clone this repository your local machine.
- From version 2, frontend module and backend module are divided.
   - The backend module is the same as version 1.
   - Therefore, starting only the backend module will have the same as version 1.
- In version 2 development,
- Start up in frontend, backend, and reverse proxy by Nginx.
   - Start with docker & docker-compose.
- After starting all the modules, you can access to http://localhost for development.

### How to start Frontend

#### install dependencies

```
$ docker-compose run --rm frontend npm install --no-optional
$ docker-compose run --rm frontend npm run afterinstall
```

#### start frontend with hot reload at localhost:8081

```
$  docker-compose run --rm -p 8081:8081 frontend npm run dev
```

- If the following error occurs, execute the this command
   - `Module build failed: Error: ENOENT: no such file or directory, scandir '/usr/src/node_modules/node-sass/vendor`

```
$ docker-compose run --rm frontend npm rebuild node-sass --force
```

### How to start Backend

#### start backend 

```
$ docker-compose run --rm maven mvn clean package
$ docker-compose run --rm -p 8080:8080 tomcat
```

- Otherwise, Run the following classes in IDE such as Eclipse or IntelliJ IDEA

- org.support.project.knowledge.Launch

### How to start Reverse proxy

```
$ docker-compose run --rm -p 80:80 nginx 
```


-------


## How to build release package

### Frontend

#### build for production with minification

```
$  docker-compose run --rm frontend npm run build
```

- Fronted module will generated to "frontend/dist" directory.
- Next, please copy this module's file to backend/src/main/webapp
- Then, Then you will run the build of Backend.

### Backend

#### Build war (web application archive)

```
$ docker-compose run --rm maven mvn clean package
```

- 'knowledge.war' is builded in backend/target directory when this command finished succeed



