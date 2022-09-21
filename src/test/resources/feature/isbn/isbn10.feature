# new feature
# Tags: optional

Feature: Assign ISBN
  As librarian
  I need to assign ISBN codes to some new books
  To keep control, order and inventory of the books

  Scenario: Identify if the ISBN code is correct
    Given the librarian entered the application and generated a code
    When the librarian checks if the code is a valid ISBN code
    Then the librarian realizes if the code serves as an ISBN code