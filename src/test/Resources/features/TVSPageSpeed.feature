 @RunAll
Feature: Printing LCP and FCP values from Page Speed with TVS Website

  @Homepage
  Scenario Outline: Printing LCP and FCP values from Page Speed with TVS Website for Mobiles
    Given navigates to the Page Speed url
    When user navigated to home page and enters the TVS website url in "<Environment>" environment
    Then prints the LCP and FCP values from Page Speed with TVS Website for mobiles
    When user clicks on Desktop and enters the TVS website url in "<Environment>" environment
    Then prints the LCP and FCP values from Page Speed with TVS Website for Desktop
    Examples:
    | Environment |
    | https://www.tvsmotor.com                                             |
    | https://www.tvsmotor.com/about-us/overview                           |
    | https://www.tvsmotor.com/about-us/company-vision                     |
    | https://www.tvsmotor.com/about-us/sst                                |
    | https://www.tvsmotor.com/about-us/board-of-directors                 |
    | https://www.tvsmotor.com/about-us/board-of-directors/venu-srinivasan |
    | https://www.tvsmotor.com/about-us/achievements                       |
    | https://www.tvsmotor.com/about-us/careers/overview                   |
    | https://www.tvsmotor.com/about-us/contact-us                         |
    | https://www.tvsmotor.com/about-us/tvsride4safety                     |
    | https://www.tvsmotor.com/our-products/vehicles                       |
    | https://www.tvsmotor.com/our-service/our-products-and-offerings      |
    | https://www.tvsmotor.com/our-service/amc                             |
    | https://www.tvsmotor.com/our-service/rsa                             |
    | https://www.tvsmotor.com/our-service/proactive-inspection            |
    | https://www.tvsmotor.com/our-service/user-manual                     |
    | https://www.tvsmotor.com/investors/financial-reports                 |
    | https://www.tvsmotor.com/investors/investor-information              |
    | https://www.tvsmotor.com/investors/communication                     |
    | https://www.tvsmotor.com/investors/sebi-disclosure                   |
    | https://www.tvsmotor.com/investors/overview                          |
    | https://www.tvsmotor.com/analysts-meet                               |
    | https://www.tvsmotor.com/transcripts                                 |
    | https://www.tvsmotor.com/shareholder-communication                   |
    | https://www.tvsmotor.com/credit-rating-rationale                     |
    | https://www.tvsmotor.com/annual-report                               |
    | https://www.tvsmotor.com/stock-exchange-intimation                   |
    | https://www.tvsmotor.com/annual-return                               |
    | https://www.tvsmotor.com/composition-of-committees                   |
    | https://www.tvsmotor.com/familiarisation-programme                   |
    | https://www.tvsmotor.com/board-meetings                              |
    | https://www.tvsmotor.com/quarterly-reports                           |
    | https://www.tvsmotor.com/shareholder-pattern                         |
    | https://www.tvsmotor.com/sustainability/esg-profile                  |
    | https://www.tvsmotor.com/media/press-release                         |
    | https://www.tvsmotor.com/media/news                                  |
    | https://www.tvsmotor.com/media/blog/overview                         |
    | https://www.tvsmotor.com/media/blog/technologytalks                  |
    | https://www.tvsmotor.com/media/blog/didyouknow                       |
    | https://www.tvsmotor.com/media/blog/maintenance                      |
    | https://www.tvsmotor.com/media/blog/traveloguw                       |
    | https://www.tvsmotor.com/media/blog/expertspeaks                     |
    | https://www.tvsmotor.com/media/blog/tvsvehicles                      |
    | https://www.tvsmotor.com/media/press-kit                             |
    | https://www.tvsmotor.com/media/contact                               |
    | https://www.tvsmotor.com/tvs-dealer-locator/tvs-2-wheeler            |
    | https://www.tvsmotor.com/tvs-dealer-locator/tvs-service-center       |
    | https://www.tvsmotor.com/tvs-dealer-locator/authorised-parts-stockist|
    | https://www.tvsmotor.com/book-a-ride                                 |
    | https://www.tvsmotor.com/book-online                                 |
    | https://www.tvsmotor.com/account/login                               |
    | https://www.tvsmotor.com/account/cancellogin                         |
    | https://www.tvsmotor.com/privacy-policy                              |
    | https://www.tvsmotor.com/disclaimer                                  |
    |https://www.tvsmotor.com/cookie-policy                                |
    | https://www.tvsmotor.com/become-a-dealer                             |
    | https://www.tvsmotor.com/become-a-dealer/thank-you                   |
    | https://www.tvsmotor.com/disclaimer/termsandandconditions            |
    # | https://www.tvsmotor.com/account/my-profile                          |
    # | https://www.tvsmotor.com/account/my-booking                          |
    #| https://www.tvsmotor.com/account/my-bike                             |
    # | https://www.tvsmotor.com/account/add-bike                            |
    # | https://www.tvsmotor.com/account/my-ride-stories                     |
    #| https://www.tvsmotor.com/account/my-discussion                       |
    # | https://www.tvsmotor.com/account/my-event                            |
    #| https://www.tvsmotor.com/Account/Add-bike                            |
    | https://www.tvsmotor.com/tru4-engine-oil                             |





