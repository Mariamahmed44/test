#!/bin/bash

# Advanced Test Runner Script
# Provides intelligent test execution with retry logic, reporting, and notifications

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
NC='\033[0m'

# Default values
BROWSER="chrome"
ENVIRONMENT="production"
TEST_SUITE="all"
PARALLEL="false"
HEADLESS="true"
RETRY_COUNT="0"
TIMEOUT="300"
GENERATE_REPORT="true"
SEND_NOTIFICATION="false"

# Logging functions
log() { echo -e "${BLUE}[$(date +'%H:%M:%S')] $1${NC}"; }
error() { echo -e "${RED}[ERROR] $1${NC}" >&2; }
warning() { echo -e "${YELLOW}[WARNING] $1${NC}"; }
success() { echo -e "${GREEN}[SUCCESS] $1${NC}"; }
info() { echo -e "${PURPLE}[INFO] $1${NC}"; }

# Help function
show_help() {
    cat << EOF
Test Runner Script - Advanced Selenium Test Execution

Usage: $0 [OPTIONS]

Options:
    -b, --browser BROWSER       Browser to use (chrome, firefox, edge) [default: chrome]
    -e, --environment ENV       Environment (dev, staging, production) [default: production]
    -s, --suite SUITE          Test suite (all, smoke, regression, specific class) [default: all]
    -p, --parallel             Enable parallel execution
    -h, --headless             Enable headless mode [default: true]
    -r, --retry COUNT          Number of retry attempts for failed tests [default: 0]
    -t, --timeout SECONDS      Test timeout in seconds [default: 300]
    --no-report                Skip report generation
    --notify                   Send notifications on completion
    --help                     Show this help message

Examples:
    $0 --browser chrome --suite smoke --parallel
    $0 -b firefox -e staging -s regression -r 2
    $0 --suite "LoginTest" --headless --notify
    $0 --environment dev --parallel --timeout 600

EOF
}

# Parse command line arguments
parse_arguments() {
    while [[ $# -gt 0 ]]; do
        case $1 in
            -b|--browser)
                BROWSER="$2"
                shift 2
                ;;
            -e|--environment)
                ENVIRONMENT="$2"
                shift 2
                ;;
            -s|--suite)
                TEST_SUITE="$2"
                shift 2
                ;;
            -p|--parallel)
                PARALLEL="true"
                shift
                ;;
            -h|--headless)
                HEADLESS="true"
                shift
                ;;
            -r|--retry)
                RETRY_COUNT="$2"
                shift 2
                ;;
            -t|--timeout)
                TIMEOUT="$2"
                shift 2
                ;;
            --no-report)
                GENERATE_REPORT="false"
                shift
                ;;
            --notify)
                SEND_NOTIFICATION="true"
                shift
                ;;
            --help)
                show_help
                exit 0
                ;;
            *)
                error "Unknown option: $1"
                show_help
                exit 1
                ;;
        esac
    done
}

# Validate arguments
validate_arguments() {
    log "Validating arguments..."
    
    # Validate browser
    case $BROWSER in
        chrome|firefox|edge) ;;
        *) error "Invalid browser: $BROWSER"; exit 1 ;;
    esac
    
    # Validate environment
    case $ENVIRONMENT in
        dev|staging|production) ;;
        *) error "Invalid environment: $ENVIRONMENT"; exit 1 ;;
    esac
    
    # Validate retry count
    if ! [[ "$RETRY_COUNT" =~ ^[0-9]+$ ]] || [ "$RETRY_COUNT" -lt 0 ] || [ "$RETRY_COUNT" -gt 5 ]; then
        error "Invalid retry count: $RETRY_COUNT (must be 0-5)"
        exit 1
    fi
    
    # Validate timeout
    if ! [[ "$TIMEOUT" =~ ^[0-9]+$ ]] || [ "$TIMEOUT" -lt 60 ]; then
        error "Invalid timeout: $TIMEOUT (minimum 60 seconds)"
        exit 1
    fi
    
    success "Arguments validated"
}

# Pre-execution checks
pre_execution_checks() {
    log "Running pre-execution checks..."
    
    # Check if Maven project exists
    if [ ! -f "pom.xml" ]; then
        error "pom.xml not found. Are you in the correct directory?"
        exit 1
    fi
    
    # Check if TestNG configuration exists
    if [ ! -f "testng.xml" ]; then
        error "testng.xml not found"
        exit 1
    fi
    
    # Create necessary directories
    mkdir -p target/screenshots target/logs src/test/java/utils/reports
    
    # Set up environment variables
    export BROWSER ENVIRONMENT HEADLESS
    export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
    
    success "Pre-execution checks completed"
}

