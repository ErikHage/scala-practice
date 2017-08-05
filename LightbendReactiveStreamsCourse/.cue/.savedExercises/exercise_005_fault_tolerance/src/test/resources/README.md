fault-tolerance

# Exercise 5 > Fault Tolerance

In this exercise, we will look at how to handle failures in a stream.

## Quality Assurance

Previously, our Quality Assurance was implemented by inspecting and filtering
out any cars that did not meet standards. We will modify this so that instead
of applying a filter, it will throw an exception and then handle that exception
with an appropriate supervisor strategy.

- In `QualityAssurance`, create a companion object and add an `Exception` named
  `CarFailedInspection` with a single parameter `car` of type `UnfinishedCar`.
  Have the `Exception` extend `IllegalStateException` and include an appropriate
  error message.
- In `QualityAssurance`, modify the `inspect` flow. Rather than filtering,
  instead throw a `CarFailedInspection` `Exception` if the car does not meet the
  necessary criteria.
- Add an appropriate **supervisor** **strategy** to the `inspect` flow so that
  it can continue to process messages when a failure occurs.

Use the `test` command to verify the solution works as expected.  
Use the `nextExercise` command to move to the next exercise.
