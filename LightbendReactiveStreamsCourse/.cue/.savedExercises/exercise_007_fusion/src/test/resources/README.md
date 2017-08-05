fusion

# Exercise 7 > Fusion

In this exercise, we will introduce asynchronous boundaries to observe the
effects of Fusion.

## Akkassembly

- Pull in the runnable Akkassembly class by running the following
command in SBT: `pullTemplate scala/com/lightbend/akkassembly/Akkassembly.scala`
- You will find the new `Akkassembly.scala` in your exercises folder.
- Run the application using the `run` command in SBT.
- Verify your output is similar to:

```scala
[info] Running com.lightbend.akkassembly.Akkassembly 
[DEBUG] [11/28/2016 10:59:15.620] [run-main-5] [EventStream(akka://akkassembly)] logger log1-Logging$DefaultLogger started
[DEBUG] [11/28/2016 10:59:15.621] [run-main-5] [EventStream(akka://akkassembly)] Default Loggers started
[INFO] [11/28/2016 10:59:16.752] [akkassembly-akka.actor.default-dispatcher-4] [akka.actor.ActorSystemImpl(akkassembly)] Order Cars completed in 1020ms
[DEBUG] [11/28/2016 10:59:16.762] [akkassembly-akka.actor.default-dispatcher-3] [EventStream] shutting down: StandardOutLogger started
```

- Make note of the time it took to Order Cars.

## UnfinishedCar
- In `UnfinishedCar.scala` notice how many of the methods have a `busy` 
method which currently takes `0.milliseconds`.
- `busy` is defined in `package.scala`. Open `package.scala` and have a look
at what `busy` does.
- Adjust each busy method to take `100.milliseconds` instead.
- Run the application and note how long it takes to Order Cars.

## Factory
- In Factory, insert asynchronous boundaries at different points in the
stream.
- Run the application and note how long it takes to order cars.
- Try experimenting with different busy delays and different async
boundaries.
- **Question** What are the important locations to put an async boundary?
- **Question** Try a larger delay with and without async boundaries.
What happens to your CPU usage in each case?
- When you are finished, revert your busy delays back to 0.millis.

Use the `test` command to verify the solution works as expected.  
Use the `nextExercise` command to move to the next exercise.
