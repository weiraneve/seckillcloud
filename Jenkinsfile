pipeline
{
  agent any
  stages {
    stage('Build')
    {
      steps {
        sh" mvn clean package "
      }
    }
      stage('Deploy')
    {
      steps {
         sh" docker-compose up --build -d "
      }
    }
  }
}
