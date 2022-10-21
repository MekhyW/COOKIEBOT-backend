# COOKIEBOT-backend
Data management and scheduler backend for the Cookiebot and Bombot Telegram bots

This project aims to manage a NoSQL (MongoDB) database with http.

Using: Java 17 + Spring Boot + Spring Web + Spring Data MongoDB

# TODO
-> Implement more database collections

-> Implement oAuth2 authentication 


# SERVER PATHS
# configs:

host:port/configs [GET]

host:port/configs/{id} [GET, POST, PUT, DELETE] 

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

# registers:

host:port/registers [GET]

host:port/registers/{id} [GET, POST, DELETE]

host:port/registers/{id}/users [GET, POST, PUT, DELETE]

Json attributes:

id : string

user : string

date : string

# rules:

host:port/rules [GET]

host:port/rules/{id} [GET, POST, PUT, DELETE]

Json attributes:

id : string

rules : string

# welcomes:

host:port/welcomes [GET]

host:port/welcomes/{id} [GET, POST, PUT, DELETE]

Json attributes:

id : string

message : string
