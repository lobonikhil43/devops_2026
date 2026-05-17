def call() {
    script {
        def os       = sh(script: 'uname -a', returnStdout: true).trim()
        def hostname = sh(script: 'hostname', returnStdout: true).trim()
        def cpu      = sh(script: 'nproc', returnStdout: true).trim()
        def memory   = sh(script: 'free -h | grep Mem', returnStdout: true).trim()
        def disk     = sh(script: 'df -h | grep -E "^/dev"', returnStdout: true).trim()
        def java     = sh(script: 'java -version 2>&1', returnStdout: true).trim()
        def user     = sh(script: 'whoami', returnStdout: true).trim()

        echo """
==============================
🖥️  Agent Details
==============================
Node Name      : ${env.NODE_NAME}
Node Labels    : ${env.NODE_LABELS}
Workspace      : ${env.WORKSPACE}
OS Details     : ${os}
Hostname       : ${hostname}
CPU Cores      : ${cpu}
Memory         : ${memory}
Disk Usage     : ${disk}
Java Version   : ${java}
Running As     : ${user}
==============================
        """
    }
}
