#!/usr/bin/env groovy

/**
 * 所有JOB名字命名规则：
 * dubbo服务以-api-impl结尾，后端管理web以-admin结尾，前端PC以-front结尾，轻应用以-mobile结尾，智慧商业中后台的定时任务或消息接收(没有LISTEN端口)以-taskOrReceiver结尾
 * 开发、测试、准生产环境一套Jenkins即可，生产环境的Jenkins需要单独部署，并且在上述命名基础上都加上-prd结尾
 *
 * Dubbo服务脚本规则
 * 创建/work/dubbo/${instanceName}目录，里面包含smartbiz-dubbo.sh，${instanceName}*-exec.jar
 *
 * Jboss4.3脚本规则
 * 在/work/upgrade/放置重启JBOSS脚本，其他按照JBOSS4.3规范进行
 *
 * 具体部署的服务器地址在Jenkins服务器的/work/jenkins目录中的gbss_instances.json文件中
 */

def profile = getProfile()

// 设置Pipe只保存最近5次的build
def discarder = [$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '5']]
properties([discarder])

//if (profile == 'prd' || profile == 'drprd') {
//	properties([discarder])
//} else {
	// 定时0点的某个时间点运行
	//def triggers = pipelineTriggers([[$class:"SCMTrigger", scmpoll_spec:"H 0 * * *"]])

	//properties([discarder, triggers])
//}

node {
	def jobName = env.JOB_NAME
	if (jobName.contains("/")) {
		jobName = jobName.substring(0, jobName.indexOf("/"))
	}

	try {
		stage('Checkout') {
			checkout scm

			dir ("${env.WORKSPACE}/gbss-config") {
				git url: "ssh://git@git.infinitus.com.cn:7999/gbssb/gbss-config.git"
			}
			echo "My profile is: ${profile}"
			echo "My WORKSPACE is: ${env.WORKSPACE}"
			echo "My branch is: ${env.BRANCH_NAME} and Job name is: ${env.JOB_NAME}"
		}

		stage('Build') {
			def mvnHome = tool 'M3'

			def mvnProfile = profile

			// PC前端
			if (env.JOB_NAME.contains("-front")) {
				mvnProfile = mvnProfile + ",front"
			}

			// MOBILE轻应用
			if (env.JOB_NAME.contains("-mobile")) {
				mvnProfile = mvnProfile + ",mobile"
			}

			if (env.JOB_NAME.contains("-api-impl")) {
				echo "Building dubbo api"
				sh "${mvnHome}/bin/mvn clean install -P prod -Dmaven.test.skip=true -U -Pmain,${mvnProfile}"
			} else if(env.JOB_NAME.contains("-taskOrReceiver")){
				echo "Building task or receiver"
				sh "${mvnHome}/bin/mvn clean install -P prod -Dmaven.test.skip=true -U -Pmain,${mvnProfile}"
			}else {
				echo "Building webapp"
				sh "${mvnHome}/bin/mvn clean install -P prod -Dmaven.test.skip=true -U -Pmain,${mvnProfile}"
			}
		}

		stage('Deploy') {

			def json = readJSON file: "${env.WORKSPACE}/gbss-config/smartbiz_instances_${profile}.json"
		echo "Deploy 1"
			// deploy dubbo api
			if (env.JOB_NAME.contains("-api-impl")) {
				if (json[profile] != null && json[profile][jobName] != null) {
					def ins = json[profile][jobName];
					
					for (def i =0; i < ins.size(); i++) {
						if (env.BRANCH_NAME == null || env.BRANCH_NAME == "develop" || env.BRANCH_NAME == "master" || env.BRANCH_NAME.startsWith(ins[i].branch)) {
							updateDubbo(ins[i].username, ins[i].password, ins[i].ip, ins[i].port, ins[i].projectName, ins[i].appName, ins[i].instanceName)
						}
					}
				}
			}

			// deploy task or receiver
			if (env.JOB_NAME.contains("-taskOrReceiver")) {
				if (json[profile] != null && json[profile][jobName] != null) {
					def ins = json[profile][jobName];
					for (def i =0; i < ins.size(); i++) {
						if (env.BRANCH_NAME == null || env.BRANCH_NAME == "develop" || env.BRANCH_NAME == "master" || env.BRANCH_NAME.startsWith(ins[i].branch)) {
							updateTaskOrReceiver(ins[i].username, ins[i].password, ins[i].ip, ins[i].port, ins[i].projectName, ins[i].appName, ins[i].instanceName)
						}
					}
				}
			}

			// deploy web
			if (env.JOB_NAME.contains("-admin") || env.JOB_NAME.contains("-front") || env.JOB_NAME.contains("-mobile")) {
				if (json[profile] != null && json[profile][jobName] != null) {
					def ins = json[profile][jobName];
					for (def i =0; i < ins.size(); i++) {
						if (env.BRANCH_NAME == null || env.BRANCH_NAME == "develop" || env.BRANCH_NAME == "master" || env.BRANCH_NAME.startsWith(ins[i].branch)) {
							updateTomcat(ins[i].username, ins[i].password, ins[i].ip, ins[i].port, ins[i].projectName, ins[i].appName, ins[i].instanceName, ins[i].packageName)
						}
					}
				}
			}
		}
	} catch (err) {
		currentBuild.result = "FAILED"
		throw err
	} finally {
		// Success or failure, always send notifications
		def json_config = readJSON file: "${env.WORKSPACE}/gbss-config/gbss_config.json"
		def to_email = "ci.gbss@infinitus-int.com"
		if (json_config[jobName] != null && json_config[jobName]["email"] != null) {
			to_email = json_config[jobName]["email"]
		}
		notifyBuild(currentBuild.result, to_email)
	}
}

