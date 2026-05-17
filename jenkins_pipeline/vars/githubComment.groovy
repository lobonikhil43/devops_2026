def call(Map config = [:]) {

    withCredentials([
        string(
            credentialsId: config.credentialsId,
            variable: 'GITHUB_TOKEN'
        )
    ]) {

        withEnv([
            "GITHUB_REPO=${config.repo}",
            "PR_NUMBER=${config.prNumber}",
            "PR_MESSAGE=${config.message}"
        ]) {

            sh '''
                #!/bin/bash
                set +x

                response=$(curl -s -o response.json -w "%{http_code}" \
                  -X POST \
                  -H "Authorization: token $GITHUB_TOKEN" \
                  -H "Accept: application/vnd.github+json" \
                  -H "Content-Type: application/json" \
                  -d "{\"body\": \"$PR_MESSAGE\"}" \
                  "https://api.github.com/repos/$GITHUB_REPO/issues/$PR_NUMBER/comments")

                echo "GitHub API Status: $response"

                if [ "$response" -ge 200 ] && [ "$response" -lt 300 ]; then
                    echo "✅ PR comment posted successfully"
                else
                    echo "❌ Failed to post PR comment"
                    cat response.json
                    exit 1
                fi
            '''
        }
    }
}