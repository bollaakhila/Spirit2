Feature: Student Feature
Background: details
  Given the body

Scenario: Verify that student data is created
When creating the student data
Then student data is created

Scenario: Verify Student is updated
  When Student is created
  And update the Age field
  Then Student age is Updated

  Scenario:  verify the student list
    When creating the student data
    Then student data is display

  Scenario: deleted the Student
    When Student data is delete
    Then Deleted Student data

  Scenario: verify that delete data can not be deleted
    When Student data is delete
    And Deleted Student data
    And duplicate delete
    Then id is required error

Scenario:  verify Student name is update
  When created a student data
  And update the name field
  Then Student name is update

  Scenario: verify Student name is null
    When created a student with no name
    Then Name is required error

    Scenario: verify Student age is null
      When created a student with no age
      Then Age is required error

  Scenario: verify Student email is null
    When created a student with no email
    Then email is required error

  Scenario: verify Student id is null
    When created a student with no id
    Then id is required error


