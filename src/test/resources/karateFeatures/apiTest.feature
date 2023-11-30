@karate
Feature: pet store api tests

  Scenario: basic test with status code validation
    Given url 'https://petstore.swagger.io/v2/store/inventory'
    When method GET
    Then status 200


  Scenario: header verification
    Given url 'https://petstore.swagger.io/v2/store/inventory'
    When method get
    Then status 200
    And match header Content-Type == 'application/json'
    And match header Connection == 'keep-alive'
    And match header Date == '#present'


  Scenario: json body verification
    Given url 'https://petstore.swagger.io/v2/store/inventory'
    When method get
    Then status 200
    And match header Content-Type == 'application/json'
    And print response
    And print response.Available
    And match response.Available == '#notpresent'
    And match response.roma == '#notpresent'
    And print "patates kızartması"

 Scenario: fdgfd
   Given print "rasid"