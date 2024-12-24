@ORPValidation @RunAll
Feature: Validating On-Road price updated correctly on brand pages for all the states and variants

  @ORPMultiVehicles
  Scenario Outline: Verify On-Road prices for all states on TVS brand pages for all variants
    Given navigate to the tvs brand "<Vehicle>" page in "<Environment>"
    When user navigated to the price section and accept the cookies pop up
    When click on the state dropdown and select the state
    #Then get the On-Road prices for all the states and variants
    When get the list of all states
    Then iterate through each state to select and fetch On-Road prices
    Then fetch On-Road prices for the selected state
    Then compare UI and API and Excel prices for all variants in each state

    Examples: select the vehicle brand
      #UAT Env
      | Vehicle        | Environment |
      | TVS_Zest_110   | PROD        |
      | TVS_NTORQ_125  | PROD       |
    # | TVS_RADEON_110 | PROD         |
      | StarCity+      | PROD        |
      | TVS_XL_100     | PROD        |

    @RaiderORP
    Scenario Outline: Verify On-Road prices for Raider TVS brand page
    Given navigate to the tvs brand "<Vehicle>" page in "<Environment>"
    When the user navigates to the price section of raider page and accepts the cookies pop-up
    Then get the On-Road prices for all the states and variants for "<Variant>"

      Examples: select the vehicle brand
        | Vehicle    | Environment | Variant     |
        | TVS_Raider | PROD        | DRUM        |
        | TVS_Raider | PROD        | SINGLE SEAT |
        | TVS_Raider | PROD        | SPLIT SEAT  |
        | TVS_Raider | PROD        | iGO         |
        | TVS_Raider | PROD        | SSE         |
        | TVS_Raider | PROD        | SX          |

  @ApacheSeries
  Scenario Outline: Verify On-Road prices for ApacheSeries brand page with all variants
    Given navigate to the "<Vehicle>" page in "<Environment>"
    When user navigated to the apache price section and accept the cookies pop up
    When click on the dropdowns to select "<Variant>" and state
    #Then get the On-Road prices for all the states and models for apache series
    Then get the list of states
    Then for each state get the UI On-Road prices
    Then for each state get the API On-Road prices
    Then compare the UI and API On-Road prices

    Examples: select the vehicle brand
      | Vehicle         | Environment | Variant           |
      | TVSApacheSeries | PROD        | Apache RTR 310    |
      | TVSApacheSeries | PROD        | Apache RTR 200 4V |
      | TVSApacheSeries | PROD        | Apache RR 310     |
      | TVSApacheSeries | PROD        | Apache RTR 160 2V |
      | TVSApacheSeries | PROD        | Apache RTR 160 4V |
      | TVSApacheSeries | PROD        | Apache RTR 180    |

  @TVSSport_ORP
  Scenario Outline: Verify On-Road prices for all variants of the TVS Sport brand across all states
    Given navigate to "<Vehicle>" brand page on "<Environment>"
    When user navigated to price section and accept the cookies
    When click on the state dropdown and fetch all the states
    Then get all states for the selected vehicle
    Then iterate through each state to select and get on-road prices
    Then fetch On-Road prices for the selected states
    Then compare UI and API prices with Excel prices for all the variants and states

    Examples: select the vehicle brand
      | Vehicle   | Environment |
      | TVS_SPORT | PROD         |
