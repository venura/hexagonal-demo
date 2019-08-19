Writing better Gherkin:

https://cucumber.io/docs/bdd/better-gherkin/
Story #1
Story: Register with email and password
In order to access the demo web app
As an Mazarin employee
I want to create an account with my email and a secure password
Acceptance criteria:
- User should be able to register with email and password

- Email is case insensitive and should be unique across the whole system

- Email should be in the correct format

Story #2
Story: Providing a secure password when registering
In order to avoid hackers compromising member accounts
As the systems administrator
I want new members to provide a secure password when they register

Acceptance criteria:
- password should be more than 5 characters
- password should contain at least 1 digit/number
- password should contain at least 1 uppercase
- password should contain at least 1 lowercase
MARK
- I should get an error message telling me what I
did wrong if I enter an insecure password