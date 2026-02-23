#!/bin/bash

kubectl create secret generic auth-db-user \
  --from-literal=POSTGRES_AUTH_USER=admin

kubectl create secret generic auth-db-name \
  --from-literal=POSTGRES_AUTH_DB=database_auth

kubectl create secret generic auth-db-password \
  --from-literal=POSTGRES_AUTH_PASSWORD=1234

kubectl create secret generic read-votes-db-user \
  --from-literal=POSTGRES_READ_VOTES_USER=admin

kubectl create secret generic read-votes-db-name \
  --from-literal=POSTGRES_READ_VOTES_DB=database_read_votes

kubectl create secret generic read-votes-db-password \
  --from-literal=POSTGRES_READ_VOTES_PASSWORD=1234

kubectl create secret generic write-votes-db-user \
  --from-literal=POSTGRES_WRITE_VOTES_USER=admin

kubectl create secret generic write-votes-db-name \
  --from-literal=POSTGRES_WRITE_VOTES_DB=database_write_votes

kubectl create secret generic write-votes-db-password \
  --from-literal=POSTGRES_WRITE_VOTES_PASSWORD=1234