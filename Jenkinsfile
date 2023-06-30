pipeline {
    agent any

    stages {

        stage("test") {

            steps {
                echo 'Testing the Microservices'
            }
        }

        stage("build and deploy") {

            steps {
                echo 'Building Taiga the Microservice...'
                dir("DEVOPS/automation") {
                    sh 'python3 deployment_automation.py'
                }
            }
        }

    }
}