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

                curl -s -X POST \
                  -H "Authorization: token $GITHUB_TOKEN" \
                  -H "Content-Type: application/json" \
                  -d "{\"body\": \"$PR_MESSAGE\"}" \
                  "https://api.github.com/repos/$GITHUB_REPO/issues/$PR_NUMBER/comments"
            '''
        }
    }
}