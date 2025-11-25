pipeline {
    agent any

    parameters {
        string(name: 'ECR_REPO_URI', description: 'ID del repositorio privado')
        string(name: 'IMAGE_TAG', description: 'Versión de la imagen')
        string(name: 'AWS_REGION', description: 'Región del repositorio')
        choice(
            name: 'MICROSERVICIO',
            choices: ['venta', 'intranet', 'tiempo'],
            description: 'Elige el microservicio para subir su imagen'
        )
    }

    stages {
        stage('Checkout') { //Clonar
            steps {
                checkout scm
            }
        }

        stage('Init Submodules') {
            steps {
                sh '''
                    git config --global --add safe.directory $WORKSPACE
                    rm -rf backend
                    git submodule update --init --recursive
                '''
            }
        }

        stage('Borrar carpeta según elección') {
            steps {
                script {
                    def carpetas = [
                        venta    : 'backend/src/main/java/com/losagiles/CineAgile/controllersVenta',
                        intranet : 'backend/src/main/java/com/losagiles/CineAgile/controllersIntranet',
                        tiempo   : 'backend/src/main/java/com/losagiles/CineAgile/controllersTiempo'
                    ]

                    def todasCarpetas = [
                        'backend/src/main/java/com/losagiles/CineAgile/controllersVenta',
                        'backend/src/main/java/com/losagiles/CineAgile/controllersIntranet',
                        'backend/src/main/java/com/losagiles/CineAgile/controllersTiempo'
                    ]

                    def carpetaAConservar = carpetas[params.MICROSERVICIO]
                    for (c in todasCarpetas) {
                        if (c != carpetaAConservar) {
                            echo "Borrando carpeta: ${c}"
                            sh "rm -rf ${c}"
                        } else {
                            echo "Conservando carpeta: ${c}"
                        }
                    }
                }
            }
        }

        stage('Build Maven') {
            steps {
                sh 'cd backend && mvn clean package -DskipTests'
            }
        }

        stage('Loguarse a AWS ECR') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'aws-credentials-id', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    sh '''
                        echo "REGION: $AWS_REGION"
                        echo "REPO: $ECR_REPO_URI"
                        aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
                        aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
                        aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO_URI
                    '''
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                sh '''
                    cd backend
                    docker build -t $ECR_REPO_URI:$IMAGE_TAG .
                    docker push $ECR_REPO_URI:$IMAGE_TAG
                '''
            }
        }
    }

    post {
        success {
            echo "Imagen Docker subida a ECR: $ECR_REPO_URI:$IMAGE_TAG"
        }
        failure {
            echo "Falló el pipeline"
        }
    }
}

