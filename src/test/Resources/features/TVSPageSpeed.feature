@PageSpeed
Feature: Printing LCP and FCP values from Page Speed with TVS Website

  @FCP_LCP
  Scenario Outline: Printing LCP and FCP values from Page Speed with TVS Website for Mobiles
    Given navigates to the Page Speed url
    When user navigated to home page and enters the TVS website url in "<Environment>" environment
    Then prints the LCP and FCP values from Page Speed with TVS Website for mobiles
    When user clicks on Desktop and enters the TVS website url in "<Environment>" environment
    Then prints the LCP and FCP values from Page Speed with TVS Website for Desktop
    Examples:
      | Environment                                  |
      | https://www.tvsmotor.com/                    |
      | https://www.tvsmotor.com/commuter/tvs-xl100         |
      | https://www.tvsmotor.com/commuter/tvs-sport        |
#      | https://www.tvsmotor.com/account/login       |
#      | https://www.tvsmotor.com/become-a-dealer     |
#      | https://www.tvsmotor.com/about-us/contact-us |
      | https://www.tvsmotor.com/tvs-ntorq           |
      | https://www.tvsmotor.com/tvs-zest            |




