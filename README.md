# Elevator: Reader-Writer Problem
By: Ivonne Gattringer and Sara Panturu

Our application shows you the reader-writer problem.

## Situation:

There are buildings that have elevators, but from time to time elevators also have to be maintained.
In that case, the people that are already travelling with the elevator must go outside in order to let the maintainer do his job. In order to not let the people wait too long for the maintenance, the maintainer has a limited time to improve the elevator.

### Example:
The elevator starts working. After 15 ticks, the maintainer should start working on the improvements on the elevator but there are already people in the lift. The maintainer waits for all the people to go out before he can start his work. After all the people are gone, he has a time of 5 ticks to improve the elevator. Meanwhile, the users of the elevator are already waiting to use it. After the time is over, the maintanance ends and the people are allowed again to enter the elevator.
