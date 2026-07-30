ARG TARGETARCH

# Build Stage
FROM --platform=$BUILDPLATFORM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Install dependencies for Playwright (Chromium)
RUN apt-get update && apt-get install -y \
    libglib2.0-0t64 \
    libnss3 \
    libatk1.0-0t64 \
    libatk-bridge2.0-0t64 \
    libcups2 \
    libdrm2 \
    libxkbcommon0 \
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxrandr2 \
    libgbm1 \
    libasound2t64 \
    libpango-1.0-0 \
    libcairo2 \
    libdbus-1-3 \
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

RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

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
