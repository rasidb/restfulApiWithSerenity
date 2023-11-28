package com.restfulApi.starWars;

import net.serenitybdd.rest.Ensure;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.is;

public class OnePlanet extends StarWars_SetupClass{
    @DisplayName("tek gezegen bilgilerini al ve yanıt süresini kontrol et")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void showOnePlanet(int id) {
        // Belirli bir gezegenin detaylarını almak için bir GET isteği yapın
        given()
                .accept("application/json")
                .pathParam("id", id)
                .get("/planets/{id}")
                .then()
                .statusCode(200);

        // İsteğin URI'sini kontrol et
        Ensure.that("URI is correct", vRes -> vRes.body("url", is("https://swapi.dev/api/planets/" + id + "/")));

        // İsteğin yanıt süresini kontrol et
        Ensure.that("Connection is too slow", vRES -> vRES.time(Matchers.greaterThan(600L)));
    }
}
