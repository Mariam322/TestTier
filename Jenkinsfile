pipeline {
  agent {
    kubernetes {
      yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: maven:3.9.9-eclipse-temurin-17
    command: ['cat']
    tty: true

  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    command: ['sleep']
    args: ['9999999']
    volumeMounts:
      - name: docker-config
        mountPath: /kaniko/.docker

  - name: kubectl
    image: lachlanevenson/k8s-kubectl:v1.25.4
    command: ['cat']
    tty: true

  volumes:
  - name: docker-config
    projected:
      sources:
      - secret:
          name: regcred
          items:
          - key: .dockerconfigjson
            path: config.json
"""
    }
  }

  environment {
    DOCKER_REGISTRY = "docker.io/mariammseddi12"
    IMAGE_NAME = "tiers-service"
    K8S_NAMESPACE = "default"
  }

  stages {

    stage('Checkout Code') {
      steps {
        deleteDir()
        git url: 'https://github.com/Mariam322/TiersService.git', branch: 'main'
      }
    }

    stage('Build Java JAR') {
      steps {
        container('maven') {
          sh "mvn -B clean package -DskipTests"
        }
      }
    }

    stage('Build & Push Docker Image') {
      steps {
        container('kaniko') {
          sh """
            /kaniko/executor \
              --context=dir://${WORKSPACE} \
              --dockerfile=${WORKSPACE}/Dockerfile \
              --destination=${DOCKER_REGISTRY}/${IMAGE_NAME}:latest \
              --skip-tls-verify \
              --snapshot-mode=redo \
              --cache=true
          """
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        container('kubectl') {
          withKubeConfig([credentialsId: 'kubernetes-vps-config']) {
            sh """
              kubectl apply -f kubernetes/tiers-deployment.yaml -n ${K8S_NAMESPACE}
              kubectl apply -f kubernetes/tiers-service.yaml -n ${K8S_NAMESPACE}

              echo "⏳ Waiting a little..."
              sleep 20
              kubectl get pods -n ${K8S_NAMESPACE} -o wide
            """
          }
        }
      }
    }
  }

  post {
    success { echo "🎉 Tiers microservice pipeline completed successfully!" }
    failure { echo "❌ Pipeline failed — check Jenkins logs." }
  }
}

