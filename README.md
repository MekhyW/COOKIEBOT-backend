# COOKIEBOT-backend
Data management and scheduler backend for the Cookiebot and Bombot Telegram bots

This project aims to manage a NoSQL (MongoDB) database with https.

Using: Java 19 + Spring Boot + Spring Web + Spring Data MongoDB + Spring Security

# CONFIGURATION GUIDE

It's necessary to create a file called "application.properties" inside src/main/resources folder.

### Insert the URI string to connect your MongoDB database:

	spring.data.mongodb.uri=YourUriHere


### Set your user and password, always with the role "ADMIN":

	spring.security.user.name=username
	spring.security.user.password=password
	spring.security.user.roles=ADMIN


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

# SERVER PATHS
### configs:

	https://host/configs [GET]
	
	https://host/configs/{id} [GET, POST, PUT, DELETE] 

Json attributes:

	id : string 
	
	furbots : boolean
	
	stickerSpamLimit : integer
	
	timeWithoutSendingImages : integer
	
	timeCaptcha : integer
	
	functionsFun : boolean
	
	functionsUtility : boolean
	
	sfw : boolean
	
	language : string

	publisherPost : boolean
	
	publisherAsk : boolean

### registers:

	https://host/registers [GET]
	
	https://host/registers/{id} [GET, POST, DELETE]
	
	https://host/registers/{id}/users [GET, POST, PUT, DELETE]

Json attributes:

	id : string
	
	user : string
	
	date : string


### rules:

	https://host/rules [GET]
	
	https://host/rules/{id} [GET, POST, PUT, DELETE]

Json attributes:

	id : string
	
	rules : string


### welcomes:

	https://host/welcomes [GET]
	
	https://host/welcomes/{id} [GET, POST, PUT, DELETE]

Json attributes:

	id : string
	
	message : string


### blacklist:

	https://host/blacklist [GET]
	
	https://host/blacklist/{id} [GET, POST, DELETE]

Json attributes:

	id : string


### stickers:

	https://host/stickers [GET]
	
	https://host/stickers/{id} [GET, POST, PUT, DELETE]

Json attributes:

	id : string
	
	lastUsed : string


### randomdatabase:

	https://host/randomdatabase [GET/POST]

Json attributes:

	id : string
	
	idMessage : string
	
	idMedia : string


### stickerdatabase:

	https://host/stickerdatabase [GET/POST]

Json attributes:

	id : string
