node {
    sh "export PATH=/usr/local/bin:/usr/bin:/bin:/sbin:$PATH"
    docker.withRegistry('https://registry.hub.docker.com', 'goda-docker-credentials') {
        git url:'https://github.com/GodaProjects/ci-testing-spring-boot.git', branch:'master'
        withCredentials([usernamePassword(credentialsId: 'godaprojects-dockercreds', passwordVariable: 'password', usernameVariable: 'username')]) {
            sh "./mvnw  compile jib:dockerBuild -Djib.to.auth.username=$username -Djib.to.auth.password=$password"
        }
    }
}