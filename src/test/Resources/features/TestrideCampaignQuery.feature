@regression
Feature: Book a Test Ride Campaign Form Submission
  @TestRideCampPositive
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

  @TestRideCampNegative
  Scenario Outline:Verify Submitting Test Ride Campaign forms for TVS Vehicles with Invalid Details
    Given Navigate to Campaign Test Ride page for "<vehicle>"
    When user fills incorrect "<name>", "<mobileNumber>" and "<pinCode>"
    Then URL should indicate redirection to the UAT environment
    #Then request otp button is disabled
    And entered incorrect otp and verify the error message
    And validate invalid location pincode error message
    And Click on the submit button


    Examples: Negative Cases
      | name       | mobileNumber  | vehicle               |
      | test124    | 79754         | ApacheRR310_Campaign1 |
      | 1234567899 | 797540845     | TVSJupiter_Campaign3  |
      | 432423     | 79754084      | TvsSport_Campaign4    |
      | 3434432    | 79754084543   | TvsStarCity_Campaign5 |
      | 4323       | mobile        | XL100_Campaign8       |
      | #@4353     | 7975404       | Ntorq125_Campaign10   |
      | 43#43      | 7975408       | Radeon_Campaign11     |
      | 4243#2     | 79758454      | Zest110_Campaign12    |
      | 422&*)     | 7408454       | Raider_Campaign16     |
      | 5))@94     | 708454        | Jupiter125_Campaign17 |