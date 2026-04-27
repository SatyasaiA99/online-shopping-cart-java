pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'satyasaia99'
        DOCKERHUB_IMAGE = 'onlineshopping'
        DOCKERHUB_TAG = 'v1'
        SONARQUBE_ENV = 'sq'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/SatyasaiA99/online-shopping-cart-java.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withMaven(globalMavenSettingsConfig: 'settings.xml',
                          jdk: 'jdk21',
                          maven: 'maven3') {
                    sh 'mvn deploy'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                docker build -t ${DOCKERHUB_USER}/${DOCKERHUB_IMAGE}:${DOCKERHUB_TAG} .
                """
            }
        }

        stage('Push to DockerHub') {
    steps {
        withCredentials([usernamePassword(credentialsId: 'Dockerhub',
                                         usernameVariable: 'DOCKER_USER',
                                         passwordVariable: 'DOCKER_PASS')]) {

            sh """
                echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                docker push ${DOCKERHUB_USER}/${DOCKERHUB_IMAGE}:${DOCKERHUB_TAG}
            """
        }
    }
}
 stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                sh '''
                echo "🚀 Deploying to Kubernetes..."

                kubectl apply -f Deployment.yml
                kubectl apply -f Service.yml

                kubectl rollout status deployment/onlineshopping-deployment
                '''
            }
        }
    }       
    }
}
