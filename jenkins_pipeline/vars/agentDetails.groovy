def call() {
    script {
        echo "=============================="
        echo "   Agent Details"
        echo "=============================="
        echo "Node Name      : ${env.NODE_NAME}"
        echo "Node Labels    : ${env.NODE_LABELS}"
        echo "Workspace      : ${env.WORKSPACE}"

        def os = sh(script: 'uname -a', returnStdout: true).trim()
        echo "OS Details     : ${os}"

        def hostname = sh(script: 'hostname', returnStdout: true).trim()
        echo "Hostname       : ${hostname}"

        def cpu = sh(script: 'nproc', returnStdout: true).trim()
        echo "CPU Cores      : ${cpu}"

        def memory = sh(script: 'free -h | grep Mem', returnStdout: true).trim()
        echo "Memory         : ${memory}"

        def disk = sh(script: 'df -h | grep -E "^/dev"', returnStdout: true).trim()
        echo "Disk Usage     : ${disk}"

        def java = sh(script: 'java -version 2>&1', returnStdout: true).trim()
        echo "Java Version   : ${java}"

        def user = sh(script: 'whoami', returnStdout: true).trim()
        echo "Running As     : ${user}"

        echo "=============================="
    }
}