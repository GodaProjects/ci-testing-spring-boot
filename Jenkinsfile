pipeline {
    agent {
        docker { image 'node:7-alpine' }
    }
    stage('Initialize'){
        def dockerHome = tool 'goda-docker-global-config'
        sh 'export PATH=${goda-docker-global-config}/bin:${PATH}'
    }
    sh 'node --version'
}