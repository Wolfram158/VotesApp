# Docker Compose

## Before start ##

### Create .env file in the root of project ###
The following keys must be defined:
```txt
POSTGRES_AUTH_USER=
POSTGRES_AUTH_DB=
POSTGRES_AUTH_PASSWORD=

POSTGRES_READ_VOTES_USER=
POSTGRES_READ_VOTES_DB=
POSTGRES_READ_VOTES_PASSWORD=

POSTGRES_WRITE_VOTES_USER=
POSTGRES_WRITE_VOTES_DB=
POSTGRES_WRITE_VOTES_PASSWORD=
```

### Create secret.yaml file in the resource directory of gateway subproject ###
Define jwt secret:
```yaml
jwt:
  secret: YOUR_JWT_SECRET
```
Jwt secret can be generated using `openssl` (if installed):
```bash
openssl rand -base64 32
```

### Create secret.yaml file in the resource directory of email subproject ###
The following content must be defined in secret.yaml file:
```yaml 
pbkdf2:
  secret: YOUR_PBKDF2_SECRET
  saltLength: YOUR_SALT_LENGTH(recommended:16)
  iterations: YOUR_ITERATIONS(recommended:310000)

spring:
  mail:
    host: YOUR_MAIL_HOST
    port: YOUR_MAIL_PORT
    protocol: YOUR_MAIL_PROTOCOL
    username: YOUR_MAIL_USERNAME
    password: YOUR_MAIL_PASSWORD

mail:
  debug: false
  codeLength: 10
  codeExpirationSeconds: 600
```

### Create secret.yaml file in the resource directory of auth subproject ###
The following content must be defined in secret.yaml file:
```yaml
spring:
  datasource:
    password:
      ${POSTGRES_AUTH_PASSWORD}

email:
  url:
    http://email-service:8084/api/v1/email

jwt:
  secret:
    JWT_SECRET_FROM_GATEWAY_SUBPROJECT

pbkdf2:
  secret: PBKDF2_SECRET_FROM_EMAIL_SUBPROJECT
  saltLength: SALT_LENGTH_FROM_EMAIL_SUBPROJECT
  iterations: ITERATIONS_FROM_EMAIL_SUBPROJECT
```

## How to start ##
In the project root run command:
```bash
docker-compose up --build -d
```

## How to continue ##
Right after `docker compose up --build -d` executed successfully,
command `docker ps` can be executed to see started containers.
To get into container, run:
```bash
docker exec -it <container-name> sh
```
Being inside container, to get into database (if exists), run:
```bash
psql -U $POSTGRES_USER -h localhost -d $POSTGRES_DB
```

## How to end ##
To end, run:
```bash
docker-compose down
```

# k3d

Server can be launched using `start.sh` script located in k3d directory.