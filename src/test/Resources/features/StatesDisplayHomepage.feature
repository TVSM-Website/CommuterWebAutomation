@stateDropDownVerification
Feature: Verify Selected State is Displayed on Vehicle Cards

  Scenario Outline: Verify state name along with ex-showroom price visibility for scooters, motorcycles, and mopeds
    Given navigate to the TVS Motor home page in "<environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When navigate to the "Our Products" page for state drop down
    And navigates through state drop down and check visibility for each vehicle type

    Examples:
      | environment |
      | PROD        |
