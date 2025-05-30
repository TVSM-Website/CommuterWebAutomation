@JupiterPriceSection
Feature:Validate TVS Jupiter 110 and 125 Price updates for all the States and variants

  @JupiterExPrice
  Scenario Outline: Verify Ex-Showroom Price for TVS Jupiter Variants for all States
    Given navigate to the given "<Vehicle>" page on "<Environment>"
    When user closes the location pop up
    Then click on price button to navigate to price section
    When click on the jupiter state dropdown and fetch all states
    Then fetch all states from the dropdown
    Then iterate through each state and get Ex-showRoom prices
    Then fetch Ex-showroom prices for the state selected
    Then compare UI and API prices with Excel prices for the variants and states

    Examples:
      | Vehicle     | Environment |
      #| JUPITER | PROD         |
      | JUPITER_125 | PROD        |


  @JupiterORP
  Scenario Outline: Verify ORP Price for TVS Jupiter Variants for all States
    Given navigate to the given "<Vehicle>" page on the "<Environment>"
    When user closes the location pop up
    Then click on price button to navigate to price section
    When click on the jupiter orp state dropdown and fetch all states
    Then fetch all states from the orp dropdown
    Then iterate through each state and get orp prices
    Then fetch orp prices for the state selected
    Then compare UI and API orp prices with Excel prices for the variants and states

    Examples:
      | Vehicle     | Environment |
      #| JUPITER | PROD         |
      | JUPITER_125 | PROD        |