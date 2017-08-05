//Building a real-time pricing system: Akkaoogle

//find the cheapest deal for a product
//find the cheapest deal in no more than 200 to 300 ms

//Actor message dispatchers:

//  1.Dispatcher
//    The default dispatcher used by the actor system. It's an event-based dispatcher that binds actors to a thread pool. It creates one mailbox per actor.

//  2.PinnedDispatcher
//    Dedicates one thread per actor. It's like creating thread-based actors

//  3.BalancingDispatcher
//    This event-driven dispatcher redistributes work from busy actors to idle actors. All the actors of the same type share one mailbox.

//  4.CallingThreadDispatcher
//    It runs the actor on the calling thread. It doesn't create any new thread. Great for unit testing purposes.

//how to use them
//step 1
//  specify them in the configuration file
//step 2
//  set up the actor with the dispatcher

//example:

//akkaoogle {
//  dispatchers {
//    external-price-calculator-actor-dispatcher {
//      # Dispatcher is the name of the event-based dispatcher
//      type = Dispatcher
//      # What kind of ExecutionService to use
//        executor = "fork-join-executor"
//      # Configuration for the fork-join pool
//        fork-join-executor {
//          # Min number of threads to cap factor-based parallelism number to
//            parallelism-min = 2
//          # Parallelism (threads) ... ceil(available processors * factor)
//          parallelism-factor = 2.0
//          # Max number of threads to cap factor-based parallelism number to
//            parallelism-max = 100
//        }
//      # Throughput defines the maximum number of messages to be
//      # processed per actor before the thread jumps to the next actor.
//      # Set to 1 for as fair as possible.
//      throughput = 100
//    }
//    internal-price-calculator-actor-dispatcher {
//      # Dispatcher is the name of the event-based dispatcher
//      type = BalancingDispatcher
//      # What kind of ExecutionService to use
//        executor = "thread-pool-executor"
//      thread-pool-executor {
//        # Min number of threads to cap factor-based core number to
//          core-pool-size-min = 5
//      }
//    }
//  }
//}

//to use them, use the withDispatcher method instead of Props

//private def createInternalPriceCalculators(initialLoad: Int)(
//  implicit system: ActorSystem) = {
//  (for (i <- 0 until initialLoad) yield
//    system.actorOf(Props[InternalPriceCalculator]
//      .withDispatcher
//      ("dispatchers.internal-price-calculator-actor-dispatcher"),
//      name=("internal-price-calculator" + i))).toList
//}
//private def createExternalPriceCalculators(initialLoad: Int,
//                                           proxies: List[ActorRef])(implicit system: ActorSystem) = {
//  (for (i <- 0 until initialLoad) yield system.actorOf(
//    Props(new ExternalPriceCalculator(proxies))
//      .withDispatcher(
//        "dispatchers.external-price-calculator-actor-dispatcher"),
//    name = ("external-price-calculator" + i))).toList
//}