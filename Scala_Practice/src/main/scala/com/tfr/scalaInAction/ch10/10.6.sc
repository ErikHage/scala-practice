//Testing asynchronous messaging systems

//new tool - Awaitility
//  provides a testing DSL for testing asynchronous systems

//case class PlaceOrder(productId: String, quantity: Int, customerId: String)
//
//class OrderingService extends Actor {
//  def act = {
//    react {
//      case PlaceOrder(productId, quantity, customerId) =>
//    }
//  }
//}

//await method to await until the order is saved into the database
//if not then something went wrong while processing the message

//here's the specification for the ordering service

//class OrderingServiceSpecification extends Specification //with AwaitilitySupport
//{
//  "Ordering system" should {
//    "place order asynchronously" in {
//      val s = new OrderingService().start()
//      s ! PlaceOrder("product id", 1, "some customer id")
//      await until { orderSavedInDatabase("some customer id") }
//      1 must_== 1
//    }
//    def orderSavedToDatabase(customerId: String) = true
//  }
//}

//above sends an asynchronous message to the ordering service and waits until the order is saved to the database. the default timeout for Awaitility is 10 seconds, and you can overload await to change that.

//Awaitility doesn't provide any infrastructure to help test async systems, but it does make your examples readable