# COOKIEBOT-backend
Data management and scheduler backend for the Cookiebot and Bombot Telegram bots

This project aims to manage a NoSQL (MongoDB) database with http.

Using: Java 17 + Spring Boot + Spring Web + Spring Data MongoDB

# TODO

-> Implement oAuth2 authentication 

# CONFIRMED SERVER PATHS
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

# blacklist:

host:port/blacklist [GET]

host:port/blacklist/{id} [GET, POST, DELETE]

Json attributes:

id : string

# stickers:

host:port/stickers [GET]

host:port/stickers/{id} [GET, POST, PUT, DELETE]

Json attributes:

id : string

lastUsed : string

# randomdatabase:

host:port/randomdatabase [GET/POST]

Json attributes:

id : string

idMessage : string

idMedia : string