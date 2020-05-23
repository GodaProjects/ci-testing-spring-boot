pipeline {
  agent { label 'goda-docker-label' }
  stages {
    stage('Checkout Source') {
      steps {
        git url:'https://github.com/GodaProjects/ci-testing-spring-boot.git', branch:'master'
      }
    }

    stage('Build App') {
        steps {
            // retaining it see some debug info
            sh "ls -ltr"
            sh "pwd"
            // This is the one which allowed me to run the ./mvnw
            sh "chmod 755 *"
            //Build the project
            sh "./mvnw clean install -DskipTests"
        }
    }

    stage('Test App') {
        steps {
            sh "./mvnw test"
        }
    }

    stage('Build Image for App') {
        steps {
            script {
                docker.withRegistry('https://registry.hub.docker.com', 'goda-docker-credentials') {
                    withCredentials([usernamePassword(credentialsId: 'godaprojects-dockercreds', passwordVariable: 'password', usernameVariable: 'username')]) {
                        sh "./mvnw  compile jib:dockerBuild -Djib.to.auth.username=$username -Djib.to.auth.password=$password"
                    }
                }

            }
        }
    }

    // Not needed anymore since Jib takes care of both creating and uploading the image.
    /*stage('Upload the image to docker hub') {
        steps {
            withCredentials([usernamePassword(credentialsId: 'godaprojects-dockercreds', passwordVariable: 'password', usernameVariable: 'user')]) {
                // the code in here can access $password and $user
                sh "docker login -u $user -p $password"
            }
            sh "./mvnw exec:exec"
        }
    }*/

    stage('Kubernates Deploy App') {
      agent { label 'goda-kubepod' }
      steps {
        git url:'https://github.com/GodaProjects/ci-testing-spring-boot.git', branch:'master'
        script {
          kubernetesDeploy(configs: "goda-app.yaml", kubeconfigId: "goda-kube-config")
        }
      }
    }

  }

}