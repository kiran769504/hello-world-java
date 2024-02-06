pipeline {
    agent any

    parameters {
        string(name: 'ENVIRONMENT', defaultValue: 'UAT', description: 'Target deployment environment')
    }

    environment {
        MAVEN_HOME = tool name: 'Maven', type: 'maven'
        PATH = "${env.MAVEN_HOME}/bin:${env.PATH}"
        MAVEN_OPTS = '-Xmx2g -XX:MaxPermSize=512m -XX:+UseParallelGC'
        ARTIFACTORY_CREDS = credentials('artifactory-credentials')  // Assuming you've set up Jenkins credentials with ID 'artifactory-credentials'
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
                    // Deploy to Artifactory using Maven deploy goal
                    withCredentials([usernamePassword(credentialsId: 'artifactory-username', passwordVariable: 'ARTIFACTORY_PASSWORD', usernameVariable: 'ARTIFACTORY_USERNAME')]) {
                        sh "${env.MAVEN_HOME}/bin/mvn deploy -DaltDeploymentRepository=artifactory-releases::default::http://18.117.92.97:8082/artifactory/libs-release-local -DaltSnapshotDeploymentRepository=artifactory-snapshots::default::http://18.117.92.97:8082/artifactory/libs-snapshot-local"
                    }
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
