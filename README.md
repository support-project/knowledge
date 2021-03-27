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


## Developer manual

### Build with Eclipse & Tomcat

You can read how to develop with Eclipse & Tomcat by [this article](http://koda3.hatenablog.com/entry/2015/04/08/061130).
Here is a short summary:

- Import to Eclipse
  - Download Eclipse
  - Clone this repository
  - Run Eclipse, specify JRE 1.8
  - Import Knowledge directory by "File > Import > Maven > Existing Maven Projects"
- Run in Eclipse
  - Download tomcat v8
  - Set up tomcat v8 according to Eclipse Servers view
  - Add tomcat to Eclipse
  - Right click and start
- Access to http://localhost:8080/knowledge

### Build manually

You can create war file by `launch.sh` with some modifications.
Also you need docker and docker-compose.
See https://github.com/support-project/knowledge/pull/1100 about `launch.sh` modifications.


### ER diagram

You can see ER diagram at document/database (in Japanese).

## vulnerability testing
- vulnerability testing with VAddy
<a href="https://vaddy.net/" target="_blank">
<img alt="VAddy logo" src="https://raw.github.com/wiki/support-project/knowledge/assets/images/VAddy_logo_b.png" height="40" /></a>
