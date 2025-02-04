pipeline {
    agent {
        label "${AGENT}"
    }

    stages {
        stage('Test') {
            steps {
                sh "./gradlew clean test --info --parallel"
            }
        }

        stage('Build') {
            steps {
                sh "docker build -t eunhomoon/wedding:${RELEASE_TAG} ."
            }
        }

        //stage('push to docker hub') {
        //    steps {
        //        withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        //            sh "docker login -u ${USERNAME} -p ${PASSWORD}"
        //            sh "docker push eunhomoon/wedding:${RELEASE_TAG}"
        //        }
        //    }
        //}

        stage('Deploy') {
            steps {
                dir('/home/janek/wedding') {
                    sh "docker compose up -d --build"

                }
            }
        }
    }
}