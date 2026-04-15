pipeline {
    agent any

	triggers {
        pollSCM('H/2 * * * *')
    }

    tools {
        maven 'Maven3'
    }

    parameters {
        choice(name: 'env', choices: ['qa', 'dev', 'prod'], description: 'Select Environment')
        choice(name: 'suite', choices: ['default', 'smoke', 'regression'], description: 'Select Test Suite')
        choice(name: 'runMode', choices: ['local', 'remote'], description: 'Run tests locally or on Selenium Grid')
    }
    
    
    stages {

		  stage('Check if Dev Repo Changed') {
		    steps {
		        script {
		            dir('devrepo') {
		                checkout([
		                    $class: 'GitSCM',
		                    branches: [[name: '*/main']],
		                    userRemoteConfigs: [[url: 'https://github.com/ckc2002/expense-tracker.git']]
		                ])
		
		                def lastCommit = bat(
		                    script: 'git rev-parse HEAD',
		                    returnStdout: true
		                ).trim()
		
		                def previousCommitFile = "${env.WORKSPACE}\\last_commit.txt"
		
		                if (fileExists(previousCommitFile)) {
		                    def oldCommit = readFile(previousCommitFile).trim()
		
		                    if (oldCommit == lastCommit) {
		                        echo "No changes in DEV repo. Skipping pipeline."
		                        currentBuild.result = 'NOT_BUILT'
		                        error("Stopping pipeline because no new commit found.")
		                    }
		                }
		
		                writeFile file: previousCommitFile, text: lastCommit
		            }
		        }
		    }
		}
    
    
        stage('Checkout Automation Repo') {
			    steps {
			        dir('automation') {
			            git branch: 'main',
			                url: 'https://github.com/ckc2002/expense_automation.git'
			        }
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
                bat 'cd automation && mvn clean'
            }
        }

        stage('Run UI & API Tests') {
            steps {
                bat "cd automation && mvn test -Denv=${params.env} -DrunMode=${params.runMode} -DsuiteXmlFile=test-suites/${params.suite}.xml"
            }
        }

        stage('Archive Test Results') {
            steps {
                junit(
                    testResults: 'automation/target/surefire-reports/TEST-*.xml',
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
                    results: [[path: 'automation/allure-results']],
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