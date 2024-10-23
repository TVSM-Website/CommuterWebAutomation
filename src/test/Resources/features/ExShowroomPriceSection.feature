@ExShowRoomPriceValidation @regression
Feature: Validating Ex-Road price updated correctly on brand pages for all the states and variants

  @ExShowRoom_AllVehicles
  Scenario Outline: Verify Ex-Road prices for all states on TVS brand pages for all variants
    Given navigate to the "<Vehicle>" brand page in "<Environment>"
    When user navigated to the price section and accept the cookies pop up
    When click on the state dropdown and fetch the states
    #Then get the On-Road prices for all the states and variants
    Then get the list of all states for the selected vehicle
    Then iterate through each state to select and fetch Ex-showRoom prices
    Then fetch Ex-showroom prices for the selected state
    Then compare UI and API prices with Excel prices for all variants in each state
    #Then validate the prices against Excel data

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle        | Environment |
#      | JUPITER_125    | PROD        |
#      | JUPITER        | PROD        |
      | TVS_Zest_110   | UAT        |
      | TVS_NTORQ_125  | UAT        |
      | TVS_RADEON_110 | UAT        |
      | StarCity+      | UAT        |
      | TVS_SPORT      | UAT        |
      | TVS_XL_100     | UAT        |

  @Ronin
  Scenario Outline: Verify On-Road prices for Ronin TVS brand page
    Given navigate to the tvs brand "<Vehicle>" page in "<Environment>"
    When the user navigates to the price section and accepts the cookies pop-up
#    When click the state dropdown and select the state
#    Then get the On-Road prices for all the states and variants for ronin

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle   | Environment |
      | TVS_Ronin | UAT         |

  @Raider_ExshowroomPrice
  Scenario Outline: Verify Ex-show room prices for Raider TVS brand page
    Given navigate to the "<Vehicle>" brand page in "<Environment>"
    When the user navigates to the price section of raider page and accepts the cookies pop-up
    Then get the Ex-showroom prices for all the states and variants for raider

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle    | Environment |
     #| TVS_Raider | UAT         |
      | TVS_Raider | PROD        |

#  @ApacheSeries_ExShowroomPrice
#  Scenario Outline: Verify On-Road prices for ApacheSeries brand page with all variants
#    Given navigate to the "<Vehicle>" page in "<Environment>"
#    When user navigated to the apache price section and accept the cookies pop up
#    When click on the dropdowns to select "<Variant>" and state
#    #Then get the On-Road prices for all the states and models for apache series
#    Then get the list of states
#    Then for each state get the UI On-Road prices
#    Then for each state get the API On-Road prices
#    Then compare the UI and API On-Road prices
#
#    Examples: select the vehicle brand
#      | Vehicle         | Environment | Variant           |
#      #| TVSApacheSeries | PROD        | Apache RTR 310    |
#      | TVSApacheSeries | PROD        | Apache RR 310     |
#      | TVSApacheSeries | PROD        | Apache RTR 160 2V |
#      | TVSApacheSeries | PROD        | Apache RTR 160 4V |
#      | TVSApacheSeries | PROD        | Apache RTR 180    |
#      | TVSApacheSeries | PROD        | Apache RTR 200 4V |