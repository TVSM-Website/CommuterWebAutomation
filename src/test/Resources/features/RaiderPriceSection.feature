@Raider_PriceSection
Feature: Validating Ex-showroom and On-Road prices of Raider for all the states and variants

  @Raider_ExPrice
  Scenario Outline: Verify Ex-show room prices for Raider TVS brand page
    Given navigate to the "<Vehicle>" brand page on "<Environment>"
    When the user navigates to the raider price section and accepts the cookies pop-up
    Then get the Ex-showroom prices for all the states and variants "<Variant>"

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle    | Environment | Variant     |
     | TVS_Raider | UAT         | DRUM        |
      | TVS_Raider | UAT         | SINGLE SEAT |
      | TVS_Raider | UAT        | SPLIT SEAT  |
      | TVS_Raider | UAT         | iGO         |
      | TVS_Raider | UAT         | SSE         |
      | TVS_Raider | UAT         | SX          |

  @Raider_OnRoadPrice
  Scenario Outline: Verify On-Road prices for Raider TVS brand page
    Given user navigate to "<Vehicle>" brand page on "<Environment>"
    When the user navigates to the raider price section and accepts the cookies pop-up
    Then get the On-Road prices for all the states and variants "<Variant>"

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle    | Environment | Variant     |
      | TVS_Raider | PROD         | DRUM        |
      | TVS_Raider | PROD         | SINGLE SEAT |
      | TVS_Raider | PROD        | SPLIT SEAT  |
      | TVS_Raider | PROD         | iGO         |
      | TVS_Raider | PROD         | SSE         |
      | TVS_Raider | PROD         | SX          |