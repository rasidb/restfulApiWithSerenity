package com.restfulApi.reqresIn;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeAll;

public class ReqresIn_setupClass {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI="https://reqres.in/";
        RestAssured.basePath="/api/";
    }
}
