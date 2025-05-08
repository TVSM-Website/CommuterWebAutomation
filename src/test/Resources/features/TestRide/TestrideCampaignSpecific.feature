@TestRideCampSpec @RunAll
Feature: Book a Test Ride Campaign Form Submission

  @PositiveTests
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
      | name                 | mobileNumber | pinCode | otpButtonStatus | vehicle                  |
      | Test ride Automation | 7975408454   | 560072  | enabled         | ApacheRR310_Campaign1    |
      | test ride automation | 7975408454   | 560060  | enabled         | TVSJupiter_Campaign3     |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSSport_Campaign4       |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSStaRCity+_Campaign5   |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSXL100_Campaign8       |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSNtorq_Campaign10      |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSRadeon_Campaign11     |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSZest110_Campaign12    |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSRaider_Campaign16     |
      | Test ride Automation | 7975408454   | 560064  | enabled         | TVSJupiter125_Campaign17 |
      | Test ride Automation | 7975408454   | 560064  | enabled         | sport_campaign           |
      | Test ride Automation | 7975408454   | 560064  | enabled         | raider_campaign          |
      | Test ride Automation | 7975408454   | 560064  | enabled         | ntorq_campaign           |

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
      | 432423     | 79754084      | TVSSport_Campaign4    |
      | 3434432    | 79754084543   | TVSStaRCity+_Campaign5 |
      | 4323       | mobile        | TVSXL100_Campaign8       |
      | #@4353     | 7975404       | TVSNtorq_Campaign10   |
      | 43#43      | 7975408       | TVSRadeon_Campaign11     |
      | 4243#2     | 79758454      | TVSZest110_Campaign12    |
      | 422&*)     | 7408454       | TVSRaider_Campaign16     |
      | 5))@94     | 708454        | TVSJupiter125_Campaign17 |
      | 1234567899 | 797540845     | sport_campaign  |
      | 1234567899 | 797540845     | raider_campaign  |
      | 1234567899 | 797540845     | ntorq_campaign  |

