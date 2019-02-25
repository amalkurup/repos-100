node {
	def MAVEN_HOME = tool "Maven_HOME"
    def JAVA_HOME = tool "JAVA_HOME"
    env.PATH="${env.PATH}:${MAVEN_HOME}/bin:${JAVA_HOME}/bin"
    
    def GIT_URL='https://github.com/amalkurup/repos-100.git'
	def OS_PROJECT_NAME='infy-commercial'
	def REPO_NAME='repos-100'
    
    stage('First Time Deployment'){
        script{
            openshift.withCluster() {
                openshift.withProject("${OS_PROJECT_NAME}") {
                    def bcSelector = openshift.selector( "bc", "${REPO_NAME}")
                    def bcExists = bcSelector.exists()
                    if (!bcExists) {
                        openshift.newApp("$APP_TEMPLATE_URL")
                    } else {
                        sh 'echo build config already exists'  
                    } 
                }
            }
        }
    }
    
	stage ('Checkout') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[ url: "${GIT_URL}"]]])
    }
    
	stage('Packaging') {
            
        bat 'mvn -DskipTests package' 
            
    }
    
	stage("Dev - Building Application"){
        script{
            openshift.withCluster() {
                openshift.withProject("${OS_PROJECT_NAME}"){
                    openshift.startBuild("${REPO_NAME}")   
                }
            }
        }
    }

    stage("Dev - Deploying Application"){
	   script{
            openshift.withCluster() {
                openshift.withProject("${OS_PROJECT_NAME}"){
                    openshiftDeploy(deploymentConfig: "${REPO_NAME}")   
                }
            }
        }  
    }
        
}