def call() {
    sh '''#!/bin/bash
        set +x
        exec 2>/dev/null

        echo "------------------------------"
        echo "  Linting Bash Files"
        echo "------------------------------"

        if ! command -v shellcheck &> /dev/null; then
            echo "  shellcheck not installed - skipping"
            exit 0
        fi

        files=$(find . -name "*.sh" -not -path "*/.git/*")
        if [ -z "$files" ]; then
            echo "  No Bash files found"
            exit 0
        fi

        failed=0
        for f in $files; do
            result=$(shellcheck "$f" 2>&1)
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