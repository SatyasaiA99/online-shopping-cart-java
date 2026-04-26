pipeline {
    agent any
    environment {
        DOCKERHUB_USER = 'satyasaia99'
        DOCKERHUB_IMAGE = 'onlineshopping'
        DOCKERHUB_TAG = 'v1'
        SONARQUBE_ENV = 'sq'
        NEXUS_REPO = 'maven-releases'
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
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('JENKINS TO NEXUS') {
            steps {
              withMaven(globalMavenSettingsConfig: 'settings.xml', jdk: 'jdk21', maven: 'maven3', traceability: true) {
             sh 'mvn deploy'
             }
            }
        }   
        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'Docker',
                                                 usernameVariable: 'DOCKER_USER',
                                                 passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    docker push ${DOCKERHUB_USER}/${DOCKERHUB_IMAGE}:${DOCKERHUB_TAG}
                    '''
                }
            }
        }
    }
}
