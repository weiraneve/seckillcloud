pipeline
{
  agent any
  stages {
    stage('Build')
    {
      steps {
        sh" mvn package "
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
