#!groovy

def regionToAwsRegion = ['us': 'us-east-1', 'jp': 'ap-northeast-1', 'gb': 'eu-west-2', 'ie': 'eu-west-1', 'sg': 'ap-southeast-1', 'au': 'ap-southeast-2']

def AWSRegion = regionToAwsRegion[Region]

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
                    					def output = sh(returnStdout: true, script: 'pwd')
                    					echo "Output: ${output}"
							def output2=sh(returnStdOut: true, script: '/usr/local/bin/aws lexv2-models --region us-east-1 start-test-execution --test-set-id P1MSHKN1E6 --target \'{"botAliasTarget":{"botId":"TM7XGIZCM9","botAliasId":"TSTALIASID","localeId":"en_US"}}\' --api-mode NonStreaming --output text --query \'{testExecutionId:testExecutionId}\' --region $AWSRegion)'
							echo "Output2: ${output2}"																	
                				}
       			        	sh "/usr/local/bin/aws lexv2-models --region us-east-1 start-test-execution --test-set-id P1MSHKN1E6 --target '{\"botAliasTarget\":{\"botId\":\"TM7XGIZCM9\",\"botAliasId\":\"TSTALIASID\",\"localeId\":\"en_US\"}}' --api-mode NonStreaming --output text --query '{testExecutionId:testExecutionId}' --region $AWSRegion"     
    	 		 		}
			}
		}
	}
}
