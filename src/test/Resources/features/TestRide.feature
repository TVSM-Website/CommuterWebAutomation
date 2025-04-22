@TestRide @RunAll
Feature: Test ride submission for different TVS vehicles

  @TRNegativeTests
  Scenario Outline: Submit Ride forms for TVS Vehicles with Invalid Details
    Given Navigate to Test Ride page for "<vehicle>"
    When click on submit button without filling required details and verify the error messages
    Then enter the invalid name and verify the error message
    And enter the invalid mobile number verify the error message
    And enter the invalid otp to verify error message
    And request OTP button should be disabled
    And click on the reset button

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


  @TestRidePositiveTests @Prod_Sanity
  Scenario Outline: Submit Ride forms for TVS Vehicles with Valid Details
    Given Navigate to Test Ride page for "<vehicle>"
    When Fill the "<name>", "<mobileNumber>" and "<pinCode>"
    #Then the URL should indicate redirection to the UAT environment
    Then Request OTP button is "<otpButtonStatus>"
    And Request OTP for entered mobile number and verify OTP
    And click on detect button to get live location
    #And select dealer from dealer dropdown
    And Click on submit button
    And Verify the confirmation message of test ride booking
    And Verify data in the database after submitting a Test Ride form

    Examples: Positive Cases
      | name               | mobileNumber | pinCode | otpButtonStatus | vehicle     |
      | Test ride apache   | 7975408454   | 560072  | enabled         | ApacheRR310 |
      | test ride jupiter  | 7975408454   | 560060  | enabled         | TVSJupiter  |
      | Test ride sport    | 7975408454   | 560064  | enabled         | TvsSport    |
      | Test ride starcity | 7975408454   | 560069  | enabled         | TvsStarCity |
      | Test ride xl       | 7975408454   | 560062  | enabled         | XL100       |
#     | Test ride ntorq    | 7975408454   | 560063  | enabled         | Ntorq125    |
#      | Test ride radeon   | 7975408454   | 560066  | enabled         | Radeon      |
#      | Test ride zest     | 7975408454   | 560067  | enabled         | Zest110     |
#      | Test ride raider   | 7975408454   | 560067  | enabled         | Raider      |
#      | Test ride jupiter  | 7975408454   | 560065  | enabled         | Jupiter125  |
