pipeline {
    agent any

    parameters {
        string(name: 'ENVIRONMENT', defaultValue: 'UAT', description: 'Target deployment environment')
    }

    environment {
        MAVEN_HOME = tool name: 'Maven', type: 'maven'
        PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"
        MAVEN_OPTS = '-Xmx2g -XX:MaxPermSize=512m -XX:+UseParallelGC'
    }

    options {
        skipDefaultCheckout()  // Skip the default checkout to perform a more controlled one
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code manually...'
                checkout scm  // Use the default checkout for more control
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                script {
                    catchError {
                        // Disable the Javadoc and source JAR generation if not needed
                        sh "${env.MAVEN_HOME}/bin/mvn clean install -Dmaven.javadoc.skip=true -Dsource.skip=true"
                    }
                }
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running unit tests...'
                script {
                    catchError {
                        sh "${env.MAVEN_HOME}/bin/mvn test"
                    }
                }
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Integration Test') {
            steps {
                echo 'Running integration tests...'
                script {
                    catchError {
                        sh "${env.MAVEN_HOME}/bin/mvn verify -Pintegration-tests"
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
