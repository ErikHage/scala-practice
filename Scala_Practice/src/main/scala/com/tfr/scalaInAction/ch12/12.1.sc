//The philosophy behind Akka

//make it easier for developers to build correct, concurrent, scalable, and fault-tolerant applications
//provides a higher level of abstractions to deal with concurrency, scalability, and faults
//  fault-tolerance: Supervision
//  scalability: Routing, Remoting, Cluster
//  concurrency: Actors, STM, Agents, Dataflow

//an event-based platform at it's core
//relies on actors for message passing an scalability
//local and remote actors
//local actors with routing -> scale up
//remote actors -> scale out