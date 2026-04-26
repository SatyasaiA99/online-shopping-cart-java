pipeline {
    agent any
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
    }
}