# Build Maven command
build_maven_command() {
    local mvn_cmd="mvn test"
    
    # Add browser parameter
    mvn_cmd="$mvn_cmd -Dbrowser=$BROWSER"
    
    # Add environment parameter
    mvn_cmd="$mvn_cmd -Denvironment=$ENVIRONMENT"
    
    # Add headless parameter
    if [ "$HEADLESS" = "true" ]; then
        mvn_cmd="$mvn_cmd -Dheadless=true"
    fi
    
    # Add parallel execution
    if [ "$PARALLEL" = "true" ]; then
        mvn_cmd="$mvn_cmd -DthreadCount=3 -Dparallel=methods"
    fi
    
    # Add test suite/class
    if [ "$TEST_SUITE" != "all" ]; then
        if [[ "$TEST_SUITE" == *"Test"* ]]; then
            # Specific test class
            mvn_cmd="$mvn_cmd -Dtest=$TEST_SUITE"
        else
            # Test suite
            mvn_cmd="$mvn_cmd -Dsuite=$TEST_SUITE"
        fi
    fi
    
    # Add timeout
    mvn_cmd="$mvn_cmd -Dsurefire.timeout=$TIMEOUT"
    
    # Always ignore test failures for proper reporting
    mvn_cmd="$mvn_cmd -Dmaven.test.failure.ignore=true"
    
    echo "$mvn_cmd"
}

# Execute tests with retry logic
execute_tests() {
    local mvn_cmd
    mvn_cmd=$(build_maven_command)
    
    info "Test Configuration:"
    info "  Browser: $BROWSER"
    info "  Environment: $ENVIRONMENT"
    info "  Test Suite: $TEST_SUITE"
    info "  Parallel: $PARALLEL"
    info "  Headless: $HEADLESS"
    info "  Retry Count: $RETRY_COUNT"
    info "  Timeout: ${TIMEOUT}s"
    
    log "Executing command: $mvn_cmd"
    
    local attempt=1
    local max_attempts=$((RETRY_COUNT + 1))
    local test_passed=false
    
    while [ $attempt -le $max_attempts ] && [ "$test_passed" = false ]; do
        if [ $attempt -gt 1 ]; then
            warning "Retry attempt $((attempt - 1)) of $RETRY_COUNT"
            sleep 10
        fi
        
        log "Test attempt $attempt of $max_attempts"
        
        # Record start time
        local start_time=$(date +%s)
        
        # Execute tests
        if eval "$mvn_cmd"; then
            local end_time=$(date +%s)
            local duration=$((end_time - start_time))
            success "Tests completed successfully in ${duration}s (attempt $attempt)"
            test_passed=true
        else
            local end_time=$(date +%s)
            local duration=$((end_time - start_time))
            error "Tests failed in ${duration}s (attempt $attempt)"
            
            if [ $attempt -lt $max_attempts ]; then
                warning "Retrying failed tests..."
                # Add retry-specific parameters for next attempt
                mvn_cmd="$mvn_cmd -Dsurefire.rerunFailingTestsCount=1"
            fi
        fi
        
        attempt=$((attempt + 1))
    done
    
    if [ "$test_passed" = false ]; then
        error "All test attempts failed"
        return 1
    fi
    
    return 0
}

