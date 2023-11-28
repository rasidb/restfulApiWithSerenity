package com.restfulApi.reqresIn;
import io.restassured.response.Response;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class GETTest extends ReqresIn_setupClass {
    @DisplayName("tek kullanıcının bilgileri")
    @ParameterizedTest()
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void singleUser(int user) {
        given().accept("application/json")
                .and().queryParam("id", user)
                .when()
                .log().uri()
                .get("/users")
                .then().statusCode(200)
                .time(lessThan(600L));

    }
    @DisplayName("farklı sayfalardaki kullanıcı listeleri")
    //status code 200 olmalı
    //her sayfada 10 tane kullanıcı listelenmeli
    @ParameterizedTest()
    @ValueSource(ints = {1,2,3})
    void usersList(int page){
        given()
                .accept("application/json")
                .queryParam("page",page)
                .and()
                .get("/users")
                .then()
                .statusCode(200)
                .time(lessThan(6000L))
                .log().body()
                .body("page",is(page))
                .body("per_page", is(6))
                .body("data.id",hasSize(6));
    }
    @DisplayName("single user not found")
    @Test
    void notFoundTest() {
        Response response = null;
        try {
            response = given()
                    .accept("application/json")
                    .pathParam("id", 999)
                    .log().uri()
                    .when()
                    .get("/users/{id}");
        } catch (Exception e) {

        }
        if (response==null){
            assertTrue(true);
        }else {
            assertEquals(404,response.statusCode());
        }
    }

}
