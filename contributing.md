# Contributing guide

<!-- TOC -->
* [Contributing guide](#contributing-guide)
  * [Running project locally](#running-project-locally)
    * [Testing the http endpoints](#testing-the-http-endpoints)
      * [Accessing the swagger](#accessing-the-swagger)
    * [Using the OAUTH 2 mock](#using-the-oauth-2-mock)
      * [Generating a JWT Token](#generating-a-jwt-token)
<!-- TOC -->

## Running project locally

Requirements:
- MongoDB
- JDK 21+
- Maven

You can use the provided docker compose to run MongoDB.

```shell
docker compose up -d
```

You should configure Spring to use the profile local:
```text
Using the property:
-Dspring.profiles.active=local

Or environment variable:
SPRING_PROFILES_ACTIVE=local
```

### Testing the http endpoints

The endpoints that start with `bff` are using JWT as authentication. You can use the OAUTH 2 mock server to test them.
The token should be passed in the Authorization header as follows:
```text
Authorization: Bearer <token>
```

The other endpoints are using http basic authentication.
By default a user with name `user` and password `1234` is configured on the local profile to be used in the local tests.

#### Accessing the swagger

The swagger is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Using the OAUTH 2 mock

To generate a fake JWT token to test endpoints that are used by the front end you can use the [oauth2_mock_server.py](./oauth2_mock/oauth2_mock_server.py) python script.
The script creates an HTTP server with mock endpoints for JWK Set and JWT Token generation.

```shell
cd ./oauth2_mock
pip install -r requirements.txt

python ./oauth2_mock_server.py
```

#### Generating a JWT Token

```shell
curl -i http://localhost:8000/generate-token -H 'Content-Type: application/json' -d '{
  "username": "user-test",
  "roles": []
}'
```