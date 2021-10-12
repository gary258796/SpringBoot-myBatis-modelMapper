pipeline {
  agent any
  stages {
      stage('Check out code from SCM.') {
        steps {
            git branch: 'main',
                        credentialsId: '312373b3-3fa1-4bf6-af08-49a7b740e417',
                        url: 'https://github.com/gary258796/SpringBoot-myBatis-modelMapper.git'
        }
      }
      stage('Build project') {
        tools {
            jdk 'jdk_1.8'
        }
        steps {
          withMaven(maven:'maven_3.6.2') {
            sh 'mvn clean package'
          }
        }
      }
      stage('Run SonarQube analysis') {
        tools {
            jdk 'jdk_11'
        }
        steps {
            withSonarQubeEnv(credentialsId: 'fc3b795e-77e7-4d87-845e-e9aee57560a1', installationName: 'sonarqube_server') {
              withMaven(maven:'maven_3.6.2') {
                sh 'mvn sonar:sonar'
              }
            }
        }
      }
  }
}
