Bu proje bir test çözme platformudur.
Backend olarak Spring Boot, frontend olarak HTML/JS ve Bootstrap kullanılmıştır.

KULLANILAN TEKNOLOJİLER
- Java 21
- Spring Boot (Spring Data JPA, Spring Security)
- H2 In-Memory Database
- HTML, JavaScript, Bootstrap 5
- localStorage (tarayıcı tarafında cache)

PROJE YAPISI
src/main/java/com/dopingProject
- config
    - DataInitializer.java: Örnek öğrenci, test ve soru verilerini oluşturur.
    - SecurityConfig.java
- controller
    - QuestionController.java
    - StudentController.java
    - StudentTestController.java
    - TestController.java
- dto
    - FinishTestRequest.java: test sonuçlarını gönderme DTO’su
    - StudentLoginResponse.java: login olduktan sonra şifreyi içermeyen DTO
- model
    - Question.java, Student.java, StudentTest.java, Test.java
- repository
    - Spring Data JPA repository interface’leri
- service ve service/impl
    - Servis interface ve implementasyonları

src/main/resources/static
- index.html
- login.html
- register.html
- main.html
- tests.html
- solve.html
- results.html

UYGULAMA NASIL ÇALIŞIR
- Spring Boot projesini terminalden şu komutla başlat:
  ./mvnw spring-boot:run
- Ardından uygulama http://localhost:8080 adresinde çalışır.
- H2 Console için http://localhost:8080/h2-console adresine git.
  JDBC URL: jdbc:h2:mem:testdb

BACKEND İŞLEYİŞİ
- Öğrenci kayıt olurken şifre BCrypt ile hash’lenir.
- Login olduktan sonra localStorage’a studentId kaydedilir.
- Öğrenci test listesinden bir test başlatır. Bu test student_tests tablosuna kaydedilir (enroll).
- Sorular ilk başta backend’den çekilir ve localStorage’da test_{testId} olarak saklanır.
- Ardından sorular sayfa sayfa gösterilir (ileri / geri tuşları ile).
- En sonunda test gönderilir. Backend doğru, yanlış ve boş sayıları hesaplar.
- Skor formülü:
  skor = doğru * 1.0 - yanlış * 0.33 + boş * 0

SONUÇLAR
- Öğrencinin çözmüş olduğu tüm testler results.html sayfasında listelenir.
- Doğru, yanlış, skor ve çözülme zamanı gösterilir.

DTO KULLANIMI
- Sadece gerekli yerlerde DTO kullanıldı. Örneğin:
    - StudentLoginResponse: login sonrası şifreyi gizler.
    - FinishTestRequest: sadece studentTestId ve answers JSON gelir.
- Diğer endpoint’lerde doğrudan Entity dönüldü. Bu hızlı geliştirme için tercih edildi.

LOCAL STORAGE İLE ÖZEL CACHE
- Sorular testi başlatınca tek istekle alınır ve localStorage’a kaydedilir.
- Sonra ileri geri yapılırken tekrar sunucuya istek atılmaz.

DEMO KULLANICILAR
Proje ilk açıldığında DataInitializer şu kullanıcıları ekler:
- ali@example.com / 1234
- ayse@example.com / 1234
Bu demo kullanıcıları ile giriş yapılarak uygulama kullanılabilir.
İstenirse register.html üzerinden kullanıcı hesabı oluşturulabilir.
