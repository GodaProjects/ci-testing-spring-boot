pipeline {





    agent { label 'goda-kubepod' }

    stage('Deploy App') {
      steps {
        script {
          kubernetesDeploy(configs: "goda-app.yaml", kubeconfigId: "goda-kube-config")
        }
      }
    }

  }

}