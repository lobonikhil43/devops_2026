def call() {
    sh '''#!/bin/bash
        set +x
        exec 2>/dev/null

        echo "------------------------------"
        echo "  Linting YAML Files"
        echo "------------------------------"

        if ! command -v yamllint &> /dev/null; then
            echo "  yamllint not installed - skipping"
            exit 0
        fi

        files=$(find . -name "*.yml" -o -name "*.yaml" | grep -v ".git")
        if [ -z "$files" ]; then
            echo "  No YAML files found"
            exit 0
        fi

        failed=0
        for f in $files; do
            result=$(yamllint "$f" 2>&1)
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