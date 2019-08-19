Feature: Logging on to the demo web app
  User already registered with the demo web app can login using email address and the password provided
  Background: John is registered on the web app
    Given John is a already registered user with email address of 'john@mazarin.lk'
    And a password of 'secr3T'
    And already confirmed the email

  Scenario: Logging on successfully
    When John logs on with username: 'john@mazarin.lk' and a valid password of 'secr3T'
    Then he should be given access to the application

  Scenario Outline: Logging on with an incorrect password
    When John logs on with username: <email> and password <password>
    Then he should be informed with message code <code>

    Examples:

      | email               | password | code                | notes
      | johnmazarin         | secr     | insufficient_length |less than 5 chars
      | john@mazarin        | secret   | no_uppercase_letter |without upper case letter
      | john@mazarin        | S3CRET   | no_lowercase_letter |without lowercase letter
      | john@mazarin        | SeCRET   | no_digit_found      |without any digit

  Scenario: Logging on with an account with an unconfirmed email
    Given the account has not yet confirm the email
    When John logs on with password 'secr3T'
    Then he should be informed that his account is not created yet

  Scenario: Logging on with an expired account
    Given the account has expired
    When John logs on with password 'secr3T'
    Then he should be informed that his account has expired
    And he should be invited to renew his account