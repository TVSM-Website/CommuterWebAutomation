@OneBot
Feature: OneBot on TVS Motor multiple pages
#
#  @OneBot_Integration
 Scenario Outline: Verify if OneBot is integrated to the given url
    Given navigate to the TVS Motor page in "<Environment>", "<url>"
    When user finds the OneBot icon on the page
    Then click on the OneBot icon and verify if Chat with us is displayed
    And click on the Chat with us and verify if the chat window is displayed
    And verify the welcome text and languages are displayed
    And verify if able to select "<Language>" and how may I address you is displayed
    And verify if able to enter name and check the options displayed
    And verify if able to close the Chat bot
    Examples:
   #| Environment | url
      | Environment | url                          | Language |
      | UAT         | https://uat-www.tvsmotor.net/| English  |

      #