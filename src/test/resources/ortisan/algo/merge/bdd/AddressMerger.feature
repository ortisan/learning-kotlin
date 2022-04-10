Feature: Address Merger
  As customer, I need a feature that merge my data and create versions based of power of data.

  Scenario: Customer does not have any address into database
    Given Im creating addresses as following
      | line1         | types       | completeness |
      | Augusta 123   | BUSINESS    | 200          |
      | Bonifacio 456 | RESIDENTIAL | 200          |
    When I have no addresses already inserted
    Then I have 2 new addresses, 0 updated addresses and 0 new diffs addresses

  Scenario: Customer already has addresses with lower power and is imputing new address with higher power
    Given Im creating addresses as following
      | line1      | types    | completeness |
      | Faria Lima | BUSINESS | 300          |
    When I already have these addresses
      | line1         | types       | completeness |
      | Augusta 123   | BUSINESS    | 200          |
      | Bonifacio 456 | RESIDENTIAL | 200          |
    Then I have 1 new addresses, 1 updated addresses and 0 new diffs addresses

  Scenario: Customer already has addresses with higher power and is imputing new address with lower power
    Given Im creating addresses as following
      | line1       | types    | completeness |
      | Augusta 123 | BUSINESS | 200          |
    When I already have these addresses
      | line1         | types       | completeness |
      | Faria Lima    | BUSINESS    | 300          |
      | Bonifacio 456 | RESIDENTIAL | 200          |
    Then I have 1 new addresses, 0 updated addresses and 1 new diffs addresses

