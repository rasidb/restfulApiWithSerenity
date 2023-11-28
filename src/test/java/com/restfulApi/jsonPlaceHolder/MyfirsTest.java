package com.restfulApi.jsonPlaceHolder;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;


import io.restassured.response.Response;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SerenityTest
class MyfirsTest {
    @BeforeEach
    void init() {
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
        Ensure.that(" id number",vRes-> vRes.body("id",is(num)));   }

    @DisplayName("example post method using csvFileSource annotation")
    @ParameterizedTest()
    @CsvFileSource(resources = "/jsonPlaceHolder/jsonPlaceHolderBody.csv", numLinesToSkip = 1)
//hazır csv dosyası kullanarak testi yap ilk satırı atla
    void postMethod(String title, String body, int userId, int id) {
        final Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title", title);
        reqBody.put("body", body);
        reqBody.put("userId", userId);

        Map response =
                given()
                        .accept("application/json")
                        .contentType(ContentType.JSON)
                        .body(reqBody)
                        .when()
                        .post("/posts")
                        .then()
                        .statusCode(201)
                        .extract()
                        .as(Map.class);

        response.remove("id");


  }

    @DisplayName("basic put method with pojo class")
    @ParameterizedTest()
    @CsvFileSource(resources = "/jsonPlaceHolder/jsonPlaceHolderBody.csv", numLinesToSkip = 1)
    void putMethod(String title, String body, int userId, int id) {
        JsonPlaceHolderPojo pojo = new JsonPlaceHolderPojo(); //JsonPlaceHolderPojo classından objeeyi oluşturduk
        pojo.setBody(body);
        pojo.setTitle(title);
        pojo.setUserId(userId);
        pojo.setId(id);
        // csv dosyamızda bulunan veriler ile her test için yeniden pojo dosyamızı ayarladık
        JsonPlaceHolderPojo response = given()
                .accept(ContentType.JSON) //response json formatında olsun
                .contentType(ContentType.JSON) //request body json formatında
                .pathParam("id", id)
                .body(pojo) //body olarak pojo objesini koyduk
                .log().body() //requestBody yazdır
                .put("users/{id}") // users/{id} pathini ekle ve put request gönder
                .then()
                .statusCode(200) //status code 200 olduğunu doğrula
                .time(lessThan(6000L)) // value değeri kadar milisaniyeden kısa olduğunu assert et
                .extract()
                .as(JsonPlaceHolderPojo.class); //respone'ı pojo objesine dönüştür
        assertEquals(pojo.toString(), response.toString()); //response body'nin request body'e eşit olduğunu doğrula
    }

    @DisplayName("basic get method and jsonSchema assertion")
    @ParameterizedTest()
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void singleUserGetMethodSchema(int id) {
     given()
                .accept("application/json")
                .pathParam("id", id)
                .get("posts/{id}")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/restfulApi/jsonPlaceHolder/JsonPlaceHolderSingleUserSchema.json"))) //jsonSchema doğruluğunu kontrol et
                .statusCode(200);

    }
}
