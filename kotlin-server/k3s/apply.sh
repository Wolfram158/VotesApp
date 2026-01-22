#!/bin/bash

kubectl apply -f $(ls -1 *.yaml | paste -sd, -)