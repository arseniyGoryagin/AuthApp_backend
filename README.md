# AuthApp Backend

This is a backend for my kotlin andoid app https://github.com/arseniyGoryagin/AuthApp

## Installation

clone the repository


```bash
git clone https://github.com/arseniyGoryagin/AuthApp.git
```

you should make a .env file containing you mongo db connect link and the server secret for generating jwt's

example .env:

```.env
DATABASE_URL=mongodb://USER:YOURPASSWORD@IP:PORT/DBNAME?authSource=AUTHDB
JWT_SECRET=12345
```
