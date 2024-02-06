Feature: Are the Book links on the Book Store page present?
  Every book has a link to the detail info

  Scenario: The possibility to go to the book link is present
    Given Open Book Store in Book store Application
    When Fill search field
    When Tap Book link
    Then Book page is opened