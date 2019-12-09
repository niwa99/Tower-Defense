Feature: Start a new wave in the game

  Background:
  I started the application
  I started the game by clicking "start game" and choosed a difficulty


  Scenario: open new operation dialog
    Given I am in the game activity and have a running game
    When I press the spawnpoint
    And the last wave completely passed the spwanpoint
    And there is a wave left
    Then the next wave will start