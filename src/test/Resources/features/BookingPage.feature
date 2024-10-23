@VehicleBooking @regression
Feature: Verify the booking vehicle online flow

  Scenario Outline: Select the vehicle ,variant,location and dealer details
    Given Navigate to the booking page
    When select the vehicle and variant
    Then next button is disabled before selecting the location and dealer
    And enter the location and choose dealership name
    And next button is enabled after selecting the location and dealer

  #Scenario Outline: Fill the details and verify the otp
    And Marketing updated checkbox should be checked
    And Verify mobile number and otp button should be disabled before filling the details
    Then Enter the personal details "<MobileNumber>","<FullName>" and "<EmailId>"
    And Verify mobile number and otp button should be enabled after filling the details

    And OTP submit button should be disabled before entering the otp
    And OTP submit button should be enabled after entering the otp
    And Mobile number should be verified
    And mobile number,fullName and email id text fields should be disabled
    Then click on netBanking payment mode
    And select the "Avenues Test for New TC" bank from dropdown values
    And click on Make Payment button


    Examples:
      | MobileNumber | FullName                | EmailId            |
      | 8088059296   | Automation Test Booking | testAuto@gmail.com |

  #Scenario: Make a payment and verify the booking status
#    And OTP submit button should be disabled before entering the otp
#    And OTP submit button should be enabled after entering the otp
#    And Mobile number should be verified
#    And mobile number,fullName and email id text fields should be disabled
#    Then click on netBanking payment mode
#    And select the "Avenues Test for New TC" bank from dropdown values
#    And click on Make Payment button

