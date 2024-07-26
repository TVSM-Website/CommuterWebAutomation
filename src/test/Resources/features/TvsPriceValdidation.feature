@priceValidation
Feature: Validating On-Road and Ex-showroom price updated correctly on brand pages
  for all the states and variants

  @matchedElement
  Scenario Outline: Verify On-Road prices for all states on TVS brand pages for all variants
    Given navigate to the tvs brand "<Vehicle>" page in "<Environment>"
    When user navigated to the price section and accept the cookies pop up
    When click on the state dropdown and select the state
    Then get the On-Road prices for all the states and variants


    Examples: select the vehicle brand
      #UAT Env
      | Vehicle        | Environment |
      | JUPITER_125   | PROD         |
      | JUPITER | PROD        |
      | TVS_NTORQ_125 | PROD         |
      | TVS_Zest_110  | PROD         |
      #| Scooty_Pep+    | PROD        |
      | TVS_RADEON_110 | PROD        |
      | StarCity+      |   UAT        |
      | TVS_SPORT      |PROD|
      |  TVS_XL_100       | PROD            |

  @Ronin
  Scenario Outline: Verify Ex-showroom and On-Road prices for Ronin TVS brand page
    Given navigate to the tvs brand "<Vehicle>" page in "<Environment>"
    When the user navigates to the price section and accepts the cookies pop-up
    When click the state dropdown and select the state
    Then get the On-Road prices for all the states and variants for ronin

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle   | Environment |
 #     | TVS_Raider | UAT         |
      | TVS_Ronin | UAT         |

  @Raider
  Scenario Outline: Verify Ex-showroom and On-Road prices for Ronin TVS brand page
    Given navigate to the tvs brand "<Vehicle>" page in "<Environment>"
    When the user navigates to the price section of raider page and accepts the cookies pop-up
    Then get the On-Road prices for all the states and variants for raider

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle    | Environment |
 #     | TVS_Raider | UAT         |
      | TVS_Raider | PROD        |

  @ApacheSeries
  Scenario Outline: Verify On-Road prices for ApacheSeries brand page with all variants
    Given navigate to the "<Vehicle>" page in "<Environment>"
    When user navigated to the apache price section and accept the cookies pop up
    When click on the dropdowns to select "<Variant>" and state
    Then get the On-Road prices for all the states and models for apache series

    Examples: select the vehicle brand
      | Vehicle         | Environment | Variant           |
      | TVSApacheSeries | PROD         | Apache RTR 310    |
      | TVSApacheSeries | PROD         | Apache RR 310     |
      | TVSApacheSeries | PROD        | Apache RTR 160 2V |
#      | TVSApacheSeries | PROD         | Apache RTR 160 4V |
#      | TVSApacheSeries | PROD         | Apache RTR 180    |
#      | TVSApacheSeries | PROD         | Apache RTR 200 4V |