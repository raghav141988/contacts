#!/bin/sh
docker build -t raghav141988/contacts-backend:latest -t raghav141988/contacts-backend:$SHA -f ./Dockerfile .
docker build -t raghav141988/contacts-frontend:latest -t raghav141988/contacts-frontend:$SHA -f ./contacts-frontend/Dockerfile ./contacts-frontend

docker push raghav141988/contacts-backend:latest
docker push raghav141988/contacts-frontend:latest

docker push raghav141988/contacts-backend:$SHA
docker push raghav141988/contacts-frontend:$SHA

kubectl apply -f ./contacts-deployment/k8s
kubectl set image deployments/contact-frontend-deployment contacts-frontend=raghav141988/contacts-frontend:$SHA
kubectl set image deployments/contact-backend-deployment contacts-api=raghav141988/contacts-backend:$SHA
