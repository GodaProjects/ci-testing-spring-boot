# travis-ci-testing-spring-boot

### Travis CI Status
[![Build Status](https://travis-ci.com/GodaProjects/ci-testing-spring-boot.svg?token=nxbpBzyPmzXPxUzh2pUd&branch=master)](https://travis-ci.com/GodaProjects/ci-testing-spring-boot)


### Travis CI Steps
CI project for testing spring boot
 - Done with setting up a build on travis
 - Setup deploy to heroku
 - Setup environment variable for the spring boot application which gets printed on the service
 - Setup docker image creation using spring boot buildkit - mvnw spring-boot:build-image
 - Setup environment variables in travis for docker hub credentials
 - Uploaded images to docker hub using exec:exec maven objective:goal.
 
This concludes playing around with travis. FOr information on how to test using minikube see https://blog.travis-ci.com/2017-10-26-running-kubernetes-on-travis-ci-with-minikube and for integrating with existing k8s cluster see https://www.caveofcode.com/continuous-delivery-to-kubernetes-with-travis-ci/

### Jenkins - Kubernates
See this video for more details - https://www.youtube.com/watch?v=V4kYbHlQYHg
1. Install Jenkins as a container on local - Named volume will be managed by Docker. 
```docker run -d --name goda-jenkins -p 4030:8080 -p 50030:50000 -v goda_jenkins_data:/var/jenkins_home -u root jenkins/jenkins:lts```
1.1 Get the initial password from logs
```docker logs -f goda-jenkins```
1.2 Point to be noted here. Jenkins plugin installation will never happen in one shot properly. (Ok.. almost nothing seems to work right the first attempt. Everything only works in the second or the third attempt)
1.3 goda-k8s-cluster is the name of the cluster config 
1.4 goda/Goda@123/Goda/http://192.168.0.33:4030/ (Will change whenever network is reconnected)
2. Kube connection details goda-k8s/kube config from user/goda folder
3. 192.168.0.33:50030 is the jenkins tunnel
4. pod template name - goda-kube, labels - goda-kubepod
4.1 When you delete and run another instance, open and save it otherwise it will not recognize the label goda-kubepod
5. container template - goda-jnlp-slave, jenkinsci/jnlp-slave:latest (seems to be deprecated), /home/jenkins
6. Setup credentials - goda-kube-config, paste things from your kube config

### Jenkins - Docker
1. Install Docker plugin (I think thats already installed)
2. Run this (https://stackoverflow.com/questions/47709208/how-to-find-docker-host-uri-to-be-used-in-jenkins-docker-plugin and before that go to Docker settings and enable "expose daemon on 2375" thingy )
```docker run -d --name docker-socat --restart=always -p 127.0.0.1:23750:2375 -v /var/run/docker.sock:/var/run/docker.sock  alpine/socat  tcp-listen:2375,fork,reuseaddr unix-connect:/var/run/docker.sock```
3. Set up a new cloud config on Jenkins and the this should be the URI of the docker (IP of goda-socat)
```tcp://172.17.0.3:2375```
4. 