pipeline {
    agent any

    tools {
        maven 'Maven3'
    }
    
     parameters {
        choice(name: 'env', choices: ['qa', 'dev', 'prod'], description: 'Select Environment')
        choice(name: 'suite', choices: ['default', 'smoke', 'regression'], description: 'Select Test Suite')
        choice(name: 'runMode', choices: ['local', 'remote'], description: 'Run tests locally or on Selenium Grid')
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
                bat 'mvn clean'
            }
        }

        stage('Run UI & API Tests') {
            steps {
                bat "mvn test -Denv=%env% -DrunMode=%runMode% -DsuiteXmlFile=test-suites/%suite%.xml"
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