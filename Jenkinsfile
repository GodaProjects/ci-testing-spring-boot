pipeline {
      def dockerHome = tool 'goda-docker-global-config'
      stages {
        stage('Initialize'){
            steps{

                sh 'export PATH=${goda-docker-global-config}/bin:${PATH}'
            }
        }
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