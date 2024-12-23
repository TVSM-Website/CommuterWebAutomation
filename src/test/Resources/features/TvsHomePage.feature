@HomeNavigation @RunAll
Feature: Verify Header navigation across the pages

  @HomeMotorcycle
  Scenario Outline: Verify functionalities of Motorcycles tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Motorcycles tab
    Then click on each vehicles to verify the title of Motorcycles
    And match the all 8 with no of vehicles displayed for Motorcycles
    Examples:
      | Environment |
      | UAT         |
     # | PROD        |

  @HomeScooters
  Scenario Outline: Verify functionalities of scooters tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on scooter tab
    Then click on each vehicles to verify the title of scooters
    And match the all 5 with no of vehicles displayed for scooters

    Examples:
      | Environment |
      | UAT         |
     # | PROD        |

  @HomeElectric @HomeNavigation
  Scenario Outline: Verify functionalities of Electric tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Electric tab
    Then click on each vehicles to verify the title of Electric vehicles
    And match the all 2 with no of vehicles displayed for electric
    Examples:
      | Environment |
      | UAT         |
     # | PROD        |

  @HomeMopeds
  Scenario Outline: Verify functionalities of Mopeds tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Mopeds tab
    Then click on each vehicles to verify the title of Mopeds
    And match the all 1 with no of vehicles displayed for Mopeds
    Examples:
      | Environment |
      | UAT         |
     # | PROD        |

  @Home3Wheelers
  Scenario Outline: Verify functionalities of ThreeWheelers tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on ThreeWheelers tab
    Then click on each vehicles to verify the title of ThreeWheelers
    And match the all 2 with no of vehicles displayed for ThreeWheelers
    Examples:
      | Environment |
      | UAT         |
      #| PROD        |

  @stateDropDownVerification
  Scenario Outline: Verify state name along with ex-showroom price visibility for scooters, motorcycles, and mopeds
    Given navigate to the TVS Motor home page in "<environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When navigate to the "Our Products" page for state drop down
    And navigates through state drop down and check visibility for each vehicle type
    Then the state name should be displayed along with ex-showroom price for the vehicle name for each scooter
    And the state name should be displayed along with ex-showroom price for the vehicle name for each motorcycle
    And the state name should be displayed along with ex-showroom price for the vehicle name for each moped

    Examples:
      | environment |
      | UAT         |
    #  | PROD        |


