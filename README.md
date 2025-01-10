# COOKIEBOT-backend

[![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)](https://redocly.github.io/redoc/?url=https://raw.githubusercontent.com/MekhyW/COOKIEBOT-backend/refs/heads/main/docs/openapi.json)

Data management and scheduler backend for the Cookiebot and Bombot Telegram bots

This project aims to manage a NoSQL (MongoDB) database with https.

Using: Java 17 + Spring Boot + Spring Web + Spring Data MongoDB + Spring Security

## RELATED SERVER-SIDE PROJECTS

[Systemd Service](https://gitlab.com/myghiproj/ahss/-/blob/main/SystemdServices/javaserver%40.service)

[Update Script](https://gitlab.com/myghiproj/ahss/-/blob/main/BashScripts/cookiebot-update.sh)

## CONFIGURATION GUIDE

It's necessary to create a file called "application.properties" inside src/main/resources folder.

### Insert the URI string to connect your MongoDB database:

	spring.data.mongodb.uri=YourUriHere

### Set your user and password, always with the role "ADMIN":

	spring.security.user.name=username
	spring.security.user.password=password
	spring.security.user.roles=ADMIN

### Configure the JWT token validation

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: http://<host>/.well-known/jwks.json
          issuer-uri: http://<host>
```

### Configure https to encrypt your connection:

By default, unprivileged users on Linux are blocked from hosting on ports under 1024.

In order to allow any port to any user, insert this line into the file /etc/sysctl.d/99-sysctl.conf with a text editor:

	net.ipv4.ip_unprivileged_port_start=0

This new configuration will be effective after a reboot. You can also apply it immediately:
	
	sudo sysctl -p /etc/sysctl.d/99-sysctl.conf

After that, use keytool to generate a certificate (for a self-signed https):

	keytool -genkey -alias bootsecurity -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore bootsecurity.p12 -validity 36500
	
Copy the bootsecurity.p12 file to the root folder of the project and finally add these settings to your application.properties:

	server.port=443
	server.ssl.key-store=bootsecurity.p12
	server.ssl.key-store-password=YourCertificatePasswordHere
	server.ssl.key-store-type=PKCS12
	server.ssl.key-alias=bootsecurity

And you're done!

After compiling to a jar file, the software will look for a bootsecurity.p12 file on the same directory as the .jar file itself.


## Swagger

The swagger is available at `/swagger-ui/index.html`

### Generating the Swagger file

```shell
mvn springdoc-openapi:generate
```

The OpenAPI file will be generated at [`docs/openapi.json`](./docs/openapi.json)

You can open the file on a swagger editor such as https://editor.swagger.io/ or use an IDE such as **VSCode** with the swagger extension installed.

## Contributing

We love contributions. You can follow the [contributing documentation](./contributing.md).

