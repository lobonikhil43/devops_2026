def call(Map config = [:]) {

    withCredentials([
        string(
            credentialsId: config.credentialsId,
            variable: 'GITHUB_TOKEN'
        )
    ]) {

        def repo = config.repo
        def prNumber = config.prNumber.toString().replace("PR-", "")

        sh """#!/bin/bash
            set +x

            response=\$(curl -s -o response.json -w "%{http_code}" \
                -X POST \
                -H "Authorization: Bearer \$GITHUB_TOKEN" \
                -H "Accept: application/vnd.github+json" \
                -H "Content-Type: application/json" \
                -d '{"body": "${config.message.replace('"', '\\"')}"}' \
                "https://api.github.com/repos/${repo}/issues/${prNumber}/comments")

            echo "GitHub API Status: \$response"

            if [ "\$response" -ge 200 ] && [ "\$response" -lt 300 ]; then
                echo "✅ PR comment posted successfully"
            else
                echo "❌ Failed to post PR comment"
                cat response.json
                exit 1
            fi
        """
    }
}
