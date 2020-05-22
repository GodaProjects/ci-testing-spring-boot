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
            sh "ls -ltr"
            sh "pwd"
            sh "chmod 755 *"
            sh "./mvnw clean install -DskipTests"
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