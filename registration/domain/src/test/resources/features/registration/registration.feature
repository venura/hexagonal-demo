Feature: Registering online for a new demo web app account
  Anyone with a valid unique email address can register on the demo web app.

  Scenario: Registering online for a new demo web app account
    Given John is not already registered with demo web app
    When John submits email - john@mazarin.lk and password - s3creT as registration details
    Then John's account should be created in a pending state
    And A confirmation email should be sent to John

  Scenario: Trying to register with an email already registered
    Given "John" is already registered with demo web app using email "john@mazarin.lk"
    When John submits email - john@mazarin.lk and password - s3creT as registration details
    Then John's account should not be created
    And John is notified that his email already got registered

  Scenario Outline: Trying to register email with wrong format
    Given <user> is not already registered with demo web app
    When <user> submits email - <email> and password - <password> as registration details
    Then <user>'s account should not be created
    And <user> is notified that email is with wrong format

    Examples:

    |  user     | email               | password |  notes
    |  John     | john                | s3creT   | invalid email format
    |  John     | john@               | s3creT   | invalid email format
    |  John     | john@mazarin        | s3creT   | invalid email format


  Scenario Outline: Trying to register with an incorrect password
    Given John is not already registered with demo web app
    When John submits email - <email> and password - <password> as registration details
    Then he should be informed with message code <code>

    Examples:

      | email                  | password | code         | notes
      | john@mazarin.lk        | secr     | InvalidRange | less than 5 chars
      | john@mazarin.lk        | secret   | NoUppercase  | without upper case letter
      | john@mazarin.lk        | S3CRET   | NoLowercase  | without lowercase letter
      | john@mazarin.lk        | SeCRET   | NoDigit      | without any digit

