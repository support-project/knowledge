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


## Development

### Frontend

#### install dependencies

```
$  docker-compose run --rm frontend npm install
```

#### serve with hot reload at localhost:8081

```
$  docker-compose run --rm -p 8081:8081 frontend npm run dev
```

- If the following error occurs, execute the this command
   - `Module build failed: Error: ENOENT: no such file or directory, scandir '/usr/src/node_modules/node-sass/vendor`

```
$ docker-compose run --rm frontend npm rebuild node-sass --force
```

# build for production with minification

```
$  docker-compose run --rm frontend npm run build
```


### Backend

#### Build war (web application archive)

- Clone this repository your local machine and run this command
- 'knowledge.war' is builded in backend/target directory when this command finished succeed

```
$ docker-compose run --rm maven mvn clean package
```

#### Run builded web application on your machine

- You can access builded web application on 'http://localhost:8080/knowledge'

```
$ docker-compose run --rm -p 8080:8080 tomcat 
```



