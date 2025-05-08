@RoninPrice
Feature: Validate TVS Ronin Ex-Showroom Price for all the States

  @RoninExPrice
  Scenario Outline: Verify Ex-Showroom Price for TVS Ronin Variants for all States
    Given navigate to the "<Vehicle>" page on "<Environment>"
    #When the user clicks on state dropdown
    #Then iterate through each state to select and fetch Ex-Showroom prices
    When the user selects each state from the dropdown and fetches Ex-Showroom prices
    Then fetch Ex-Showroom prices for the selected state
    Then compare UI and API Ex-Showroom prices for all variants in each state

    Examples:
      | Vehicle        | Environment |
      | TVS_Ronin      | UAT        |

  @RoninORP
  Scenario Outline: Verify On-Road Price for TVS Ronin Variants for all States
    Given navigate to the "<Vehicle>" page on "<Environment>" environment
    #When the user clicks on state dropdown
    #Then iterate through each state to select and fetch Ex-Showroom prices
    When user selects each state from the dropdown and fetches On-Road prices
    Then fetch On-Road prices for the selected state and variant
    Then compare UI and API On-Road prices for all variants in each state

    Examples:
      | Vehicle        | Environment |
      | TVS_Ronin      | UAT        |


