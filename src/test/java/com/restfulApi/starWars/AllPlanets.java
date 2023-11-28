package com.restfulApi.starWars;

import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;

@SerenityTest
public class AllPlanets extends StarWars_SetupClass {
    @Test
    void listAllPlanets() {
        // Gezegen isimlerini içeren bir liste oluşturuyoruz
        List<String> listOfPlanetNames = new ArrayList<>(Arrays.asList("Tatooine", "Alderaan", "Yavin IV", "Hoth", "Dagobah", "Bespin", "Endor", "Naboo", "Coruscant", "Kamino"));
        // Star Wars API'sinden tüm gezegenleri getiriyoruz
        SerenityRest
                .given()
                    .accept("application/json")
                    .log().uri()
                .get("/planets")
                .then()
                    .statusCode(200);
        Ensure.that("verify planet names are correct : " + listOfPlanetNames, vRES -> vRES.body("results.name", is(listOfPlanetNames)));
        Ensure.that("verify planet size is 10: " + listOfPlanetNames, vRES -> vRES.body("results.name", Matchers.hasSize(10)));
        Ensure.that("header Connection is keep-alive", vRES -> vRES.header("Connection", is("keep-alive")));
    }
}
