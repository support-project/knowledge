[webapi.yaml](webapi.yaml) is the web API yaml file for [swagger](https://swagger.io/).

## Quickstart

By the following steps, you can test APIs by the swagger editor.
In this manual, we run knowledge and swagger editor under `http://localhost` to avoid CORS.

- deploy knowledge by `launch.sh`.
  - assume you can access knowledge by http://172.20.0.1:8080/knowledge
- deploy swagger
  - `docker run --rm -d swaggerapi/swagger-editor`
  - assume you can access the swagger editor by http://172.17.0.2:8080
- run nginx reverse proxy
  - edit `default.conf` and modify the IP addresses of both containers.
  - run nginx reverse proxy

```bash
docker run --rm -it -p 80:80 -v `pwd`/default.conf:/etc/nginx/conf.d/default.conf
```

If everything is OK, you can access knowledge by `http://localhost/knowledge` and swagger editor by `http://localhost`.

Finally, create and set access token.

- In knowledge, create an access token.
- Open the swagger editor, and paste webapi.yaml.
- Set the access token from the `Authorize` button in the right tab of the swagger editor.
