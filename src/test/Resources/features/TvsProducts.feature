#@Products
#Feature: TVS Motor HomePage Products Menu Validations
#
#  @Products_Motorcycles
#  Scenario Outline: Verify functionalities of Motorcycles tab
#    Given navigate to the TVS Motor home page in "<Environment>" environment
#    When user navigated to home page and accepts the cookies pop-up
#    When user clicks on products and clicks on Motorcycles tab
#    Then click on each vehicles to verify the title of Motorcycles
#    And match the all 8 with no of vehicles displayed for Motorcycles
#    Examples:
#      | Environment |
#      | UAT         |
#     # | PROD        |
#
#  @Products_Scooters
#  Scenario Outline: Verify functionalities of scooters tab
#    Given navigate to the TVS Motor home page in "<Environment>" environment
#    When user navigated to home page and accepts the cookies pop-up
#    When user clicks on products and clicks on scooter tab
#    Then click on each vehicles to verify the title of scooters
#    And match the all 5 with no of vehicles displayed for scooters
#
#    Examples:
#      | Environment |
#      | UAT         |
#     # | PROD        |
#
#  @Products_Electric
#  Scenario Outline: Verify functionalities of Electric tab
#    Given navigate to the TVS Motor home page in "<Environment>" environment
#    When user navigated to home page and accepts the cookies pop-up
#    When user clicks on products and clicks on Electric tab
#    Then click on each vehicles to verify the title of Electric vehicles
#    And match the all 2 with no of vehicles displayed for electric
#    Examples:
#      | Environment |
#      | UAT         |
#     # | PROD        |
#
#  @Products_Mopeds
#  Scenario Outline: Verify functionalities of Mopeds tab
#    Given navigate to the TVS Motor home page in "<Environment>" environment
#    When user navigated to home page and accepts the cookies pop-up
#    When user clicks on products and clicks on Mopeds tab
#    Then click on each vehicles to verify the title of Mopeds
#    And match the all 1 with no of vehicles displayed for Mopeds
#    Examples:
#      | Environment |
#      | UAT         |
#     # | PROD        |
#
#  @Products_ThreeWheelers
#  Scenario Outline: Verify functionalities of ThreeWheelers tab
#    Given navigate to the TVS Motor home page in "<Environment>" environment
#    When user navigated to home page and accepts the cookies pop-up
#    When user clicks on products and clicks on ThreeWheelers tab
#    Then click on each vehicles to verify the title of ThreeWheelers
#    And match the all 2 with no of vehicles displayed for ThreeWheelers
#    Examples:
#      | Environment |
#      | UAT         |
#     # | PROD        |