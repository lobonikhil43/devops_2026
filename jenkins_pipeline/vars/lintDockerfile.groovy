def call() {
    sh '''#!/bin/bash
        set +x
        exec 2>/dev/null

        echo "------------------------------"
        echo "  Linting Dockerfiles"
        echo "------------------------------"

        if ! command -v hadolint &> /dev/null; then
            echo "  hadolint not installed - skipping"
            exit 0
        fi

        files=$(find . -name "Dockerfile*" -not -path "*/.git/*")
        if [ -z "$files" ]; then
            echo "  No Dockerfiles found"
            exit 0
        fi

        failed=0
        for f in $files; do
            result=$(hadolint "$f" 2>&1)
            if [ -z "$result" ]; then
                echo "  ✅ $f"
            else
                echo "  ❌ $f"
                echo "$result"
                failed=1
            fi
        done

        exit $failed
    '''
}