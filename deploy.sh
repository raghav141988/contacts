#!/bin/sh


cd $TRAVIS_BUILD_DIR/contacts-backend
docker build -t raghav141988/contacts-backend:latest -t raghav141988/contacts-backend:$BUILD_ID -f ./Dockerfile .
cd $TRAVIS_BUILD_DIR
ls -l
echo $PWD
docker build -t raghav141988/contacts-frontend:latest -t raghav141988/contacts-frontend:$BUILD_ID -f ./contacts-frontend/Dockerfile ./contacts-frontend
docker push raghav141988/contacts-backend:latest
docker push raghav141988/contacts-frontend:latest

docker push raghav141988/contacts-backend:$BUILD_ID
docker push raghav141988/contacts-frontend:$BUILD_ID

kubectl apply -f ./contacts-deployment/k8s
kubectl set image deployments/contact-frontend-deployment contacts-frontend=raghav141988/contacts-frontend:$BUILD_ID
kubectl set image deployments/contact-backend-deployment contacts-api=raghav141988/contacts-backend:$BUILD_ID
