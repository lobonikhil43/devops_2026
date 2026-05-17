def call() {
    sh '''#!/bin/bash
        set +x
        exec 2>/dev/null

        echo "------------------------------"
        echo "  Linting JSON Files"
        echo "------------------------------"

        files=$(find . -name "*.json" -not -path "*/.git/*")
        if [ -z "$files" ]; then
            echo "  No JSON files found"
            exit 0
        fi

        failed=0
        for f in $files; do
            result=$(python3 -m json.tool "$f" > /dev/null 2>&1)
            if [ $? -eq 0 ]; then
                echo "  ✅ $f"
            else
                echo "  ❌ $f - Invalid JSON"
                failed=1
            fi
        done

        exit $failed
    '''
}