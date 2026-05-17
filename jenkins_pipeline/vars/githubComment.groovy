def call(Map config = [:]) {
    def token       = config.token
    def repo        = config.repo
    def prNumber    = config.prNumber
    def message     = config.message

    sh """#!/bin/bash
        set +x
        exec 2>/dev/null

        curl -s -X POST \\
            -H "Authorization: token ${token}" \\
            -H "Content-Type: application/json" \\
            -d '{"body": "${message}"}' \\
            "https://api.github.com/repos/${repo}/issues/${prNumber}/comments"
    """
}
