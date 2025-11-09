#!/bin/bash
# ---------------------------------------------
# Mikroservisleri Build ve Başlatma Betiği
# ---------------------------------------------

# Proje adları ve JAR yolları
SERVICES=(
    "order:./order/target/order-0.0.1-SNAPSHOT.jar"
    "product:./product/target/product-0.0.1-SNAPSHOT.jar"
    "user:./user/target/user-0.0.1-SNAPSHOT.jar"
    "auth:./auth/target/auth-0.0.1-SNAPSHOT.jar"
    "notification:./notification/target/notification-0.0.1-SNAPSHOT.jar"
    "inventory:./inventory/target/inventory-0.0.1-SNAPSHOT.jar"
    "gateway:./gateway/target/gateway-0.0.1-SNAPSHOT.jar"
)

mkdir -p logs
rm -f logs/*.log

echo "=========================================="
echo "BUILD BAŞLANIYOR..."
echo "=========================================="

# Her servis için build yap
for SERVICE in "${SERVICES[@]}"; do
    IFS=':' read -r NAME JAR_PATH <<< "$SERVICE"
    SERVICE_DIR="./${NAME}"

    if [ -d "$SERVICE_DIR" ]; then
        echo "📦 ${NAME} build ediliyor..."
        cd "$SERVICE_DIR"
        mvn clean package -DskipTests
        cd ..
        echo "✓ ${NAME} build tamamlandı"
    else
        echo "❌ ${NAME} klasörü bulunamadı"
    fi
done

echo ""
echo "=========================================="
echo "SERVISLER BAŞLATILIYOR..."
echo "=========================================="

start_service() {
    SERVICE_NAME=$1
    JAR_PATH=$2
    LOG_FILE="logs/${SERVICE_NAME}.log"

    if [ ! -f "$JAR_PATH" ]; then
        echo "❌ HATA: ${SERVICE_NAME} JAR dosyası bulunamadı: ${JAR_PATH}"
        return 1
    fi

    echo "▶️ ${SERVICE_NAME} başlatılıyor... (Log: ${LOG_FILE})"
    nohup java -jar "$JAR_PATH" > "$LOG_FILE" 2>&1 &
    echo "   PID: $!"
    sleep 1
}

# Tüm servisleri başlat
for SERVICE in "${SERVICES[@]}"; do
    IFS=':' read -r NAME JAR_PATH <<< "$SERVICE"
    SERVICE_NAME=$(echo "${NAME}" | sed 's/.*/\u&/')-Service
    start_service "$SERVICE_NAME" "$JAR_PATH"
done

echo ""
echo "=========================================="
echo "Tüm servisler başlatıldı!"
echo "=========================================="
sleep 3

echo "Çalışan Java işlemleri:"
pgrep -f "java -jar" | xargs -r ps -f -o pid,user,cmd

echo ""
echo "Log dosyalarını kontrol etmek için:"
echo "  tail -f logs/Order-Service.log"
echo "  tail -f logs/Gateway-Service.log"
echo "=========================================="