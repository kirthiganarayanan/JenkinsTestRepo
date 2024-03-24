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
							echo "Start Test Execution Result is: ${output2}"
							sleep(120)
							def output3= sh(returnStdout: true, script: "/usr/local/bin/aws lexv2-models list-test-execution-result-items --result-filter-by '{\"resultTypeFilter\":\"OverallTestResults\",\"conversationLevelTestResultsFilterBy\":{\"endToEndResult\": \"Mismatched\"}}' --region \"$Region\"  --output text --query \'testExecutionResults.overallTestResults.items[0].endToEndResultCounts.Mismatched\' --test-execution-id ${output2}")
							echo "Result is: ${output3}"
							if (output3 != "0") {
								echo "In failure path"
               							echo "Init currentResult: ${currentBuild.currentResult}"
								currentBuild.currentResult = "FAILURE"
								echo "Post Init currentResult: ${currentBuild.currentResult}"
						    }
                				}       			        	
    	 		 		}
			}
		}
	}
}
