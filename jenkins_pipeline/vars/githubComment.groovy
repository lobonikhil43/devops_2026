def call(Map config = [:]) {

    withCredentials([
        string(
            credentialsId: config.credentialsId,
            variable: 'GITHUB_TOKEN'
        )
    ]) {

        sh """#!/bin/bash
            set +e
            set +x

            cat > payload.json <<EOF
{
  "body": ${groovy.json.JsonOutput.toJson(config.message)}
}
EOF

            response=\$(curl -s -o response.json -w "%{http_code}" \
                -X POST \
                -H "Authorization: Bearer \$GITHUB_TOKEN" \
                -H "Accept: application/vnd.github+json" \
                -H "Content-Type: application/json" \
                --data @payload.json \
                "https://api.github.com/repos/${config.repo}/issues/${config.prNumber}/comments")

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