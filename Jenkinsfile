pipeline {
    agent any

    parameters {
        string(name: 'ENVIRONMENT', defaultValue: 'UAT', description: 'Target deployment environment')
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
                echo 'Building the project...'
                script {
                    // Using catchError instead of try/catch
                    catchError {
                        sh 'mvn clean install'
                    }
                }
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running unit tests...'
                script {
                    catchError {
                        sh 'mvn test'
                    }
                }
            }
        }

        stage('Integration Test') {
            steps {
                echo 'Running integration tests...'
                script {
                    catchError {
                        sh 'mvn verify -Pintegration-tests'
                    }
                }
            }
        }

        stage('Artifact Management') {
            steps {
                echo 'Publishing artifacts...'
                archiveArtifacts 'target/*.jar'
            }
        }

        stage('Deployment') {
            steps {
                echo "Deploying to ${ENVIRONMENT} environment..."
                catchError {
                    // Use the correct path on the server for deployment
                    sh "scp target/hello-world-java-1.0-SNAPSHOT.jar ubuntu@ip-172-31-9-24:/home/ubuntu/projectartifacts/"
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
