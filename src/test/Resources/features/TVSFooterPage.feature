@HomeNavigation @RunAll
Feature: Verify Footer navigation across the pages

  @FooterMotorcycle
  Scenario Outline: Verify functionalities of Motorcycles tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of Motorcycles with "<Section>" section
    And match the all 6 with no of vehicles displayed for Motorcycles under footer section
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |

  @FooterScooters
  Scenario Outline: Verify functionalities of scooters tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of scooters with "<Section>" section
    And match the all 4 with no of vehicles displayed for scooters under footer

    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |


  @FooterMoped
  Scenario Outline: Verify functionalities of Mopeds tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of mopeds with "<Section>" section
    And match the all 1 with no of vehicles displayed for mopeds under footer

    Examples:
      | Environment | Section |
      | UAT         | Footer  |

  @FooterEV
  Scenario Outline: Verify functionalities of scooters tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of electricScooters with "<Section>" section
    And match the all 1 with no of vehicles displayed for electric scooter under footer

    Examples:
      | Environment | Section |
      | UAT         | Footer  |




  @FooterThreeWheelers
  Scenario Outline: Verify functionalities of three wheelers tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of three wheelers with "<Section>" section
    And match the all 5 with no of vehicles displayed for three wheelers under footer section

    Examples:
      | Environment | Section |
      | UAT         | Footer  |




  @HomeElectric @HomeNavigation
  Scenario Outline: Verify functionalities of Electric tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Electric tab
   # Then click on each vehicles to verify the title of Electric vehicles
    Then click on each vehicles to verify the title of Electric vehicles with "<Section>" section
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

  @FooterRidesAndEvents
  Scenario Outline: Verify functionalities of RidesAndEvents tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of RidesAndEvents with "<Section>" section
    And match the all 3 with no of vehicles displayed for RidesAndEvents under footer
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |

  @FooterInvestors
  Scenario Outline: Verify functionalities of Investors tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of Investors with "<Section>" section
    And match the all 3 with no of vehicles displayed for Investors under footer
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |

  @FooterTVSDealerLocator
  Scenario Outline: Verify functionalities of TVSDealerLocator tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of TVSDealerLocator with "<Section>" section
    And match the all 6 with no of vehicles displayed for TVSDealerLocator under footer
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |

  @FooterAboutUs
  Scenario Outline: Verify functionalities of AboutUs tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of AboutUs with "<Section>" section
    And match the all 5 with no of vehicles displayed for AboutUs under footer
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |

  @FooterNewsAndMedia
  Scenario Outline: Verify functionalities of NewsAndMedia in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of NewsAndMedia with "<Section>" section
    And match the all 3 with no of vehicles displayed for NewsAndMedia under footer
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |

  @FooterSHOP
  Scenario Outline: Verify functionalities of SHOP tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of SHOP with "<Section>" section
    And match the all 4 with no of vehicles displayed for SHOP under footer
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |


  @FooterTvsConnectApp
  Scenario Outline: Verify functionalities of TvsConnectApp tab in Footer
    Given navigate to the TVS Motor home page in "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up

    Then click on each vehicles to verify the title of TvsConnectApp with "<Section>" section
    And match the all 2 with no of vehicles displayed for TvsConnectApp under footer
    Examples:
      | Environment | Section |
      | UAT         | Footer  |
     # | PROD        |
