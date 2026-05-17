def call(Map config = [:]) {

    withCredentials([
        string(
            credentialsId: config.credentialsId,
            variable: 'GITHUB_TOKEN'
        )
    ]) {

        def repo = config.repo
        def prNumber = config.prNumber.toString().replace("PR-", "")
        def payload = groovy.json.JsonOutput.toJson([body: config.message])

        writeFile file: 'github-comment.json', text: payload

        withEnv(["REPO=${repo}", "PR_NUMBER=${prNumber}"]) {
            sh '''#!/bin/bash
                set +x

                response=$(curl -s -o response.json -w "%{http_code}" \
                    -X POST \
                    -H "Authorization: Bearer $GITHUB_TOKEN" \
                    -H "Accept: application/vnd.github+json" \
                    -H "Content-Type: application/json" \
                    --data @github-comment.json \
                    "https://api.github.com/repos/$REPO/issues/$PR_NUMBER/comments")

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
