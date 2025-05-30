@ExShowRoom_AllVehicles @RunAll
Feature: Validating Ex-Road price updated correctly on brand pages for all the states and variants

  @ExPrice_MultiVehicles
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
#      | TVS_Zest_110   | PROD        |
#      | TVS_RADEON_110 | PROD        |
      | TVS_NTORQ_125  | PROD        |
#      | StarCity+      | PROD        |
#      | TVS_XL_100     | PROD        |


  @ApacheSeries_ExPrice
  Scenario Outline: Verify Ex-show room prices for ApacheSeries brand pages for all variants
    Given navigate to "<Vehicle>" page in "<Environment>"
    When user navigated to the apache price section and accept the cookies pop up
    When click on the dropdown to select "<Variant>" and state
    #Then get the On-Road prices for all the states and models for apache series
    Then fetch the list of states
    Then for each state get the UI Ex-showroom prices
    Then for each state get the API Ex-showroom prices
    Then compare the UI and API Ex-showroom prices

    Examples: select the vehicle brand
      | Vehicle         | Environment | Variant           |
      #| TVSApacheSeries | PROD        | Apache RTR 310    |
      #| TVSApacheSeries | PROD        | Apache RR 310     |
#      | TVSApacheSeries | PROD        | Apache RTR 160 2V |
      | TVSApacheSeries | PROD        | Apache RTR 160 4V |
#      | TVSApacheSeries | PROD        | Apache RTR 180    |
#      | TVSApacheSeries | PROD        | Apache RTR 200 4V |

  @TVSSport_ExPrice
  Scenario Outline: Verify Ex-Showroom prices for all variants of the TVS Sport brand across all states
    Given navigate to "<Vehicle>" brand page in "<Environment>"
    When user navigated to price section and accept the cookies pop up
    When click on the state dropdown and fetch all states
    Then fetch all states for the selected vehicle
    Then iterate through each state to select and get Ex-showRoom prices
    Then fetch Ex-showroom prices for the selected states
    Then compare UI and API prices with Excel prices for all variants and states

    Examples: select the vehicle brand
      | Vehicle   | Environment |
      | TVS_SPORT | UAT         |
