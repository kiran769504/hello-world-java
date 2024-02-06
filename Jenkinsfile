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
                        sh "${env.MAVEN_HOME}/bin/mvn clean deploy -Dmaven.javadoc.skip=true -Dsource.skip=true"
                    }
                }
            }
        }

        // ... existing stages ...

        stage('Deployment') {
            steps {
                echo "Deploying to Artifactory..."
                catchError {
                    sh "${env.MAVEN_HOME}/bin/mvn deploy"
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
