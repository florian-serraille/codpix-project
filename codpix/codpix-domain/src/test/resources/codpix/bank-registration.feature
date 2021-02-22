Feature: Bank registration
  As a bank,
  In order to send and receive pix,
  I want to register in codpix

  Scenario: A new bank request for registration in codpix
    Given The valid bank BB
    When Bank BB ask for registration
    Then Bank receive code

  Scenario: An already existing bank request for registration in codpix

    Given The valid bank BB is already registered
    When Bank BB ask for registration
    Then Bank receive an error for for being already registered