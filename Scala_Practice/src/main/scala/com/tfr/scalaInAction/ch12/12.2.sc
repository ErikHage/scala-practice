//Simple concurrency with Akka

//use concurrency to SCALE UP

//techniques available in Akka:
// 1. Actors : objects that process messages asynchronously and encapsulate state. message-passing concurrency
// 2. Software Transactional Memory (STM) : a concurrency model analogous to database transactions for controlling access to a shared state. It’s a better alternative to locks and provides composability
// 3. Agents : Agents provide abstraction over mutable data. They only allow you to mutate the data through an asynchronous write action.
// 4. Dataflow : is deterministic. This means that it behaves the same every time you execute it. So if your problem deadlocks the first time, it will always deadlock, helping you to debug the problem. Akka implements Oz-style[a] dataflow concurrency using Future.

//can be combined!


//-------Remote Actors

//actors can communicate across multiple JVMs
//messages serialized with google protocol buffer : like xml but smaller and faster
//communication though JBoss Netty : non blocking I/O (to be replaced by an actor-based I/O library called Actor I/O)

//transparent remoting: remoteness of the actor is completely configured at deployment time (can use local for dev and remote during deployment)

//dependencies for remote servers
