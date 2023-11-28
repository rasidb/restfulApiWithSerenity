package com.restfulApi.starWars;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class StarWars_SetupClass {
    @BeforeAll
    static void setup(){
        RestAssured.baseURI="https://swapi.dev";
        RestAssured.basePath="/api";
    }
}