# Parse test results
parse_test_results() {
    log "Parsing test results..."
    
    local results_file="target/test-results-summary.txt"
    
    if [ -f target/surefire-reports/TEST-*.xml ]; then
        local total_tests=0
        local failed_tests=0
        local error_tests=0
        local skipped_tests=0
        
        # Parse XML reports
        total_tests=$(grep -h "tests=" target/surefire-reports/TEST-*.xml | sed 's/.*tests="\([0-9]*\)".*/\1/' | awk '{sum+=$1} END {print sum+0}')
        failed_tests=$(grep -h "failures=" target/surefire-reports/TEST-*.xml | sed 's/.*failures="\([0-9]*\)".*/\1/' | awk '{sum+=$1} END {print sum+0}')
        error_tests=$(grep -h "errors=" target/surefire-reports/TEST-*.xml | sed 's/.*errors="\([0-9]*\)".*/\1/' | awk '{sum+=$1} END {print sum+0}')
        skipped_tests=$(grep -h "skipped=" target/surefire-reports/TEST-*.xml | sed 's/.*skipped="\([0-9]*\)".*/\1/' | awk '{sum+=$1} END {print sum+0}')
        
        local passed_tests=$((total_tests - failed_tests - error_tests - skipped_tests))
        local success_rate=0
        
        if [ $total_tests -gt 0 ]; then
            success_rate=$(( (passed_tests * 100) / total_tests ))
        fi
        
        # Create summary
        cat > "$results_file" << EOF
Test Execution Summary
=====================
Date: $(date)
Browser: $BROWSER
Environment: $ENVIRONMENT
Test Suite: $TEST_SUITE

Results:
- Total Tests: $total_tests
- Passed: $passed_tests
- Failed: $failed_tests
- Errors: $error_tests
- Skipped: $skipped_tests
- Success Rate: ${success_rate}%

Status: $([ $((failed_tests + error_tests)) -eq 0 ] && echo "✅ PASSED" || echo "❌ FAILED")
EOF
        
        # Display summary
        info "Test Results Summary:"
        info "  Total Tests: $total_tests"
        info "  Passed: $passed_tests"
        info "  Failed: $failed_tests"
        info "  Errors: $error_tests"
        info "  Skipped: $skipped_tests"
        info "  Success Rate: ${success_rate}%"
        
        if [ $((failed_tests + error_tests)) -eq 0 ]; then
            success "All tests passed!"
            return 0
        else
            error "Some tests failed"
            return 1
        fi
    else
        error "No test results found"
        return 1
    fi
}

