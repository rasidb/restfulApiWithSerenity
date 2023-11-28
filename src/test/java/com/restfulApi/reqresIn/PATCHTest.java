package com.restfulApi.reqresIn;





import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.is;

class PATCHTest extends ReqresIn_setupClass {
    @ValueSource(ints = {1, 2, 3})
    @ParameterizedTest
    void test(int id) {
        Map<String,Object> reqBody= new HashMap<String, Object>();
        reqBody.put("name","raşid");
        given()
                .accept("application/json")
                .contentType("application/json")
                .pathParam("id",id)
                .body(reqBody)
                .when()
                .patch("/users/{id}")
                .then()
                .statusCode(200)
                .body("name",is(reqBody.get("name")));
    }
}
