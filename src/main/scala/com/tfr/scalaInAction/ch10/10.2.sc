//Automated Test Generation using ScalaCheck

//ScalaCheck is a tool for testing Scala and Java programs by generating test data based on property specifications
//define a property that specifies the behavior of a piece of code, and ScalaCheck automatically generates random test data to check whether the property holds true

//a property

val anyString = "some string value"
anyString.reverse.reverse == anyString


//---------Testing the behavior of a string with ScalaCheck

//new property
//org.scalacheck.Prop
//forAll - factory method that creates a property that can be tested by ScalaCheck
//  takes a function that returns a Boolean and takes any type of parameter as lng as there is a generator (used to generate test data)

//property for above reverse.reverse statement
import org.scalacheck.{Arbitrary, Prop}

Prop.forAll((a:String) => a.reverse.reverse == a)
//for all strings, the expression should hold true

//examples in test directory .ch10


//---------Working with ScalaCheck

//writing specifications for Either
//  by convention Left=failure, Right=success

//to test
// 1.Either will have value on either Left or Right, but not both at the same time
// 2.fold on the Left should produce the value contained by Left
// 3.fold on the Right should produce the value contained by Right
// 4.swap returns the Left value to the Right and vice versa
// 5.getOrElse on Left returns the value from Left or the given argument if this is Right
// 6.forAll on Right returns true if Left or returns the result of the application of the given function to the right value

//First create a custom generator (none exist currently)

import org.scalacheck.Gen._
import org.scalacheck.Arbitrary.arbitrary

val leftValueGenerator = arbitrary[Int].map(Left(_))
val rightValueGenerator = arbitrary[Int].map(Right(_))

//generate Either with random left or right value
implicit val eitherGenerator = oneOf(leftValueGenerator, rightValueGenerator)

//you could define one that generates different kinds of values besides Int

implicit def arbitraryEither[X,Y](implicit xa: Arbitrary[X],
  ya: Arbitrary[Y]): Arbitrary[Either[X,Y]] = {
  Arbitrary[Either[X,Y]](
    oneOf(arbitrary[X].map(Left(_)), arbitrary[Y].map(Right(_)))
  )
}

//you can also use Gen.frequency to get more control over each individual generator and its uses. you could have Left 75% of the time and Right 25%:
implicit val eitherGenerator2 = frequency((3, leftValueGenerator),(1, rightValueGenerator))

//customise the number of tests
//pass arguments to your test through SBT, the below will run 500 tests
//test-only -- -s 500







