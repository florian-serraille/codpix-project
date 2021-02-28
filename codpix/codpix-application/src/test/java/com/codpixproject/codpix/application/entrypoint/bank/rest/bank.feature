Feature: Bank registration resource

  Scenario: Create a bank

    Given url 'http://localhost:8080/api/v1/bank'
    And request { institutionCode: '001', name: 'Banco do Brasil' }
    When method post
    Then status 201
    And match response == { code: '#notnull', institutionCode: '001', name: 'Banco do Brasil'}


  Scenario: Try to create a bank already registered

    Given url 'http://localhost:8080/api/v1/bank'
    And request { institutionCode: '001', name: 'Banco do Brasil' }
    When method post
    Then status 409