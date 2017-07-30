//Challenges with concurrent programming

//  1. only a handful of programmers know how to write correct, concurrent application of a program. The correctness of the program is important
//  2. debugging multithreaded programs is difficult. the same program that causes deadlock in production might not have any locking issues when debugging locally. Sometimes threading issues show up after years of running in production
// 3. Threading encourages shared state concurrency and it's hard to make programs run in parallel because of locks, semaphores, and dependencies between threads

//Main culprit is STATE!

//threads are too low of an abstraction - too close to how hardware actually works

//locks are used to control the way shared data is modified, but they have problems:
// they don't compose
// easy to use too many or too few locks
// deadlocks and race conditions
// error recovery is hard

//Three most popular trends in concurrency

//1.Software transactional memory - similar to database transactions
//2.Dataflow Concurrency - share values across multiple tasks or threads
//3.Message-passing concurrency - components communicate by sending messages (actor model is one example)
