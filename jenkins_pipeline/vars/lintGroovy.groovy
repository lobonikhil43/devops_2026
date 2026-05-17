def call() {
    try {
        sh '''#!/bin/bash
            chmod +x ./gradlew
            ./gradlew codenarcMain
        '''
        return "✅ PASSED"
    } catch (Exception e) {
        return "❌ FAILED"
    }
}