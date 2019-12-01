Feature: Start the game from the menu

  Background:
  I started the application

  @easy
  Scenario: start a game with easy settings
    Given I am on the main page
    When I press the start game button
    Then I am on the choose difficulty dropdown
    And I choose easy by clicking on it
    Then I am on the game page
    And the map with easy settings is loaded

  Scenario: start a game with medium settings
    Given I am on the main page
    When I press the start game button
    Then I am on the choose difficulty dropdown
    And I choose medium by clicking on it
    Then I am on the game page
    And the map with medium settings is loaded

  Scenario: start a game with hard settings
    Given I am on the main page
    When I press the start game button
    Then I am on the choose difficulty dropdown
    And I choose hard by clicking on it
    Then I am on the game page
    And the map with hard settings is loaded