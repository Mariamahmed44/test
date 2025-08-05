#!/bin/bash

# Test Environment Setup Script
# This script prepares the environment for test execution

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Logging function
log() {
    echo -e "${BLUE}[$(date +'%Y-%m-%d %H:%M:%S')] $1${NC}"
}

error() {
    echo -e "${RED}[ERROR] $1${NC}" >&2
}

warning() {
    echo -e "${YELLOW}[WARNING] $1${NC}"
}

success() {
    echo -e "${GREEN}[SUCCESS] $1${NC}"
}

# Check if running in CI environment
is_ci() {
    [ -n "$CI" ] || [ -n "$GITHUB_ACTIONS" ] || [ -n "$JENKINS_URL" ]
}

# Check system requirements
check_requirements() {
    log "Checking system requirements..."
    
    # Check Java
    if command -v java >/dev/null 2>&1; then
        JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1-2)
        success "Java found: Version $JAVA_VERSION"
        
        # Check if Java 11 or higher
        if [[ $(echo "$JAVA_VERSION >= 11" | bc -l 2>/dev/null || echo "0") -eq 1 ]]; then
            success "Java version is compatible"
        else
            error "Java 11 or higher is required. Current version: $JAVA_VERSION"
            exit 1
        fi
    else
        error "Java is not installed"
        exit 1
    fi
    
    # Check Maven
    if command -v mvn >/dev/null 2>&1; then
        MAVEN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
        success "Maven found: Version $MAVEN_VERSION"
    else
        error "Maven is not installed"
        exit 1
    fi
    
    # Check browsers (only in CI or if requested)
    if is_ci || [ "$CHECK_BROWSERS" = "true" ]; then
        check_browsers
    fi
}

# Check browser installations
check_browsers() {
    log "Checking browser installations..."
    
    # Check Chrome
    if command -v google-chrome >/dev/null 2>&1; then
        CHROME_VERSION=$(google-chrome --version | awk '{print $3}')
        success "Chrome found: Version $CHROME_VERSION"
    elif command -v chromium-browser >/dev/null 2>&1; then
        CHROME_VERSION=$(chromium-browser --version | awk '{print $2}')
        success "Chromium found: Version $CHROME_VERSION"
    else
        warning "Chrome/Chromium not found"
    fi
    
    # Check Firefox
    if command -v firefox >/dev/null 2>&1; then
        FIREFOX_VERSION=$(firefox --version | awk '{print $3}')
        success "Firefox found: Version $FIREFOX_VERSION"
    else
        warning "Firefox not found"
    fi
    
    # Check if Xvfb is available (for headless execution)
    if command -v xvfb-run >/dev/null 2>&1; then
        success "Xvfb found - headless execution supported"
    else
        warning "Xvfb not found - headless execution may not work"
    fi
}

# Setup virtual display for headless execution
setup_virtual_display() {
    if is_ci || [ "$HEADLESS" = "true" ]; then
        log "Setting up virtual display..."
        
        if command -v Xvfb >/dev/null 2>&1; then
            export DISPLAY=:99
            Xvfb :99 -screen 0 1920x1080x24 > /dev/null 2>&1 &
            XVFB_PID=$!
            sleep 3
            
            # Save PID for cleanup
            echo $XVFB_PID > /tmp/xvfb.pid
            success "Virtual display started (PID: $XVFB_PID)"
        else
            error "Xvfb not available for virtual display"
            exit 1
        fi
    fi
}

# Create necessary directories
setup_directories() {
    log "Setting up directories..."
    
    # Create screenshots directory
    mkdir -p target/screenshots
    success "Screenshots directory created"
    
    # Create reports directory
    mkdir -p src/test/java/utils/reports
    success "Reports directory created"
    
    # Create logs directory
    mkdir -p target/logs
    success "Logs directory created"
    
    # Set permissions
    chmod -R 755 target/
    chmod -R 755 src/test/java/utils/reports/
}

# Download and cache dependencies
setup_dependencies() {
    log "Setting up Maven dependencies..."
    
    # Download dependencies offline
    mvn dependency:go-offline -B -q
    success "Dependencies downloaded"
    
    # Compile project
    log "Compiling project..."
    mvn compile test-compile -B -q
    success "Project compiled successfully"
}

# Setup environment variables
setup_environment() {
    log "Setting up environment variables..."
    
    # Set default browser if not specified
    export BROWSER=${BROWSER:-chrome}
    export HEADLESS=${HEADLESS:-true}
    export ENVIRONMENT=${ENVIRONMENT:-production}
    
    # Set Maven options
    export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
    
    # Set test timeout
    export TEST_TIMEOUT=${TEST_TIMEOUT:-300}
    
    success "Environment variables configured"
    success "Browser: $BROWSER"
    success "Headless: $HEADLESS"
    success "Environment: $ENVIRONMENT"
}

# Validate test configuration
validate_configuration() {
    log "Validating test configuration..."
    
    # Check if testng.xml exists
    if [ -f "testng.xml" ]; then
        success "TestNG configuration found"
    else
        error "testng.xml not found"
        exit 1
    fi
    
    # Check if pom.xml exists
    if [ -f "pom.xml" ]; then
        success "Maven configuration found"
    else
        error "pom.xml not found"
        exit 1
    fi
    
    # Validate Maven project
    mvn validate -B -q
    success "Maven project validated"
}

# Cleanup function
cleanup() {
    log "Cleaning up..."
    
    # Kill Xvfb if it was started
    if [ -f "/tmp/xvfb.pid" ]; then
        XVFB_PID=$(cat /tmp/xvfb.pid)
        if kill -0 $XVFB_PID 2>/dev/null; then
            kill $XVFB_PID
            success "Virtual display stopped"
        fi
        rm -f /tmp/xvfb.pid
    fi
}

# Trap cleanup on exit
trap cleanup EXIT

# Main execution
main() {
    log "Starting test environment setup..."
    
    check_requirements
    setup_environment
    setup_directories
    setup_dependencies
    validate_configuration
    setup_virtual_display
    
    success "Test environment setup completed successfully!"
    
    # Print summary
    echo
    log "Environment Summary:"
    echo "  Java Version: $(java -version 2>&1 | head -n 1)"
    echo "  Maven Version: $(mvn -version | head -n 1)"
    echo "  Browser: $BROWSER"
    echo "  Headless Mode: $HEADLESS"
    echo "  Test Environment: $ENVIRONMENT"
    echo "  Display: ${DISPLAY:-not set}"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        --check-browsers)
            CHECK_BROWSERS=true
            shift
            ;;
        --headless)
            HEADLESS=true
            shift
            ;;
        --browser=*)
            BROWSER="${1#*=}"
            shift
            ;;
        --environment=*)
            ENVIRONMENT="${1#*=}"
            shift
            ;;
        --help)
            echo "Usage: $0 [options]"
            echo "Options:"
            echo "  --check-browsers    Check browser installations"
            echo "  --headless          Enable headless mode"
            echo "  --browser=BROWSER   Set browser (chrome, firefox, edge)"
            echo "  --environment=ENV   Set environment (dev, staging, production)"
            echo "  --help              Show this help message"
            exit 0
            ;;
        *)
            error "Unknown option: $1"
            exit 1
            ;;
    esac
done

# Run main function
main
