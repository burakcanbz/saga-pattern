#!/bin/bash
# ---------------------------------------------
# Mikroservisleri Sonlandırma Betiği (Geliştirilmiş Sürüm)
# ---------------------------------------------

echo "Tüm çalışan Spring Boot servisleri aranıyor ve sonlandırılıyor..."

# Durdurulacak servislerin isim desenleri
# Örnek: (order|user|product|auth)
SERVICE_PATTERNS="(order|user|product|auth|notification|inventory|gateway)"

# Çalışan tüm Java işlemlerini (ps aux) listele, kendi betiğimizi (grep) dışla.
# JAR dosyası yürütenleri filtrele ve sadece servis desenlerimizle eşleşenleri al.
# awk ile PID (1. sütun) al.
# Tr komutu, boş satırları temizler, bu sayede kill komutu hata vermez.

PIDS=$(ps aux | grep "java -jar" | grep -E "$SERVICE_PATTERNS" | grep -v "grep" | awk '{print $2}' | tr '\n' ' ')

if [ -z "$PIDS" ]; then
    echo "✅ Şu anda çalışan Spring Boot servisi bulunamadı."
else
    echo "Aşağıdaki PID'ler sonlandırılıyor: $PIDS"
    # kill -9 ile tüm bulunan PID'lere sonlandırma komutu gönderiliyor.
    # $PIDS değişkenindeki tüm PID'ler tek bir kill komutuna argüman olarak verilir.
    kill -9 $PIDS

    echo "----------------------------------------------------"
    echo "✅ Tüm servisler başarıyla sonlandırma komutu aldı."
fi

# Betiğin sonuna, sonlandırmanın başarılı olup olmadığını kontrol etmek için
# kısa bir bekleme ve kontrol ekleyebiliriz:
sleep 2
echo "----------------------------------------------------"
echo "Kontrol:"
ps aux | grep "java -jar" | grep -E "$SERVICE_PATTERNS" | grep -v "grep"
echo "Eğer yukarıda PID görünmüyorsa, sonlandırma başarılıdır."