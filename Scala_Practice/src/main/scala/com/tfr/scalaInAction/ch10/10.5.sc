import org.specs2.mutable._
//Behavior driven development (BDD) using Specs2

//implementing an application by describing the behavior from the point of view of the stakeholders.

//how is this different from TDD? --it isn't!

//emphasis on the business problem

//delivering value quickly
//focus on behavior

//to get developers and stakeholders on the same page, you need the Ubiquitous language, a common language everybody speaks when describing the behavior of an application, and a tool so you can express these behaviors and write automated specifications that assert the behavior

//in BDD, you still follow the red-green-refactor cycle. the only thing that changes is the way you look at these tests or specifications


//--------Getting Started with Specs2

//"org.specs2" %% "specs2" % "1.13" % "test"
//SBT has native support for Specs2

//example in tests ...example.specs


//---------Working with Specifications

//become familiar with matchers
//beEqualTo and be_== are examples

//basic format of the Specs specification is that you extend the Specification trait and then provide examples

object MySpec extends Specification {
  "example1" in {}
  "example2" in {}
}

//a group of examples that describe the behavior of your application. typically this is your component for which you're describing the behavior; this is your system under specification

object SUSSpec extends Specification {
  "my system" should {
    "do this" in {}
    "do that" in {}
  }
}

//you can also nest examples if you want to refine your examples. you may want to add an example to describe the behavior when a parameter is empty //see example

//by default, examples are run in isolation, and they don't share any state (a good thing)

//can be declare using data tables, which lets you execute your example with a set of test data.

//you can use ScalaCheck with Specs to generate sample data for your table