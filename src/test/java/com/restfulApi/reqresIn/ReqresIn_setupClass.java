package com.restfulApi.reqresIn;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class ReqresIn_setupClass {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/";
        RestAssured.basePath="/api/";
    }
}
