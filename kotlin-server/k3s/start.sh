#!/bin/bash

k3d cluster create votes \
  --k3s-arg '--flannel-backend=none@server:*' \
  --k3s-arg '--disable-network-policy@server:*' \
  --k3s-arg '--cluster-cidr=192.168.0.0/16@server:*' \
  -p '8080:30080@loadbalancer'

kubectl create -f https://raw.githubusercontent.com/projectcalico/calico/v3.29.0/manifests/tigera-operator.yaml

kubectl create -f https://raw.githubusercontent.com/projectcalico/calico/v3.29.0/manifests/custom-resources.yaml

sleep 70s

kubectl get tigerastatus

kubectl patch installation default --type=merge --patch='{"spec":{"calicoNetwork":{"containerIPForwarding":"Enabled"}}}'

k3d cluster start votes

k3d image import kotlin-server-write-votes-service -c votes
k3d image import kotlin-server-read-votes-service -c votes
k3d image import kotlin-server-auth-service -c votes
k3d image import kotlin-server-administration-service -c votes
k3d image import kotlin-server-email-service -c votes
k3d image import kotlin-server-gateway-service -c votes

./create-secrets.sh

kubectl apply -f .