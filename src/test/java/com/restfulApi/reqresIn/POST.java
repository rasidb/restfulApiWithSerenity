package com.restfulApi.reqresIn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class POST extends ReqresIn_setupClass {
    @DisplayName("Kullanıcı Oluştur")
    @CsvFileSource(resources = "/reqresIn/singleUserCreateBody.csv",numLinesToSkip = 1)
    @ParameterizedTest
    void kullaniciOlustur(String name, String job) {
        // requestBody bir Map olarak hazırla
        Map<String, Object> istekGovdesi = new HashMap<>();
        istekGovdesi.put("name", name);
        istekGovdesi.put("job", job);

        // POST isteğini gerçekleştir ve yanıtı doğrula
        given()
                .accept("application/json")
                .contentType("application/json")
                .body(istekGovdesi)
                .when()
                .post("/users")
                .then()
                .statusCode(201) // Durum kodunun 201 (Oluşturuldu) olduğunu doğrula
                .body("ad", equalTo(name)) // Yanıtın gövdesindeki name ile beklenen name eşit olduğunu doğrula
                .body("meslek", is(job)); // Yanıtın gövdesindeki job ile beklenen job eşit olduğunu doğrula
    }

    @DisplayName("Successful Registration and Login")
    @ValueSource(strings = {"patates", "wooden", "spoon"})
    @ParameterizedTest
    void registerAndLoginSuccessfully(String password) {
        // İstek gövdesini bir Map olarak hazırla
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", password);

        // İstek gövdesini yazdır (opsiyonel)
        System.out.println(requestBody);

        // POST isteğini gerçekleştir, yanıtı al ve token'ı çıkar
        String token = given()
                .accept("application/json")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .log().uri() // URI'yi yazdır (opsiyonel)
                .post("/register")
                .then()
                .statusCode(200) // Durum kodunun 200 olduğunu doğrula
                .extract().jsonPath().get("token");

        // Token'ı yazdır (opsiyonel)
        System.out.println(token);
    }


}