def getProfile() {
	def profile = "dev"

	// 开发环境(develop和feature/hotfix分支)
	def matcher1 = (env.BRANCH_NAME =~ /develop|(feature|hotfix)\/(.*)|/)
	if (matcher1.matches()) {
		profile = "dev"
	}


	// 准生产环境
	if (env.BRANCH_NAME != null && env.BRANCH_NAME.contains("release")) {
		profile = "staging"
	}

	// 压测环境
	if ((env.BRANCH_NAME == null || env.BRANCH_NAME == "master" || env.BRANCH_NAME == "develop") && env.JOB_NAME.contains("-sp")) {
		profile = "staging-p"
	}

	// 生产环境
	if ((env.BRANCH_NAME == null || env.BRANCH_NAME == "master" || env.BRANCH_NAME.contains("release")) && env.JOB_NAME.contains("-prd")) {
		profile = "prd"
	}

	// 灾备生产环境
	if ((env.BRANCH_NAME == null || env.BRANCH_NAME == "master") && env.JOB_NAME.contains("-drprd")) {
		profile = "drprd"
	}

	profile
}

def updateDubbo(username, password, ip, port, projectName, appName, instanceName) {
	echo "update dubbo ${instanceName} now!"
	sshagent(credentials: [password]) {
		sh """ 
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} 'rm -f /work/dubbo/${instanceName}/${instanceName}*-exec.jar'
        	scp -B -o StrictHostKeyChecking=no ${appName}/target/${instanceName}*-exec.jar ${username}@${ip}:/work/dubbo/${instanceName}
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} /work/dubbo/${instanceName}/smartbiz-dubbo.sh restart
        """
	}

	// 检测启动是否成功
	timeout (time: 300, unit: 'SECONDS') {
		def nc_str
		while (nc_str == null || nc_str.length() <= 0) {
			try {
				nc_str = sh(script: "nc -z -w 1 ${ip} ${port}", returnStdout: true).trim()
			} catch (err) {
				nc_str = null
			}
			if (nc_str != null && (nc_str.contains("succeeded") || nc_str.contains("Connected") || nc_str.contains("OK"))) {
				echo "dubbo-${instanceName}-api start successful!"
			} else {
				echo "dubbo-${instanceName}-api is starting, please wait 5 seconds!"
				sleep (time: 5, unit: 'SECONDS')
			}
		}
	}

}

def updateTaskOrReceiver(username, password, ip, port, projectName, appName, instanceName) {
	echo "update task or receiver ${instanceName} now!"
	sshagent(credentials: [password]) {
		sh """ 
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} 'rm -f /work/dubbo/${instanceName}/${instanceName}*-exec.jar'
        	scp -B -o StrictHostKeyChecking=no ${appName}/target/${instanceName}*-exec.jar ${username}@${ip}:/work/dubbo/${instanceName}
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} /work/dubbo/${instanceName}/smartbiz-dubbo.sh restart
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} "ps -ef | grep java| grep ${instanceName}| grep -v grep"
        """
	}

}

