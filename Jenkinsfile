pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/ckc2002/expense_automation.git'
            }
        }

        stage('Clean & Build') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Run API Tests') {
            steps {
                sh 'mvn test -DsuiteXmlFile=test-suites/default.xml'
            }
        }

        stage('Archive Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Allure Report') {
            steps {
                allure includeProperties: false,
                       jdk: '',
                       results: [[path: 'allure-results']]
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'logs/*.log', fingerprint: true
        }
        success {
            echo '🎉 Tests Passed!'
        }
        failure {
            echo '❌ Tests Failed!'
        }
    }
}