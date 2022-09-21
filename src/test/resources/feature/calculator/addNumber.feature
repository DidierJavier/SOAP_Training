Feature: Calculator

@Add
Scenario: Add
  Given that the user entered to the add section on the calculator "235" and "265"
  When the user enters two numbers to add
  Then the system will get the result of the addition

@Divide
Scenario: Divide
  Given that the user entered to the divide section on the calculator "30" and "5"
  When the user enters two number to divide
  Then the system will get the result of the division


