@regression
Feature: Book a Test Ride Campaign Form Submission
  @TestRideCampaignQuery
  Scenario Outline:Verify Submitting Test Ride Campaign forms for TVS Vehicles with Valid Details
    Given Navigate to Campaign Test Ride page for "<vehicle>"
    When user fills the "<name>", "<mobileNumber>" and "<pinCode>"
    Then URL should indicate redirection to the UAT environment
    Then Request otp button is "<otpButtonStatus>"
    And Request otp for entered mobile number and verify OTP
    And click on the detect button to get live location
    And Click on the submit button
    And Verify the confirmation message after form is submitted
    And Verify data in the database after submitting the test Ride form

    Examples: Positive Cases
      | name                 | mobileNumber | pinCode | otpButtonStatus | vehicle                |
      | Test ride Automation | 7975408454   | 560072  | enabled         | ApacheRR310_Campaign1   |
      | test ride automation | 7975408454   | 560060  | enabled         | TVSJupiter_Campaign3    |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TvsSport_Campaign4      |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TvsStarCity_Campaign5   |
      | Test ride Automation | 7975408454   | 560064  | enabled         | XL100_Campaign8         |
      | Test ride Automation | 7975408454   | 560064  | enabled         | Ntorq125_Campaign10      |
      | Test ride Automation | 7975408454   | 560064  | enabled         | Radeon_Campaign11        |
      | Test ride Automation | 7975408454   | 560064  | enabled         | Zest110_Campaign12       |
      | Test ride Automation | 7975408454   | 560064  | enabled         | Raider_Campaign16        |
      | Test ride Automation | 7975408454   | 560064  | enabled         | Jupiter125_Campaign17    |