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

### Jenkins - Docker and K8s (All on local)
1. Install jenkins from my own image which is inherited from the official jenkins lts image, which includes docker installed inside. And here is the command to create the jenkins container. Note the -v /var/run/docker.sock:/var/run/docker.sock in the command. See the https://stackoverflow.com/questions/36765138/bind-to-docker-socket-on-windows for further information. Note that you dont have to use the npipe:////./pipe/docker_engine since Docker anyway directly does not work on windows but a linux image on windows using Docker desktop for windows.
```docker run -d --name goda-jenkins -p 4030:8080 -p 50030:50000 -v /var/run/docker.sock:/var/run/docker.sock -v goda_jenkins_data_2_docker_container:/var/jenkins_home -u root --privileged=true godaprojects/goda-jenkins:0.2```
2. Now run the following command to see the log and take the password from there.
```docker logs goda-jenkins```
3. Then the plugins get installed.
4. Set the credentials - goda/Wipro@123
5. Technically things should work now even without setting up any plugins. Lets check it with the Jenkinsfile we have now.
6. Setting up a pipeline from Git.
7. Create a credentials called godaprojects-dockercreds with docker hub creds.
8. That takes care of Docker. Now over to k8s
9. Install kubernetes and kubernetes continuous deploy plugins
10. Create credentials for kubernates from the kubernetes config file which is in your home directory under the .kube directory
11. Create a cloud configuration.
11.1 Add the credentials there and test the connection.
11.2 Fill the jenkins url and jenkins tunnel setup. http://192.168.0.33:4030 and 192.168.0.33:50030
11.3 add pod template
11.3.1 Name is goda-kube
11.3.2 Label is goda-kube-label
11.3.3 Add container - name is name is goda-jenkins-inboud-agent and the image is jenkins/inbound-agent. We are DONE.
12. Create a k8s configuration so that kubernetes continuous deploy plugin can use it in the Jenkinsfile. Name is goda-k8s-config and the content is directly pasted from the file.


#### This is not required anymore
1. Install Docker plugin (I think thats already installed)
2. Run this (https://stackoverflow.com/questions/47709208/how-to-find-docker-host-uri-to-be-used-in-jenkins-docker-plugin and before that go to Docker settings and enable "expose daemon on 2375" thingy )
```docker run -d --name docker-socat --restart=always -p 127.0.0.1:23750:2375 -v /var/run/docker.sock:/var/run/docker.sock  alpine/socat  tcp-listen:2375,fork,reuseaddr unix-connect:/var/run/docker.sock```
3. Set up a new cloud config on Jenkins and the this should be the URI of the docker (IP of goda-socat)
```tcp://172.17.0.3:2375```

#### Some links
https://github.com/kubernetes/kubernetes/issues/33664
https://stackoverflow.com/questions/32046334/where-can-i-find-the-sha256-code-of-a-docker-image
