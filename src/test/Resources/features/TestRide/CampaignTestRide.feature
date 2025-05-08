@TestRideCampaign @RunAll
Feature: Test ride submission of TVS vehicles for campaigns

  @NegativeTests
  Scenario Outline: Submit Campaign TesRide forms for TVS Vehicles with Invalid Details
    #Given Navigate to Ntorq page Test Ride page
    Given Navigate to Test Ride page for "<vehicle>"
    #When Fill the "<name>", "<mobileNumber>" and "<pinCode>"
    When click on submit button without filling required details and verify the error messages
    Then enter the invalid name and verify the error message
    And enter the invalid mobile number verify the error message
    And enter the invalid otp to verify error message
    And request OTP button should be disabled
    And click on the reset button


    Examples: Negative Cases
      | vehicle                |
      | ApacheRR310_Campaign   |
      | TVSJupiter_Campaign    |
      | TvsSport_Campaign      |
      | TvsStarCity_Campaign   |
      | XL100_Campaign         |
      | Ntorq125_Campaign      |
      | Radeon_Campaign        |
      | Zest110_Campaign       |
      | Raider_Campaign        |
      | Jupiter125_Campaign    |


  @TestRidePositiveTests
  Scenario Outline: Submit Campaign TesRide forms for TVS Vehicles with Valid Details
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
    And Verify campaign data stored in the database

    Examples: Positive Cases
      | name                 | mobileNumber | pinCode | otpButtonStatus | vehicle                |
      | Test ride Automation | 1234567891   | 560072  | enabled         | ApacheRR310_Campaign   |
      | test ride automation | 1234567891   | 560060  | enabled        | TVSJupiter_Campaign    |
      | Test ride Automation | 1234567891   | 560064  | enabled        | TvsSport_Campaign      |
      | Test ride Automation | 1234567891   | 560069  | enabled        | TvsStarCity_Campaign   |
      | Test ride Automation | 1234567891   | 560062  | enabled        | XL100_Campaign         |
      | Test ride Automation | 1234567891   | 560063  | enabled        | Ntorq125_Campaign      |
      | Test ride Automation | 1234567891   | 560066  | enabled        | Radeon_Campaign        |
      | Test ride Automation | 1234567891   | 560067  | enabled        | Zest110_Campaign       |
      | Test ride Automation | 1234567891   | 560067  | enabled        | Raider_Campaign        |
      | Test ride Automation | 1234567891   | 560065  | enabled        | Jupiter125_Campaign    |
