package com.RestfulApi.jsonPlaceHolder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class MyfirsTest {
    @BeforeAll
    static void init() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

    @DisplayName("basic get method and using pathparam")  //testimiz hakkında genel bilgi
    @ParameterizedTest //birden fazla test yapacağımız için @parameterizedTest anotasyonu kullandık
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
        //@ValueSource içinde bulunan obje sayısı kadar testlerimiz yapılacak
    void getMethod(int num) {  //int num objesi valueSource anotasyonu içinde yazdığımız objeleri işaret eder kaçıncı testte olduğumuza göre int değeri değişir
        Response response =// response objesi rest-assured classından geliyor
            given()
                    .accept(ContentType.JSON) //response body'nin hangi formatta olacağını belirliyoruz
                    .log().uri() //request url görmemize yarar
                    .pathParam("id", num) //id isimli bir path param oluşturur get methoduyla birlikte kullanılırsa request url'ye eklenir
            .when()
            .get("posts/{id}")// GET methodu göndereceğimizi ve id pathini kullanacağımızı belirttik bu methoddan sonra request gönderilir.
            .then()
                    .statusCode(200) //status code'nin 200 olduğunu doğrula
                    .contentType(ContentType.JSON) //response body'nin json formatında olduğunu doğrula
                    .body("id", is(num)) //body içinde bulunan id key'ine karşılık value'nin num olduğunu assert et (is() methodu matchers classından geliyor)
                    .extract()
                    .response();
        response.prettyPrint(); //response body'nin çıktısını al
    }

    /**
     *
     * @param title
     * @param body
     * @param userId
     */
    @DisplayName("example post method using csvFileSource annotation")
    @ParameterizedTest()
    @CsvFileSource(resources = "/jsonPlaceHolder/jsonPlaceHolderBody.csv", numLinesToSkip = 1) //hazır csv dosyası kullanarak testi yap ilk satırı atla
    void postMethod(String title, String body, int userId) {
        Map<String, Object> reqBody = new HashMap<>(); //request body için Map classından obje oluşturduk hata almamak için jackson bağımlılığını kurmak lazım
        reqBody.put("title", title);
        reqBody.put("body", body);
        reqBody.put("userId", userId); //request body oluşturuldu
        given()
                .accept("application/json") //response json formatında olsun
                .contentType(ContentType.JSON) //request body'nin formati json
                .body(reqBody) //request body'miz
        .when()
                .post("/posts")//body içerdiği için post methodunu kullandık
        .then()
                .statusCode(201)//201 status code başarıyla oluşturuldu
                .body("title", is(title))//body'nin title keyi ile csv dosyasındaki title'in aynı olduğunu doğrula
                .body("body", is(body))//body'nin body keyi le csv dosyasının body'sinin aynı olduğunu doğrula
                .body("userId", is(userId))//body'nin userId keyi ile csv dosyasının userId'sinin aynı olduğunu doğrula
        .extract()
                .response()//response objesine çevir
                .prettyPrint();//konsola response yazdır
    }

}
