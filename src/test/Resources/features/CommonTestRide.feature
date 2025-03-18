@GenTestRide @RunAll
Feature: Submit Test Ride Form from generic page

  @PositiveTests
  Scenario Outline: Submit test ride form for different vehicles
    Given the user is on the test ride page on "TestRide_PROD"
    When Fill the "<name>", "<mobileNumber>" and "<pinCode>"
    #And Request otp for entered mobile number and verify the OTP
    When the user selects "<vehicle>" from the vehicle dropdown
    When user selects the "<variant>" from the variant dropdown
    And Click on submit button
    #And Verify the confirmation message after form is submitted

    Examples:Positive Cases
      | vehicle               | variant                        | name      | mobileNumber | pinCode |
      | Apache RTR 310        | Base                           | Test User | 7975408454   | 560072  |
      | Apache RR 310         | Base without QuickShifter      | Test User | 7975408454   | 560060  |
      | TVS Apache RTR Series | RTR 180                        | Test User | 7975408454   | 560064  |
      | TVS Ronin             | TVS Ronin TD                   | Test User | 7975408454   | 560064  |
      | TVS Raider            | SX                             | Test User | 7975408454   | 560064  |
      | TVS Radeon            | Digi Drum                      | Test User | 7975408454   | 560064  |
      | TVS StaR City +       | Dual Tone Drum                 | Test User | 7975408454   | 560064  |
      | TVS Sport             | Self Start (ES) - Alloy Wheels | Test User | 7975408454   | 560072  |
      | TVS Jupiter           | DRUM SXC                       | Test User | 7975408454   | 560060  |
      | TVS Jupiter 125       | SmartXonnect                   | Test User | 7975408454   | 560064  |
      | TVS Ntorq             | Super Squad Edition            | Test User | 7975408454   | 560064  |
      | TVS Zest 110          | Matte Series                   | Test User | 7975408454   | 560064  |
      | TVS XL100             | Heavy Duty                     | Test User | 7975408454   | 560064  |
