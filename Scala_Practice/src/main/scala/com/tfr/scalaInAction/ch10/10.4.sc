//Better tests with Dependency Injection

//design pattern that separates behavior from dependency resolution
//also helps to design programs that are highly decoupled in nature

//example in example.CalculatePriceService


//--------Techniques to implement DI
//test double -> a common umbrella term for a test-specific equivalent of a component that  your system under test depends on

//techniques to implement DI in Scala
// - CakePattern: using trait mixins and abstract members
// - Structural Typing: duck typing
// - Implicit parameters: the caller doesn't have to pass the dependencies
// - Functional programming style: function currying
// - DI framework: many variations


//-------Cake Pattern
//technique to build multiple layers of indirection in your application to help with managing dependencies
// 1. Abstract members
// 2. Self type annotation
// 3. Mixin composition

//example.cakePattern

//have a component namespace that has all the calculators in your application


//--------Structural Typing

//describe types by their structure
//to create a structure, create a new type:
//type Calculators = {
//  val costPlusCalculator: Calculator
//  val externalPriceSourceCalculator: Calculator
//}

//use that to inject, via constructor

//example.structuralTyping

//great solution, but slightly slower bc it uses reflection


//--------Implicit parameters

//use implicit parameters in the constructor:
//class CalculatePriceService(
//  implicit val costPlusCalculator: CostPlusCalculator,
//  implicit val externalPriceSourceCalculator: ExternalPriceSourceCalculator
//)

//downside: can't be defined as the trait Calculator, must be their specific Types

//example.implicitParameters


//-------Dependency injection in functional style

//using function currying


//DI Framework like Spring

//requires default constructor
//scala @BeanProperty
//generates getters and setters for vars

//testing:
//@RunWith(classOf[SpringJUnit4ClassRunner])
//@ContextConfiguration(locations = Array("classpath:/application-context.xml"))

//use @Resource to inject (like autowire)


