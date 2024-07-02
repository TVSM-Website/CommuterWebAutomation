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

  @scooters
  Scenario: Verify scooter vehicle pages on TVS Motor website
    Given navigate to the TVS Motor home page
    When navigate to the "Our Products" section
    Then user should see scooters listed with their respective names and 'Know More' links
    And clicks on 'Know More' for each scooter and verifies the title
  @motorcycles

  Scenario: verify each motorcycle page navigation from home page on TVS Motor website
    Given navigate to the TVS Motor home page
    When navigate to the "Our Products" page and click 'motorcycles'
    Then user should see motorcycles listed with their respective names and 'Know More' links
    And verifies the url for each motorcycle page after clicking on their respective Know More links

  @mopeds
  Scenario: verify TVS XL100 moped page navigation from home page on TVS Motor website
    Given navigate to the TVS Motor home page
    When navigate to the "Our Products" page and click 'mopeds'
    Then user should see moped listed with its respective name and 'Know More' links
    And verifies the url for moped page after clicking on its respective Know More links
