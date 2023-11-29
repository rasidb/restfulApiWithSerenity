package com.runners;

import com.intuit.karate.Runner;
import net.serenitybdd.junit5.SerenityTest;

@SerenityTest
public class CukesRunner {

    public static void main(String[] args) {
        Runner.path("classpath:karate")
                .tags("~@ignore") // ignore etiketli senaryoları atla
                .parallel(5); // 5 paralel iş parçacığı kullan
    }
}
