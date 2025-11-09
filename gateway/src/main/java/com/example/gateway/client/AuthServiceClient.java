//package com.example.gateway.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//// Auth Service'in adresini doğrudan burada kullanmıyoruz.
//// Statik yönlendirmede bile Feign, genellikle bir servis adını kullanır.
//// Eğer Feign'i statik adrese zorlamak isterseniz url="http://localhost:8084" kullanabilirsiniz.
//@FeignClient(name = "auth", url = "http://localhost:8084")
//public interface AuthServiceClient {
//
//    // Auth Service'teki /validate uç noktasını çağırır
//    // Yanıt olarak token'dan çıkarılan User ID'yi (String) bekliyoruz.
//    @GetMapping("/auth/validate")
//    String validateToken(@RequestParam("token") String token);
//}