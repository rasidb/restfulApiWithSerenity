package com.restfulApi.reqresIn;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

 class PUTTest extends ReqresIn_setupClass {
    @DisplayName("Kullanıcı Bilgilerini Güncelle")
    @CsvFileSource(resources = "/reqresIn/singleUserCreateBody.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void kullaniciBilgileriniGuncelle(String name, String job, String id) {
        // POJO (Plain Old Java Object) sınıfınızı kullanarak veriyi düzenleme
        ReqresIn_Pojo pojo = new ReqresIn_Pojo();
        pojo.setJob(job);
        pojo.setName(name);

        // PUT isteğini gerçekleştir ve yanıtı kontrol et
        given()
                .accept("application/json")
                .contentType("application/json")
                .pathParam("id", id)
                .body(pojo)
                .when()
                .put("users/{id}")
                .then()
                .statusCode(200) // Başarılı güncelleme durum kodunu kontrol et
                .body("name", is(name), "job", is(job)); // Yanıtın gövdesinde beklenen verileri kontrol et
    }
}