def updateTomcat(username, password, ip, port, projectName, appName, instanceName, packageName) {
	echo "update tomcat ${instanceName} now!"
	if (packageName==null){
		echo "packageName is null,setting packageName with instanceName..."
		packageName = instanceName
		echo "set packageName finish!"
	}
	sshagent(credentials: [password]) {
		sh """
	        	ssh -o StrictHostKeyChecking=no ${username}@${ip} rm -rf /work/tomcat-${instanceName}/webapps/${packageName}*
	        	scp -B -o StrictHostKeyChecking=no ${appName}/target/${packageName}*.war ${username}@${ip}:/work/tomcat-${instanceName}/webapps
	        	ssh -o StrictHostKeyChecking=no ${username}@${ip} /work/tomcat-${instanceName}/smartbiz-tomcat.sh restart
	        """
	}

	// 检测启动是否成功
	timeout (time: 300, unit: 'SECONDS') {
		def nc_str
		while (nc_str == null || nc_str.length() <= 0) {
			try {
				nc_str = sh(script: "nc -z -w 1 ${ip} ${port}", returnStdout: true).trim()
			} catch (err) {
				nc_str = null
			}
			if (nc_str != null && (nc_str.contains("succeeded") || nc_str.contains("Connected") || nc_str.contains("OK"))) {
				echo "tomcat ${instanceName} start successful!"
			} else {
				echo "tomcat ${instanceName} is starting, please wait 10 seconds!"
				sleep (time: 10, unit: 'SECONDS')
			}
		}
	}

}

def updateJboss4(username, password, ip, port, projectName, appName, instanceName) {
	echo "update jboss ${instanceName} now!"
	sshagent(credentials: [password]) {
		sh """
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} rm -f /work/jboss/jboss-eap-4.3/jboss-as/server/${instanceName}/deploy/${appName}*.war
        	scp -B -o StrictHostKeyChecking=no gbss-${projectName}-webapp/target/${appName}*.war ${username}@${ip}:/work/jboss/jboss-eap-4.3/jboss-as/server/${instanceName}/deploy
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} /work/upgrade/restart_jboss.sh ${instanceName} ${port} ${appName}
        """
	}

	// 检测启动是否成功
	timeout (time: 300, unit: 'SECONDS') {
		def f5_status
		while (f5_status == null || f5_status.length() <= 0) {
			try {
				f5_status = sh(script: "curl -m 5 http://${ip}:${port}/public/status/f5", returnStdout: true).trim()
			} catch (err) {
				f5_status = null
			}
			if (f5_status != null && f5_status.contains("F5_OK")) {
				echo "JBOSS ${instanceName} status: ${f5_status}"
			} else {
				f5_status = null
				echo "JBOSS ${instanceName} is starting, please wait 10 seconds!"
				sleep (time: 10, unit: 'SECONDS')
			}
		}
	}
}

def updateJboss6(username, password, ip, port, projectName, appName, instanceName) {
	echo "update jboss ${instanceName} now!"
	sshagent(credentials: [password]) {
		sh """
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} rm -f /work/jboss6/appserver/${instanceName}/deployments/*.war*
        	scp -B -o StrictHostKeyChecking=no gbss-${projectName}-webapp/target/${appName}*.war ${username}@${ip}:/work/jboss6/appserver/${instanceName}/deployments
        	ssh -o StrictHostKeyChecking=no ${username}@${ip} "cd /work/jboss6/bin/${instanceName} && ./stop-jboss.sh && sleep 10 && ./start-jboss.sh"
        """
	}

	// 检测启动是否成功
	timeout (time: 300, unit: 'SECONDS') {
		def f5_status
		while (f5_status == null || f5_status.length() <= 0) {
			try {
				f5_status = sh(script: "curl -m 5 http://${ip}:${port}/public/status/f5", returnStdout: true).trim()
			} catch (err) {
				f5_status = null
			}
			if (f5_status != null && f5_status.contains("F5_OK")) {
				echo "JBOSS ${instanceName} status: ${f5_status}"
			} else {
				f5_status = null
				echo "JBOSS ${instanceName} is starting, please wait 10 seconds!"
				sleep (time: 10, unit: 'SECONDS')
			}
		}
	}
}

def notifyBuild(String buildStatus = 'STARTED', String to_email) {
	// build status of null means successful
	buildStatus = buildStatus ?: 'SUCCESS'

	// Default values
	def colorName = 'RED'
	def colorCode = '#FF0000'
	def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
	def summary = "${subject} (${env.BUILD_URL})"
	def details = """<p>BUILD STATUS: ${buildStatus}</p><p>Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'</p><p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

	// Override default values based on build status
	if (buildStatus == 'STARTED') {
		color = 'YELLOW'
		colorCode = '#FFFF00'
	} else if (buildStatus == 'SUCCESS') {
		color = 'GREEN'
		colorCode = '#00FF00'
	} else {
		color = 'RED'
		colorCode = '#FF0000'
	}

	// Send notifications
	//slackSend (color: colorCode, message: summary)

	//hipchatSend (color: color, notify: true, message: summary)
	emailext (
			subject: subject,
			body: details,
			to: to_email,
			recipientProviders: [[$class: 'DevelopersRecipientProvider']]
	)
}