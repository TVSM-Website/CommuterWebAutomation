@priceValidation
Feature: Validating Onroad and Ex-showroom price updated correctly on brand pages
  for all the states and variants

  Scenario Outline: Verify Ex-showroom and Onroad prices for all states on TVS brand pages
    Given navigate to the tvs brand "<vehicle>" page
    When user navigated to the price section and accept the cookies pop up
    When click on the state dropdown and select the state
    Then get the prices for ex-showroom for the variants

    Examples: select the vehicle brand
      | vehicle           |
      | JUPITER_125 |
#      | tvs-ntorq-125   |
#      | tvs-jupiter     |
#      | tvs-zest        |
#      | tvs-scootypep   |