# Generate reports
generate_reports() {
    if [ "$GENERATE_REPORT" = "true" ]; then
        log "Generating test reports..."
        
        # Check if ExtentReports HTML report exists
        if [ -f "src/test/java/utils/reports/UI_test_report.html" ]; then
            success "ExtentReports HTML report generated"
            info "Report location: src/test/java/utils/reports/UI_test_report.html"
        else
            warning "ExtentReports HTML report not found"
        fi
        
        # Create consolidated report directory
        local report_dir="target/reports"
        mkdir -p "$report_dir"
        
        # Copy all reports to consolidated location
        if [ -f "src/test/java/utils/reports/UI_test_report.html" ]; then
            cp "src/test/java/utils/reports/UI_test_report.html" "$report_dir/"
        fi
        
        # Copy Surefire reports
        if [ -d "target/surefire-reports" ]; then
            cp -r target/surefire-reports "$report_dir/"
        fi
        
        # Copy screenshots if any
        if [ -d "target/screenshots" ] && [ "$(ls -A target/screenshots)" ]; then
            cp -r target/screenshots "$report_dir/"
            info "Screenshots copied to reports directory"
        fi
        
        # Create index.html for easy navigation
        cat > "$report_dir/index.html" << 'EOF'
<!DOCTYPE html>
<html>
<head>
    <title>Test Reports</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .header { background: #f4f4f4; padding: 20px; border-radius: 5px; }
        .report-link { display: block; padding: 10px; margin: 10px 0; background: #e7f3ff; text-decoration: none; border-radius: 3px; }
        .report-link:hover { background: #d1ecf1; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Test Execution Reports</h1>
        <p>Generated on: <script>document.write(new Date().toLocaleString());</script></p>
    </div>
    
    <h2>Available Reports</h2>
    <a href="UI_test_report.html" class="report-link">📊 ExtentReports - Detailed Test Report</a>
    <a href="surefire-reports/index.html" class="report-link">📋 Surefire Reports - JUnit Style</a>
    <a href="screenshots/" class="report-link">📸 Screenshots (if available)</a>
    
    <h2>Quick Links</h2>
    <a href="../test-results-summary.txt" class="report-link">📄 Test Summary</a>
</body>
</html>
EOF
        
        success "Consolidated reports generated in: $report_dir"
    else
        info "Report generation skipped"
    fi
}

# Send notifications
send_notification() {
    if [ "$SEND_NOTIFICATION" = "true" ]; then
        log "Sending notifications..."
        
        local status_icon="✅"
        local status_text="PASSED"
        
        if [ -f "target/test-results-summary.txt" ]; then
            if grep -q "❌ FAILED" "target/test-results-summary.txt"; then
                status_icon="❌"
                status_text="FAILED"
            fi
        fi
        
        local message="Test execution completed: $status_icon $status_text
Browser: $BROWSER
Environment: $ENVIRONMENT  
Suite: $TEST_SUITE
Time: $(date)"
        
        # Slack notification (if webhook URL is available)
        if [ -n "$SLACK_WEBHOOK_URL" ]; then
            curl -X POST -H 'Content-type: application/json' \
                --data "{\"text\":\"$message\"}" \
                "$SLACK_WEBHOOK_URL" > /dev/null 2>&1 && \
                success "Slack notification sent" || \
                warning "Failed to send Slack notification"
        fi
        
        # Teams notification (if webhook URL is available)
        if [ -n "$TEAMS_WEBHOOK_URL" ]; then
            curl -X POST -H 'Content-type: application/json' \
                --data "{\"text\":\"$message\"}" \
                "$TEAMS_WEBHOOK_URL" > /dev/null 2>&1 && \
                success "Teams notification sent" || \
                warning "Failed to send Teams notification"
        fi
        
        # Email notification (if configured)
        if command -v mail >/dev/null 2>&1 && [ -n "$EMAIL_RECIPIENTS" ]; then
            echo "$message" | mail -s "Test Execution Results: $status_text" "$EMAIL_RECIPIENTS" && \
                success "Email notification sent" || \
                warning "Failed to send email notification"
        fi
        
        if [ -z "$SLACK_WEBHOOK_URL" ] && [ -z "$TEAMS_WEBHOOK_URL" ] && [ -z "$EMAIL_RECIPIENTS" ]; then
            info "No notification endpoints configured"
        fi
    else
        info "Notifications disabled"
    fi
}

# Cleanup function
cleanup() {
    log "Cleaning up..."
    
    # Kill any remaining browser processes
    pkill -f "chrome\|firefox\|edge" 2>/dev/null || true
    
    # Kill Xvfb if running
    if [ -f "/tmp/xvfb.pid" ]; then
        local xvfb_pid=$(cat /tmp/xvfb.pid)
        kill "$xvfb_pid" 2>/dev/null || true
        rm -f /tmp/xvfb.pid
    fi
    
    # Archive logs
    if [ -d "target/logs" ] && [ "$(ls -A target/logs)" ]; then
        tar -czf "target/logs-$(date +%Y%m%d-%H%M%S).tar.gz" target/logs/
        success "Logs archived"
    fi
}

# Trap cleanup on exit
trap cleanup EXIT

# Print banner
print_banner() {
    cat << 'EOF'
╔══════════════════════════════════════════╗
║           Test Runner v2.0               ║
║     Advanced Selenium Test Execution    ║
╚══════════════════════════════════════════╝
EOF
}

# Main execution function
main() {
    print_banner
    
    parse_arguments "$@"
    validate_arguments
    pre_execution_checks
    
    # Start time tracking
    local start_time=$(date +%s)
    
    # Execute tests
    local test_result=0
    if execute_tests; then
        success "Test execution completed successfully"
    else
        error "Test execution failed"
        test_result=1
    fi
    
    # End time tracking
    local end_time=$(date +%s)
    local total_duration=$((end_time - start_time))
    local duration_formatted=$(printf '%02d:%02d:%02d' $((total_duration/3600)) $((total_duration%3600/60)) $((total_duration%60)))
    
    info "Total execution time: $duration_formatted"
    
    # Parse results and generate reports
    parse_test_results
    generate_reports
    send_notification
    
    # Final summary
    echo
    info "Execution Summary:"
    info "  Duration: $duration_formatted"
    info "  Browser: $BROWSER"
    info "  Environment: $ENVIRONMENT"
    info "  Suite: $TEST_SUITE"
    info "  Reports: $([ "$GENERATE_REPORT" = "true" ] && echo "Generated" || echo "Skipped")"
    info "  Notifications: $([ "$SEND_NOTIFICATION" = "true" ] && echo "Sent" || echo "Disabled")"
    
    if [ $test_result -eq 0 ]; then
        success "All tests completed successfully! 🎉"
    else
        error "Some tests failed. Check the reports for details."
    fi
    
    exit $test_result
}

# Run main function with all arguments
main "$@"
