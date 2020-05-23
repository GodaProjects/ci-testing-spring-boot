pipeline {
    stage('Initialize'){
        def dockerHome = tool 'goda-docker-global-config'
        env.PATH = "${goda-docker-global-config}/bin:${env.PATH}"
    }
    agent {
        docker { image 'node:7-alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh 'node --version'
            }
        }
    }
}