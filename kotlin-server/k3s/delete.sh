#!/bin/bash

kubectl delete -f $(ls -1 *.yaml | paste -sd, -)