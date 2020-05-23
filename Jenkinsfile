pipeline {
    stages {
        stage('Initialize'){
            steps{
                def dockerHome = tool 'goda-docker-global-config'
                env.PATH = "${goda-docker-global-config}/bin:${env.PATH}"
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