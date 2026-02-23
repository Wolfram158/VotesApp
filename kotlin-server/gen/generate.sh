#!/bin/bash

POSTGRES_AUTH_USER=auth_user
POSTGRES_AUTH_DB=auth_db
POSTGRES_AUTH_PASSWORD=1234

POSTGRES_READ_VOTES_USER=read_votes_user
POSTGRES_READ_VOTES_DB=read_votes_db
POSTGRES_READ_VOTES_PASSWORD=1234

POSTGRES_WRITE_VOTES_USER=write_votes_user
POSTGRES_WRITE_VOTES_DB=write_votes_db
POSTGRES_WRITE_VOTES_PASSWORD=1234

envContent="POSTGRES_AUTH_USER=$POSTGRES_AUTH_USER
POSTGRES_AUTH_DB=$POSTGRES_AUTH_DB
POSTGRES_AUTH_PASSWORD=$POSTGRES_AUTH_PASSWORD

POSTGRES_READ_VOTES_USER=$POSTGRES_READ_VOTES_USER
POSTGRES_READ_VOTES_DB=$POSTGRES_READ_VOTES_DB
POSTGRES_READ_VOTES_PASSWORD=$POSTGRES_READ_VOTES_PASSWORD

POSTGRES_WRITE_VOTES_USER=$POSTGRES_WRITE_VOTES_USER
POSTGRES_WRITE_VOTES_DB=$POSTGRES_WRITE_VOTES_DB
POSTGRES_WRITE_VOTES_PASSWORD=$POSTGRES_WRITE_VOTES_PASSWORD
"

echo "$envContent" > ../.env

jwtSecret=$(openssl rand -base64 32)

gatewaySecretYaml="jwt:
  secret: $jwtSecret"

echo "$gatewaySecretYaml" > ../gateway/src/main/resources/secret.yaml

pbkdf2Secret=$(openssl rand -base64 32)
pbkdf2SaltLength=16
pbkdf2Iterations=310000

host=smtp.yandex.ru
port=465
protocol=smtps

emailSecretYaml="pbkdf2:
  secret: $pbkdf2Secret
  saltLength: $pbkdf2SaltLength
  iterations: $pbkdf2Iterations

spring:
  mail:
    host: $host
    port: $port
    protocol: $protocol
    username: $1
    password: $2

mail:
  debug: false
  codeLength: 10
  codeExpirationSeconds: 600"

echo "$emailSecretYaml" > ../email/src/main/resources/secret.yaml

authSecretYaml="email:
  url:
    http://email-service:8084/api/v1/email

jwt:
  secret:
    $jwtSecret

pbkdf2:
  secret: $pbkdf2Secret
  saltLength: $pbkdf2SaltLength
  iterations: $pbkdf2Iterations"

echo "$authSecretYaml" > ../auth/src/main/resources/secret.yaml