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

        stage('Clean Old Reports') {
            steps {
                bat '''
                    if exist allure-results rmdir /s /q allure-results
                    if exist allure-report rmdir /s /q allure-report
                    if exist target rmdir /s /q target
                '''
            }
        }

        stage('Clean & Build') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Run UI & API Tests') {
            steps {
                bat "mvn test -Denv=${params.env} -DrunMode=${params.runMode} -DsuiteXmlFile=test-suites/${params.suite}.xml"
            }
        }

        stage('Archive Test Results') {
            steps {
                junit(
                    testResults: 'target/surefire-reports/TEST-*.xml',
                    allowEmptyResults: true,
                    skipPublishingChecks: true,
                    skipMarkingBuildUnstable: true
                )
                echo "After JUnit: ${currentBuild.currentResult}"
            }
        }

        stage('Allure Report') {
            steps {
                allure(
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']],
                    resultPolicy: 'LEAVE_AS_IS'
                )
                echo "After Allure: ${currentBuild.currentResult}"
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'logs/*.log', fingerprint: true, allowEmptyArchive: true
        }
        success {
            echo '🎉 Tests Passed!'
        }
        unstable {
            echo '⚠️ Build marked unstable. Check the stage result shown above.'
        }
        failure {
            echo '❌ Tests Failed!'
        }
    }
}