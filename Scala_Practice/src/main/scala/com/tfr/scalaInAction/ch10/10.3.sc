//Test-driven development cycle

//using tests to drive the design of software

// 1. start with a failing end-to-end test (integration)
// 2. write a bunch of unit tests to break the problem into smaller pieces and make them pass
// 3. you only write production code when you have a failing test, and you only refactor when your tests are passing

//test-code-refactor cycle
//  AKA red-green-refactor cycle

//jUnit ans Specs are tools you can use to test Scala code


//--------Setting up your environment for TDD

//continuous integration
//  runs all tests on each check in automatically
//    -Jenkins
//    -Jenkins SBT plugin
//    -Code Coverage

//*Maven is a fine backup, as SBT is relatively new and might not be supported by all testing tools/CI environments (SBT can generate a .pom using the make-pom action)


//-------Using jUnit to test Scala Code

//to add jUnit:
//  libraryDependencies += "junit" % "junit" % "4.10" % "test"
//SBT doesn't recognise jUnit-style test cases by default, so you need this also:
//  libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test"

//not the best because it doesn't understand scala natively