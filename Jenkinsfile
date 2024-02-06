pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    // Your Maven build command
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Your test execution command
                    // You may run unit tests here
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Your deployment command
                    // You may copy the JAR to a server or deploy to a container
                }
            }
        }
    }

    post {
        always {
            // Clean up or post-build actions
        }
    }
}
