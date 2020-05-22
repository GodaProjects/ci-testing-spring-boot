pipeline {
    agent any

    stages {
        stage('Build App') {
            steps {
                sh "./mvnw clean install -DskipTests"
            }
        }
    }


  agent { label 'goda-kubepod' }

  stages {

    stage('Checkout Source') {
      steps {
        git url:'https://github.com/GodaProjects/ci-testing-spring-boot.git', branch:'master'
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