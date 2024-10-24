@testRide @regression
Feature: Test ride submission for different TVS vehicles
#
#  Scenario Outline: Submit Test ride form for Apache RR310
#    Given Navigate to test ride page
#    When Fill the "<name>", "<mobileNumber>" and "<pinCode>"
#    Then Request OTP for entered mobile number and verify OTP
#    And Click on submit button
#    And Verify the confirmation message of test ride booking
#
#    Examples:
#      | name                    | mobileNumber | pinCode |
#      | Test Booking Automation | 8088059296   | 590015  |
  @testRideNeg
  Scenario Outline: Submit Ride forms for TVS Vehicles with Invalid Details
    #Given Navigate to Ntorq page Test Ride page
    Given Navigate to Test Ride page for "<vehicle>"
    #When Fill the "<name>", "<mobileNumber>" and "<pinCode>"
    When click on submit button without filling required details and verify the error messages
    Then enter the invalid name and verify the error message
    And enter the invalid mobile number verify the error message
    And enter the invalid otp to verify error message
#    And request OTP button should be disabled
#    And click on the reset button

    #Then Request OTP button is "<otpButtonStatus>"
    #And Verify on submit button is disabled

    Examples: Negative Cases
      | vehicle       |
      | ApacheRR310   |
      | TVSJupiter    |
      | TvsSport      |
      | TvsStarCity   |
      | XL100         |
      | Ntorq125      |
      | Radeon        |
      | Zest110       |
      | Raider        |
      | Jupiter125    |


  @testRidePos
  Scenario Outline: Submit Ride forms for TVS Vehicles with Valid Details
    Given Navigate to Test Ride page for "<vehicle>"
    When Fill the "<name>", "<mobileNumber>" and "<pinCode>"
    Then the URL should indicate redirection to the UAT environment
    Then Request OTP button is "<otpButtonStatus>"
    And Request OTP for entered mobile number and verify OTP
    And click on detect button to get live location
    #And select dealer from dealer dropdown
    And Click on submit button
    And Verify the confirmation message of test ride booking
    And Verify data in the database after submitting a Test Ride form

    Examples: Positive Cases
      | name               | mobileNumber | pinCode | otpButtonStatus | vehicle     |
      | Test ride apache   | 1234567891   | 560072  | enabled         | ApacheRR310 |
      | test ride jupiter  | 1234567891   | 560060  | enabled         | TVSJupiter  |
      | Test ride sport    | 1234567891   | 560064  | enabled         | TvsSport    |
      | Test ride starcity | 1234567891   | 560069  | enabled         | TvsStarCity |
      | Test ride xl       | 1234567891   | 560062  | enabled         | XL100       |
      | Test ride ntorq    | 1234567891   | 560063  | enabled         | Ntorq125    |
      | Test ride radeon   | 1234567891   | 560066  | enabled         | Radeon      |
      | Test ride zest     | 1234567891   | 560067  | enabled         | Zest110     |
      | Test ride raider   | 1234567891   | 560067  | enabled         | Raider      |
      | Test ride jupiter  | 1234567891   | 560065  | enabled         | Jupiter125  |


#      | Test ride Automation | 8088059296   | 590015  | enabled         | zest         |
#      | Test ride Automation | 8088059296   | 590015  | enabled         | apacheRTR160 |
#      | Test ride Automation | 8088059296   | 590015  | enabled         | ntorqdrum    |
#      | Test ride Automation | 8088059296   | 590015  | enabled         | mulbikes     |
#      | Test ride Automation | 8088059296   | 590015  | enabled         | TVSSRS       |
#      | Test ride Automation | 8088059296   | 590015  | enabled         | TVSSSRX      |
#      | Test ride Automation | 8088059296   | 590015  | enabled         | SSRX         |



#      | name                 | mobileNumber | pinCode | otpButtonStatus
#      |                      | 8088059296   | 590015  | enabled
#      | Test ride Automation |              | 590015  | enabled
#      | Test ride Automation | 8088059296   |         | enabled
#      | InvalidName123       | 8088059296   | 590015  | enabled
#      | Test ride Automation | 123456789    | 590015  | disabled
#      | Test ride Automation | 8088059296   |         | enabled

#  Scenario Outline: Submit Test ride form for TVS Jupiter 125
#    Given Navigate to Ntorq page and click on test ride button
#    When Fill the "<name>", "<mobileNumber>" and "<pinCode>"
#    Then Request OTP for entered mobile number and verify OTP
#    And Click on submit button
#    And Verify the confirmation message of test ride booking
#
#    Examples:
#      | name                 | mobileNumber | pinCode |
#      | Test ride Automation | 8088059296   | 590015  |