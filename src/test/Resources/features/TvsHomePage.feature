@vehiclesNav @RunAll @Prod_Sanity
Feature: Verify navigation to brand pages with checkpoint validations
  @HomeMotorcycle
  Scenario Outline: Verify functionalities of Motorcycles tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Motorcycles tab
    Then click on each motorcycle to verify redirection and validate booking,test ride,and dealer locator buttons
    Then wait for the page to load completely
    And match the all 11 with no of vehicles displayed for Motorcycles
    Examples:
      | Environment |
      | UAT         |

  @HomeScooters
  Scenario Outline: Verify functionalities of scooters tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on scooter tab
    #Then click on each vehicles to verify the title of scooters
    And click on scooters to verify redirection and validate booking,test ride,and dealer locator buttons
    Then wait for the page to load completely
    And match the all 4 with no of vehicles displayed for scooters

    Examples:
      | Environment |
      | UAT         |

  @HomeElectric @HomeNavigation
  Scenario Outline: Verify functionalities of Electric tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Electric tab
    #And click on electrics to verify redirection and validate booking,test ride,and dealer locator buttons
    Then click on each vehicles to verify the title of Electric vehicles
    Then wait for the page to load completely
    And match the all 3 with no of vehicles displayed for electric
    Examples:
      | Environment |
      | UAT         |

  @HomeMopeds
  Scenario Outline: Verify functionalities of Mopeds tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Mopeds tab
    And click on mopeds to verify redirection and validate booking,test ride,and dealer locator buttons
    Then wait for the page to load completely
    And match the all 1 with no of vehicles displayed for Mopeds
    Examples:
      | Environment |
      | UAT         |

  @Home3Wheelers
  Scenario Outline: Verify functionalities of ThreeWheelers tab
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on ThreeWheelers tab
    Then click on each vehicles to verify the title of ThreeWheelers
    Then wait for the page to load completely
    And match the all 2 with no of vehicles displayed for ThreeWheelers
    Examples:
      | Environment |
      | UAT         |


