def call() {
    sh '''#!/bin/bash
        set +x
        exec 2>/dev/null

        echo "------------------------------"
        echo "  Linting Python Files"
        echo "------------------------------"

        if ! command -v flake8 &> /dev/null; then
            echo "  flake8 not installed - skipping"
            exit 0
        fi

        files=$(find . -name "*.py" -not -path "*/.git/*")
        if [ -z "$files" ]; then
            echo "  No Python files found"
            exit 0
        fi

        failed=0
        for f in $files; do
            result=$(flake8 "$f" 2>&1)
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