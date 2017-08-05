working-with-flows

# Exercise 3 > Working with Flows

In this exercise, we will start working with `Flow`s. We will look at a few
different `Flow`s and how they can be used.

## Auditor

Our Auditor needs to know how many `Car`s are being produced by the assembly
line within the a specified time period.

- Create a def in `Auditor` named `sample` with a parameter `sampleSize` of type
  `FiniteDuration` and returns a `Flow`[`Car`, `Car`, `NotUsed`].
- Implement `sample` such that it **takes** all elements that are emitted
  **within** the time period defined by `sampleSize`.

## Paint Shop

When a car arrives from the `BodyShop` we will want to paint it the appropriate
color.

- Create a val in `PaintShop` named `paint` that returns a
  `Flow`[`UnfinishedCar`, `UnfinishedCar`, `NotUsed`].
- Implement the `paint` such that it creates a new `Flow` of `UnfinishedCar`,
  combines it with the `Color`s from the `colors` and then transforms the
  result, applying the paint color to the `UnfinishedCar`.
- **HINT** Use `zip` to combine your `Flow` with your `Source`.

## Wheel Shop

Similar to how we apply paint `Color`s in the `PaintShop` we also want to
install `Wheel`s in the `WheelShop`. However unlike `Colors`s, each 
`UnfinishedCar` will need four `Wheel`s instead of just one.

- Create a val in `WheelShop` named `installWheels` that returns a 
  `Flow`[`UnfinishedCar`, `UnfinishedCar`, `NotUsed`].
- Implement the `installWheels` such that it combines a **group** of four 
  `Wheel`s with the incoming `UnfinishedCar` and then installs the `Wheel`s onto
   the `UnfinishedCar`

## Engine Shop

When shipments of `Engine`s enter the assembly line they need to be installed
into the `UnfinishedCar`s. This will be handled in the `EngineShop`.

- Create a val in `EngineShop` named `engines` that returns a 
  `Source`[`Engine`, `NotUsed`]
- Implement the `engines` such that it takes `Engine`s from the `shipments` and
  **concatenates** them into a `Source` of `Engine`.
- **HINT** You can perform `Flow` operations on a `Source` directly, or using
  `via`. This will return a transformed `Source`.

- Create a val in `EngineShop` named `installEngine` that returns a 
  `Flow`[`UnfinishedCar`, `UnfinishedCar`, `NotUsed`].
- Implement the `installEngine` such that it combines an `Engine` with the 
  incoming `UnfinishedCar` and then installs the `Engine` into the `UnfinishedCar`

## Quality Assurance

Once all necessary steps have been performed on the car, we will need to 
inspect it to ensure it meets standards. If it passes inspection then it can be
considered to be a completed car. At this point we can assign it a 
`SerialNumber` and it can be ready for sale.

- Create a class called `QualityAssurance` with an empty constructor.
- Create a val in `QualityAssurance` named `inspect` that returns a 
  `Flow`[`UnfinishedCar`, `Car`, `NotUsed`].
- Implement `inspect` such that it takes in an UnfinishedCar, **collects** all
  completed cars and **filters** out any cars that lack an engine, paint color, or
  have an incorrect number of wheels. It will then convert the `UnfinishedCar` to
  a `Car`, giving it a new `SerialNumber`.

Use the `test` command to verify the solution works as expected.  
Use the `nextExercise` command to move to the next exercise.
