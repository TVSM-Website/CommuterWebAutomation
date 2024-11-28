@PageNavigation
Feature: Verify page-wise navigation and functionality across TVS Motor brand pages

  @PageNavigation_MotorCycle
  Scenario Outline: Verify functionalities of Motorcycles tab from brand pages
    Given navigate to the TVS Motor brand page with "<Url>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Motorcycles tab
    Then click on each vehicles to verify the title of Motorcycles
    And match the all 8 with no of vehicles displayed for Motorcycles
    Examples:
      | Url           |
      | careers_uat   |
      | offerings_uat |
      | blog_uat      |


  @PageNavigation_Scooter
  Scenario Outline: Verify functionalities of scooters tab from brand pages
    Given navigate to the TVS Motor brand page with "<Url>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on scooter tab
    Then click on each vehicles to verify the title of scooters
    And match the all 5 with no of vehicles displayed for scooters

    Examples:
      | Url           |
      | careers_prod  |
      | offerings_uat |
      | blog_uat      |
      | careers_uat   |

  @PageNavigation_Electric
  Scenario Outline: Verify functionalities of Electric tab from brand pages
    Given navigate to the TVS Motor brand page with "<Url>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Electric tab
    Then click on each vehicles to verify the title of Electric vehicles
    And match the all 2 with no of vehicles displayed for electric
    Examples:
      | Url           |
      | careers_uat   |
      | offerings_uat |
      | blog_uat      |

  @PageNavigation_Mopeds
  Scenario Outline: Verify functionalities of Mopeds tab from brand pages
    Given navigate to the TVS Motor brand page with "<Url>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on Mopeds tab
    Then click on each vehicles to verify the title of Mopeds
    And match the all 1 with no of vehicles displayed for Mopeds
    Examples:
      | Url           |
      | careers_uat   |
      | offerings_uat |
      | blog_uat      |

  @PageNavigation_3Wheelers
  Scenario Outline: Verify functionalities of ThreeWheelers tab from brand pages
    Given navigate to the TVS Motor brand page with "<Url>"
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on products and clicks on ThreeWheelers tab
    Then click on each vehicles to verify the title of ThreeWheelers
    And match the all 2 with no of vehicles displayed for ThreeWheelers
    Examples:
      | Url           |
      | careers_uat   |
      | offerings_uat |
      | blog_uat      |
