pipeline {
    agent any

    parameters {
        string(name: 'ENVIRONMENT', defaultValue: 'production', description: 'Target deployment environment')
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code manually...'
                git url: 'https://github.com/kiran769504/hello-world-java.git', branch: 'master'
            }
        }

        stage('Unit Test') {
            steps {
                script {
                    echo 'Running unit tests...'
                    try {
                        sh 'mvn clean test'
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Unit tests failed: ${e.message}")
                    }
                }
            }
        }

        stage('Integration Test') {
            when {
                expression { params.RUN_INTEGRATION_TESTS == 'true' }
            }
            steps {
                script {
                    echo 'Running integration tests...'
                    try {
                        sh 'mvn verify -Pintegration-tests'
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Integration tests failed: ${e.message}")
                    }
                }
            }
        }

        stage('Artifact Management') {
            steps {
                script {
                    echo 'Publishing artifacts...'
                    // Adjust the path and file extension based on your project
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage('Deployment') {
            steps {
                script {
                    echo "Deploying to ${ENVIRONMENT} environment..."
                    // Implement your deployment strategy (e.g., copy JAR file to a server)
                    // Adjust the path and file name based on your project
                    sh "scp target/*.jar user@server:/path/to/deployment"
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
