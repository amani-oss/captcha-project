# -------------------
# Build stage
# -------------------
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# نسخ ملفات المشروع كلها
COPY . .

# منح صلاحية التنفيذ لملف gradlew وبناء المشروع بدون الاختبارات
RUN chmod +x gradlew && ./gradlew clean build -x test

# -------------------
# Runtime stage
# -------------------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# نسخ الـ JAR الناتج من مرحلة البناء
COPY --from=builder /app/build/libs/app.jar app.jar

# فتح المنفذ 8080 لتشغيل التطبيق
EXPOSE 8080

# الأمر لتشغيل التطبيق
CMD ["java", "-jar", "app.jar"]

