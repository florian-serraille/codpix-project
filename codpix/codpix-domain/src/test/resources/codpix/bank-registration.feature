Feature: Bank registration
  As a bank,
  In order to send and receive pix,
  I want to register in codpix

  Scenario: The Bank request for register in codpix
    Given A valid bank register request
    When Bank ask for registration
    Then Bank receive code