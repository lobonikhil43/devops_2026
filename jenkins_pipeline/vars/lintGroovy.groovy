def call() {
    sh '''#!/bin/bash
        set +x
        exec 2>/dev/null

        echo "------------------------------"
        echo "  Linting Groovy Files"
        echo "------------------------------"

        if ! command -v npm-groovy-lint &> /dev/null; then
            echo "  npm-groovy-lint not installed - skipping"
            exit 0
        fi

        files=$(find . -name "*.groovy" -not -path "*/.git/*")
        if [ -z "$files" ]; then
            echo "  No Groovy files found"
            exit 0
        fi

        failed=0
        for f in $files; do
            result=$(npm-groovy-lint "$f" 2>&1)
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