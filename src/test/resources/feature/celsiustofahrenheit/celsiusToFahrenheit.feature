# new feature
# Tags: optional

Feature: Temperature
  As scientific
  I want to do temperature conversions
  To make liquid mixtures properly

  Scenario: Convert from celsius to fahrenheit
    Given the scientist measured the temperature to be x degrees Celsius and entered the web resource
    When the scientist consults the temperature value in degrees Fahrenheit
    Then the scientist gets the temperature conversion in degrees "y" Fahrenheit