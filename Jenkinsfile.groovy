#!groovy
pipeline  {
	agent any
	stages {
		stage("AWS CLI demo") {
			steps {
				withCredentials([[
       					$class: 'AmazonWebServicesCredentialsBinding',
       					credentialsId: 'aws-jenkins-demo-id',
      					accessKeyVariable: 'AWS_ACCESS_KEY_ID',
       					secretKeyVariable: 'AWS_SECRET_ACCESS_KEY' ]]) {
						script {							
							def output2= sh(returnStdout: true, script: '/usr/local/bin/aws lexv2-models start-test-execution --test-set-id P1MSHKN1E6 --target \'{"botAliasTarget":{"botId":"TM7XGIZCM9","botAliasId":"TSTALIASID","localeId":"en_US"}}\' --api-mode NonStreaming --output text --query \'{testExecutionId:testExecutionId}\' --region "$Region"')
							echo "Output2: ${output2}"																	
                				}       			        	
    	 		 		}
			}
		}
	}
}
