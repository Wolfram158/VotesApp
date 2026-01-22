#!/bin/bash

kubectl create secret generic auth-db-user \
  --from-literal=POSTGRES_AUTH_USER=admin

kubectl create secret generic auth-db-name \
  --from-literal=POSTGRES_AUTH_DB=database_auth

kubectl create secret generic auth-db-password \
  --from-literal=POSTGRES_AUTH_PASSWORD=1234