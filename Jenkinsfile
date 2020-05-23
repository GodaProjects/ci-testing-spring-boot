pipeline {
      stage('Initialize'){
         def dockerHome = tool 'goda-docker-global-config'
         sh 'export PATH=${goda-docker-global-config}/bin:${PATH}'
      }
      stages {
        stage('Test') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                sh 'node --version'
            }
        }
    }

}