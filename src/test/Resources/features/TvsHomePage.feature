@HomePage
Feature: TVS motor HomePage Validations

  @tvshomepage
  Scenario: Verify the functionalities of home page
    Given navigate to the TVS Motor home page
    When a popup appears on the homepage close the pop up
    Then should close the pop up
    And verify the count of total vehicles

  @ourProducts
  Scenario: Verify functionalities of our products page
    Given navigate to our products page
    When clicks on each vehicles tab and select the state
    Then each image src has the same name as vehicle displayed on each card
    And  selected state should be updated on vehicle page


