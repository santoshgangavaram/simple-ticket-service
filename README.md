<h1>Ticket/Seat Reservation Service</h1>

<h2>Product description</h2>

```
----------[[  STAGE  ]]----------
---------------------------------
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss

Ticket Service that provides the following functions:
· Find the number of seats available within the venue
Note: available seats are seats that are neither held nor reserved.
· Find and hold the best available seats on behalf of a customer
Note: each ticket hold should expire within a set number of seconds. 
· Reserve and commit a specific group of held seats for a customer
```

## Assumptions

1. Solution solves for exactly above auditorium structure (seating arrangement), which means 8 rows and 34 seats each
2. Algorithm only solves for horizontal contiguous seats grouping for finding best available seats for customer
3. No persistent seat state management implemented. It is all in-memory.
4. No rest end point nor UI interface implemented
5. Reservation is not modeled/managed in the solution. we can have it baked in easily if we have an interface to offer functions on it
6. junit4 is used as external dependency to implement tests
7. java8 is used to build/run the code

## Instructions to build and run the tests

1. To build the package and run tests
``` mvn package ```
2. To run tests alone
``` mvn test ```
