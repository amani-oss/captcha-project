let currentCaptchaId = ""; // متغير لتخزين المعرف الفريد

// 1. وظيفة جلب كابتشا جديدة
async function loadCaptcha() {
    const response = await fetch("http://localhost:8080/captcha/new");
    const data = await response.json();
    
    currentCaptchaId = data.captchaId; // تخزين الـ ID
    // تحديث رابط الصورة باستخدام الـ ID الجديد
    document.getElementById('captcha-image').src = "http://localhost:8080" + data.imageUrl;
}

// 2. وظيفة التحقق (عند الضغط على الزر)
async function verifyCaptcha() {
    const userInput = document.getElementById('captcha-input').value;

    const response = await fetch("http://localhost:8080/captcha/verify", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            captchaId: currentCaptchaId, // إرسال الـ ID المخزن
            captchaCode: userInput       // إرسال ما كتبه المستخدم
        })
    });

    const result = await response.json();

    if (result.success) {
        alert("✅ أحسنت! تم التحقق بنجاح.");
    } else {
        alert("❌ الكود غير صحيح، جربي مرة أخرى.");
        loadCaptcha(); // تحديث الكابتشا تلقائياً عند الخطأ
    }
}
