def call(Map config = [:]) {
    def url = config.url
    def branch = config.branch
    def credentialsId = config.credentialsId

    try {
        echo "Cloning ${url} on branch: ${branch}"
        git(
            url: url,
            branch: branch,
            credentialsId: credentialsId
        )
        echo "Clone successful!"
    } catch (Exception e) {
        error("Clone failed: ${e.message}")
    }
}