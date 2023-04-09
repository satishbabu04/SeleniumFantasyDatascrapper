Feature: Scrape

  Scenario: Open Fantasy Page

    Given Launch fantasy Page
    When user clicks on Create Team
    And Clicks on back button
    Then Clicks on My Contests
    And Clicks on ViewLeaderboard
    Then User should be able to view all participating teams
    And User clicks on each team to get score in each match and Booster info and C VC info