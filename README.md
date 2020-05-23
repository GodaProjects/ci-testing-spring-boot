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
1. Install jenkins from my own image which is inherited from the official jenkins lts image, which includes docker installed inside. And here is the command to create the jenkins container. Note the -v /var/run/docker.sock:/var/run/docker.sock in the command. See the https://stackoverflow.com/questions/36765138/bind-to-docker-socket-on-windows for further information. Note that you dont have to use the npipe:////./pipe/docker_engine since Docker anyway directly does not work on windows but a linux image on windows using Docker desktop for windows.
```docker run -d --name goda-jenkins -p 4030:8080 -p 50030:50000 -v /var/run/docker.sock:/var/run/docker.sock -v goda_jenkins_data_2_docker_container:/var/jenkins_home -u root --privileged=true godaprojects/goda-jenkins:0.2```
2. Now run the following command to see the log and take the password from there.
```docker logs goda-jenkins```
3. Then the plugins get installed.
4. Set the credentials - goda/Wipro@123
5. Technically things should work now even without setting up any plugins. Lets check it with the Jenkinsfile we have now.


1. Install Docker plugin (I think thats already installed)
2. Run this (https://stackoverflow.com/questions/47709208/how-to-find-docker-host-uri-to-be-used-in-jenkins-docker-plugin and before that go to Docker settings and enable "expose daemon on 2375" thingy )
```docker run -d --name docker-socat --restart=always -p 127.0.0.1:23750:2375 -v /var/run/docker.sock:/var/run/docker.sock  alpine/socat  tcp-listen:2375,fork,reuseaddr unix-connect:/var/run/docker.sock```
3. Set up a new cloud config on Jenkins and the this should be the URI of the docker (IP of goda-socat)
```tcp://172.17.0.3:2375```
4. 


https://github.com/kubernetes/kubernetes/issues/33664
https://stackoverflow.com/questions/32046334/where-can-i-find-the-sha256-code-of-a-docker-image
