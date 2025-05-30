@PageNavigation @RunAll
Feature: Verify page-wise navigation and functionality across TVS Motor brand pages

  @PageNavigation_MotorCycle
  Scenario Outline: Verify functionalities of Motorcycles tab from brand pages
    Given navigate to the TVS Motor brand page on "<env>" with "<path>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Motorcycles tab
    Then click on each vehicles to verify the title of Motorcycles
    #And match the all 12 with no of vehicles displayed for Motorcycles
    Examples:
    Examples:
      | env | path                               |
      | uat | tvs-ntorq                          |
      | uat | tvs-ntorq/price                    |
      | uat | tvs-ntorq/smartxonnect             |
      | uat | tvs-ntorq/ntorqians/youngtorq      |
      | uat | tvs-ntorq/racing                   |
      | uat | tvs-ntorq/faq                      |
      | uat | tvs-ntorq/features                 |
      | uat | tvs-ntorq/ntorqians                |
      | uat | tvs-ntorq/ntorqians/revvv          |
      | uat | tvs-ntorq/ntorq-xt                 |
      | uat | tvs-ntorq/ntorq-xp                 |
      | uat | tvs-ntorq/super-squad              |
      | uat | tvs-ntorq/ntorq-re                 |
      | uat | tvs-ntorq/ntorq-125                |
      | uat | tvs-ntorq/colours                  |
      | uat | tvs-ntorq/technical-specifications |
      | uat | tvs-ntorq/price-in-delhi           |
      | uat | tvs-ntorq/tnc                      |
      | uat | tvs-ntorq/prebooking               |
      | uat | tvs-ntorq/merchandise-accessories  |


  @PageNavigation_Scooter
  Scenario Outline: Verify functionalities of scooters tab from brand pages
    Given navigate to the TVS Motor brand page on "<env>" with "<path>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on scooter tab
    Then click on each vehicles to verify the title of scooters
    #And match the all 4 with no of vehicles displayed for scooters

    Examples:
    Examples:
      | env | path                               |
      | uat | tvs-ntorq                          |
      | uat | tvs-ntorq/price                    |
      | uat | tvs-ntorq/smartxonnect             |
      | uat | tvs-ntorq/ntorqians/youngtorq      |
      | uat | tvs-ntorq/racing                   |
      | uat | tvs-ntorq/faq                      |
      | uat | tvs-ntorq/features                 |
      | uat | tvs-ntorq/ntorqians                |
      | uat | tvs-ntorq/ntorqians/revvv          |
      | uat | tvs-ntorq/ntorq-xt                 |
      | uat | tvs-ntorq/ntorq-xp                 |
      | uat | tvs-ntorq/super-squad              |
      | uat | tvs-ntorq/ntorq-re                 |
      | uat | tvs-ntorq/ntorq-125                |
      | uat | tvs-ntorq/colours                  |
      | uat | tvs-ntorq/technical-specifications |
      | uat | tvs-ntorq/price-in-delhi           |
      | uat | tvs-ntorq/tnc                      |
      | uat | tvs-ntorq/prebooking               |
      | uat | tvs-ntorq/merchandise-accessories  |


  @PageNavigation_Electric
  Scenario Outline: Verify functionalities of Electric tab from brand pages
    Given navigate to the TVS Motor brand page on "<env>" with "<path>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Electric tab
    Then click on each vehicles to verify the title of Electric vehicles
    #And match the all 2 with no of vehicles displayed for electric
    Examples:
    Examples:
      | env | path                               |
      | uat | tvs-ntorq                          |
      | uat | tvs-ntorq/price                    |
      | uat | tvs-ntorq/smartxonnect             |
      | uat | tvs-ntorq/ntorqians/youngtorq      |
      | uat | tvs-ntorq/racing                   |
      | uat | tvs-ntorq/faq                      |
      | uat | tvs-ntorq/features                 |
      | uat | tvs-ntorq/ntorqians                |
      | uat | tvs-ntorq/ntorqians/revvv          |
      | uat | tvs-ntorq/ntorq-xt                 |
      | uat | tvs-ntorq/ntorq-xp                 |
      | uat | tvs-ntorq/super-squad              |
      | uat | tvs-ntorq/ntorq-re                 |
      | uat | tvs-ntorq/ntorq-125                |
      | uat | tvs-ntorq/colours                  |
      | uat | tvs-ntorq/technical-specifications |
      | uat | tvs-ntorq/price-in-delhi           |
      | uat | tvs-ntorq/tnc                      |
      | uat | tvs-ntorq/prebooking               |
      | uat | tvs-ntorq/merchandise-accessories  |


  @PageNavigation_Mopeds
  Scenario Outline: Verify functionalities of Mopeds tab from brand pages
    Given navigate to the TVS Motor brand page on "<env>" with "<path>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Mopeds tab
    Then click on each vehicles to verify the title of Mopeds
    #And match the all 1 with no of vehicles displayed for Mopeds
    Examples:
    Examples:
      | env | path                               |
      | uat | tvs-ntorq                          |
      | uat | tvs-ntorq/price                    |
      | uat | tvs-ntorq/smartxonnect             |
      | uat | tvs-ntorq/ntorqians/youngtorq      |
      | uat | tvs-ntorq/racing                   |
      | uat | tvs-ntorq/faq                      |
      | uat | tvs-ntorq/features                 |
      | uat | tvs-ntorq/ntorqians                |
      | uat | tvs-ntorq/ntorqians/revvv          |
      | uat | tvs-ntorq/ntorq-xt                 |
      | uat | tvs-ntorq/ntorq-xp                 |
      | uat | tvs-ntorq/super-squad              |
      | uat | tvs-ntorq/ntorq-re                 |
      | uat | tvs-ntorq/ntorq-125                |
      | uat | tvs-ntorq/colours                  |
      | uat | tvs-ntorq/technical-specifications |
      | uat | tvs-ntorq/price-in-delhi           |
      | uat | tvs-ntorq/tnc                      |
      | uat | tvs-ntorq/prebooking               |
      | uat | tvs-ntorq/merchandise-accessories  |


  @PageNavigation_3Wheelers
  Scenario Outline: Verify functionalities of ThreeWheelers tab from brand pages
    Given navigate to the TVS Motor brand page on "<env>" with "<path>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on ThreeWheelers tab
    Then click on each vehicles to verify the title of ThreeWheelers
    #And match the all 3 with no of vehicles displayed for ThreeWheelers
    Examples:
    Examples:
      | env | path                               |
      | uat | tvs-ntorq                          |
      | uat | tvs-ntorq/price                    |
      | uat | tvs-ntorq/smartxonnect             |
      | uat | tvs-ntorq/ntorqians/youngtorq      |
      | uat | tvs-ntorq/racing                   |
      | uat | tvs-ntorq/faq                      |
      | uat | tvs-ntorq/features                 |
      | uat | tvs-ntorq/ntorqians                |
      | uat | tvs-ntorq/ntorqians/revvv          |
      | uat | tvs-ntorq/ntorq-xt                 |
      | uat | tvs-ntorq/ntorq-xp                 |
      | uat | tvs-ntorq/super-squad              |
      | uat | tvs-ntorq/ntorq-re                 |
      | uat | tvs-ntorq/ntorq-125                |
      | uat | tvs-ntorq/colours                  |
      | uat | tvs-ntorq/technical-specifications |
      | uat | tvs-ntorq/price-in-delhi           |
      | uat | tvs-ntorq/tnc                      |
      | uat | tvs-ntorq/prebooking               |
      | uat | tvs-ntorq/merchandise-accessories  |
