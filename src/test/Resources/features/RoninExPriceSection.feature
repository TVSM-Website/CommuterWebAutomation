@RoninExPrice
Feature: Validate TVS Ronin Ex-Showroom Price for all the States

  Scenario Outline: Verify Ex-Showroom Price for TVS Ronin Variants for all States
    Given navigate to the "<Vehicle>" page on "<Environment>"
    When the user clicks on state dropdown
    Then iterate through each state to select and fetch Ex-Showroom prices
    Then fetch Ex-Showroom prices for the selected state
    Then compare UI and API Ex-Showroom prices for all variants in each state

    Examples:
      | Vehicle        | Environment |
      | TVS_Ronin    | UAT        |