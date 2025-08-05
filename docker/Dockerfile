# Multi-stage Dockerfile for Selenium Test Automation
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml first for better layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src
COPY testng.xml .

# Compile the project
RUN mvn compile test-compile

# Runtime stage
FROM eclipse-temurin:17-jre

# Install necessary packages
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    xvfb \
    dbus-x11 \
    && rm -rf /var/lib/apt/lists/*

# Install Chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Install Firefox
RUN apt-get update && apt-get install -y firefox \
    && rm -rf /var/lib/apt/lists/*

# Create app user
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Set working directory
WORKDIR /app

# Copy Maven installation from builder
COPY --from=builder /usr/share/maven /usr/share/maven
RUN ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Copy application files
COPY --from=builder /app .
COPY --chown=appuser:appuser . .

# Create necessary directories
RUN mkdir -p target/screenshots target/reports \
    && chown -R appuser:appuser /app

# Switch to app user
USER appuser

# Set environment variables
ENV DISPLAY=:99
ENV MAVEN_OPTS="-Xmx1024m"
ENV JAVA_OPTS="-Xmx1024m"

# Expose port for reports (if serving HTML reports)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/health || exit 1

# Default command
CMD ["sh", "-c", "Xvfb :99 -screen 0 1920x1080x24 & sleep 5 && mvn test -Dheadless=true"]

# Alternative commands for different test scenarios:
# For specific test class: docker run --rm test-automation mvn test -Dtest=LoginTest
# For different browser: docker run --rm -e BROWSER=firefox test-automation mvn test -Dbrowser=firefox
# For debugging: docker run --rm -it test-automation bash
