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
                    echo 'Building the Java application...'
                    // Your Maven build command
                    sh 'mvn clean install'
                }
            }
        }

        stage('Unit Test') {
            steps {
                script {
                    echo 'Running unit tests...'
                    // Your Maven unit test command
                    // The pipeline will fail if any unit tests fail
                    sh 'mvn test'
                }
            }
        }

        stage('Integration Test (Optional)') {
            when {
                expression { params.RUN_INTEGRATION_TESTS == 'true' }
            }
            steps {
                script {
                    echo 'Running integration tests...'
                    // Your Maven integration test command
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo 'Deploying the Java application...'
                    // Your deployment command
                    // You may copy the JAR to a server or deploy to a container
                }
            }
        }
    }

    post {
        success {
            echo 'Build successful! Additional post-build actions can be added here.'
        }
        failure {
            echo 'Build failed! Additional actions for failure can be added here.'
        }
    }
}
