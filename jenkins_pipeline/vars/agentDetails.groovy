def call() {
    script {
        def os       = sh(script: 'uname -a', returnStdout: true).trim()
        def hostname = sh(script: 'hostname', returnStdout: true).trim()
        def cpu      = sh(script: 'nproc', returnStdout: true).trim()
        def memory   = sh(script: 'free -h | grep Mem', returnStdout: true).trim()
        def java     = sh(script: 'java -version 2>&1', returnStdout: true).trim()
        def user     = sh(script: 'whoami', returnStdout: true).trim()

        sh """
            echo "=============================="
            echo "   Agent Details"
            echo "=============================="
            echo "Node Name      : ${env.NODE_NAME}"
            echo "Node Labels    : ${env.NODE_LABELS}"
            echo "Workspace      : ${env.WORKSPACE}"
            echo "OS Details     : ${os}"
            echo "Hostname       : ${hostname}"
            echo "CPU Cores      : ${cpu}"
            echo "Memory         : ${memory}"
            echo "Java Version   : ${java}"
            echo "Running As     : ${user}"
            echo "------------------------------"
            echo "Disk Usage :"
            echo "Filesystem      Size  Used  Avail  Use%  Mounted"
            echo "--------------------------------------------------"
            df -h | grep -E "^/dev" | awk '{printf "%-15s %-6s %-6s %-6s %-6s %s\\n", \$1, \$2, \$3, \$4, \$5, \$6}'
            echo "=============================="
        """
    }
}
