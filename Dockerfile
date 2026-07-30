ARG TARGETARCH

# Build Stage
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Install dependencies for Playwright (Chromium)
RUN apt-get update && apt-get install -y \
    libglib2.0-0 \
    libnss3 \
    libatk1.0-0 \
    libatk-bridge2.0-0 \
    libcups2 \
    libdrm2 \
    libxkbcommon0 \
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxrandr2 \
    libgbm1 \
    libasound2 \
    libpango-1.0-0 \
    libcairo2 \
    && rm -rf /var/lib/apt/lists/*

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle.properties ./

RUN chmod +x ./gradlew

COPY . .

RUN ./gradlew :site:dockerRuntime

# Runtime Stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/site/build/docker /app

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
    CMD wget -qO- http://localhost:8080/api/health || exit 1

# Environment Variables
ENV PORT=8080
ENV ASSETS_PATH=/app/assets

# Create assets directory
RUN mkdir -p /app/assets

ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS:-} -cp /app/lib/*:/app/app.jar xyz.malefic.guptarealty.server.MainKt"]
