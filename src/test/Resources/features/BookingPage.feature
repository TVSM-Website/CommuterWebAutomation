@VehicleBooking @Prod_Sanity
Feature: Verify the booking vehicle online flow

  Scenario Outline: Verify the Book a vehicle online for scooters on
    Given navigate to tvs website on "<Environment>" environment
    When user navigated to home page and accepts the cookies pop-up
    When user clicks on buy vehicle button
    When select the vehicle "<VehicleName>" from the list
    Then select the variant "<VariantName>"
    And click on next button
    And enter the location and choose dealership name
    And Enter the personal details "<MobileNumber>","<FullName>" and "<EmailId>"
    And enter the location and choose dealership name
    And click on terms and conditions checkbox
    And click on verify mobile number button
    Then Click on submit button to verify otp
    And click on the Pay button
    And click on the cancel and confirm cancel button


    Examples:
      | Environment | VehicleName     | VariantName | MobileNumber | FullName               | EmailId      |
      | PROD        | TVS Jupiter  | DRUM   | 7975408454   | Automation TestBooking | test@tvs.com |
      | PROD       | TVS Jupiter  | DRUM SXC  | 7975408454   | Automation TestBooking | test@tvs.com |
      | PROD       | TVS Jupiter  | DRUM ALLOY  | 7975408454   | Automation TestBooking | test@tvs.com |
      | PROD        | TVS Jupiter  | DISC SXC  | 7975408454   | Automation TestBooking | test@tvs.com |


  #Scenario: Make a payment and verify the booking status
#    And OTP submit button should be disabled before entering the otp
#    And OTP submit button should be enabled after entering the otp
#    And Mobile number should be verified
#    And mobile number,fullName and email id text fields should be disabled
#    Then click on netBanking payment mode
#    And select the "Avenues Test for New TC" bank from dropdown values
#    And click on Make Payment button

