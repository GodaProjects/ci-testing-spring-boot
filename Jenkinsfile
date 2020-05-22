pipeline {

  agent { label 'goda-kubepod' }

  stages {

    stage('Checkout Source') {
      steps {
        git url:'https://github.com/GodaProjects/ci-testing-spring-boot.git', branch:'master'
      }
    }

    stage('Build App') {
        steps {
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
            sh "./mvnw spring-boot:build-image"
        }
    }

    stage('Push Image to Docker Hub') {
        steps {
            sh "./mvnw spring-boot:build-image"
        }
    }

    stage('Deploy App') {
      steps {
        script {
          kubernetesDeploy(configs: "goda-app.yaml", kubeconfigId: "goda-kube-config")
        }
      }
    }

  }

}