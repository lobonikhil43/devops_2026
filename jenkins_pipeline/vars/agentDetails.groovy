def call() {
    script {
        sh '''#!/bin/bash
            set +x
            exec 2>/dev/null

            os=$(uname -a)
            hostname=$(hostname)
            cpu=$(nproc)
            memory=$(free -h | grep Mem | awk '{print "Total: "$2"  Used: "$3"  Free: "$4}')
            java=$(java -version 2>&1 | head -1)
            user=$(whoami)

            python=$(python3 --version 2>&1 || echo "Not Installed")
            docker=$(docker --version 2>&1 || echo "Not Installed")
            git=$(git --version 2>&1 || echo "Not Installed")

            echo "=============================="
            echo "   Agent Details"
            echo "=============================="
            echo "Node Name   : $hostname"
            echo "Workspace   : $WORKSPACE"
            echo "OS          : $os"
            echo "CPU Cores   : $cpu"
            echo "Memory      : $memory"
            echo "Java        : $java"
            echo "Running As  : $user"
            echo "------------------------------"
            echo "Tool Versions :"
            echo "  Python     : $python"
            echo "  Docker     : $docker"
            echo "  Git        : $git"
            echo "------------------------------"
            echo "Disk Usage  :"
            echo "Filesystem      Size  Used  Avail  Use%  Mounted"
            echo "--------------------------------------------------"
            df -h | grep -E "^/dev" | awk \'{printf "%-15s %-6s %-6s %-6s %-6s %s\\n", $1, $2, $3, $4, $5, $6}\'
            echo "=============================="
        '''
    }
}
