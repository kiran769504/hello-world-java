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

        stage('Build') {
            steps {
                script {
                    echo 'Building the project...'
                    try {
                        sh 'mvn clean install'
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Build failed: ${e.message}")
                    }
                }
            }
        }

        stage('Unit Test') {
            steps {
                script {
                    echo 'Running unit tests...'
                    try {
                        sh 'mvn test'
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Unit tests failed: ${e.message}")
                    }
                }
            }
        }

        stage('Integration Test') {
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
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage('Deployment') {
            steps {
                script {
                    echo "Deploying to ${ENVIRONMENT} environment..."
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
