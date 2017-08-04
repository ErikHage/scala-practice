val x = 1
//Building Higher abstractions with Monads
import com.tfr.scalaInAction.ch5.examples.Stubs._
// Monads let you compose functions that don’t compose well, such as functions that have side effects.
// Monads let you order computation within functional programming so that you can model sequences of actions.


//------------Managing state using monads

//retail pricing example w/o monads
object PriceCalculatorWithoutMonad {

  case class PriceState(productId: String, stateCode: String, price: Double)

  def findBasePrice(productId: String, stateCode:String): PriceState = {
    val basePrice = findTheBasePrice(productId)
    PriceState(productId, stateCode, basePrice)
  }

  def applyStateSpecificDiscount(ps: PriceState): PriceState = {
    val discount = findStateSpecificDiscount(ps.productId, ps.stateCode)
    ps.copy(price= ps.price - discount)
  }

  def applyProductSpecificDiscount(ps: PriceState): PriceState = {
    val discount = findProductSpecificDiscount(ps.productId)
    ps.copy(price = ps.price - discount)
  }

  def applyTax(ps:PriceState): PriceState = {
    val tax = calculateTax(ps.productId, ps.price)
    ps.copy(price = ps.price + tax)
  }

  def calculatePrice(productId:String, stateCode:String): Double = {
    val a = findBasePrice(productId, stateCode)
    val b = applyStateSpecificDiscount(a)
    val c = applyProductSpecificDiscount(b)
    val d = applyTax(c)
    d.price
  }
}


//STATE MONAD
//  threads the changing state across multiple operations transparently

object StateMonad {



  trait State[S, +A] {
    def apply(s: S): (S, A)

    def map[B](f: A=>B): State[S,B] = State.state(apply(_) match {
      case (s,a) => (s, f(a))
    })

    def flatMap[B](f: A=>State[S,B]): State[S,B] = State.state(apply(_) match {
      case (s,a) => f(a)(s)
    })
  }

  object State {
    def state[S, A](f: S => (S, A)) = new State[S, A] {
      def apply(s: S) = f(s)
    }

    def init[S]: State[S, S] = {
      state[S, S](s => (s, s))
    }

    def modify[S](f: S => S) = {
      init[S] flatMap (s => state(_ => (f(s), ())))
    }

    def gets[S,A](f: S=>A): State[S,A] = {
      init[S] flatMap (s => state(_ => (s, f(s))))
    }
  }
}

object PriceCalculator {
  import StateMonad.State._

  case class PriceState(productId: String, stateCode: String, price: Double)

  def findBasePrice(ps: PriceState): Double = {
    val basePrice = findTheBasePrice(ps.productId)
    basePrice
  }

  def applyStateSpecificDiscount(ps: PriceState): Double = {
    val discount = findStateSpecificDiscount(ps.productId, ps.stateCode)
    ps.price - discount
  }

  def applyProductSpecificDiscount(ps: PriceState): Double = {
    val discount = findProductSpecificDiscount(ps.productId)
    ps.price - discount
  }

  def applyTax(ps:PriceState): Double = {
    val tax = calculateTax(ps.productId, ps.price)
    ps.price + tax
  }

  def calculatePrice(productId:String, stateCode:String): Double = {
    def modifyPriceState(f: PriceState => Double) =
      modify[PriceState](s => s.copy(price = f(s)))

    def logStep(f: PriceState => String) = gets(f)

    val stateMonad = for {
      _ <- modifyPriceState(findBasePrice)
      a <- logStep(s => "Base Price " + s)
      _ <- modifyPriceState(applyStateSpecificDiscount)
      b <- logStep(s => "After State Discount " + s)
      _ <- modifyPriceState(applyProductSpecificDiscount)
      c <- logStep(s => "After Product Discount " + s)
      _ <- modifyPriceState(applyTax)
      d <- logStep(s => "After Tax " + s)
    } yield a :: b :: c :: d :: Nil

    val initialPriceState = PriceState(productId, stateCode, 0.0)
    val (finalPriceState, log) = stateMonad.apply(initialPriceState)
    val finalPrice = finalPriceState.price

    finalPrice
  }

  //without using for-comprehension
  def calculatePrice2(productId: String, stateCode: String): Double = {
    def modifyPriceState(f: PriceState => Double) =
      modify[PriceState](s => s.copy(price = f(s)))

    val stateMonad = modifyPriceState(findBasePrice) flatMap {
      a => modifyPriceState(applyStateSpecificDiscount) flatMap {
        b => modifyPriceState(applyProductSpecificDiscount) flatMap {
          c => modifyPriceState(applyTax) map {
            d => ()
          }
        }
      }
    }

    val initialPriceState = PriceState(productId, stateCode, 0.0)
    val finalPriceState = stateMonad.apply(initialPriceState)._1
    val finalPrice = finalPriceState.price

    finalPrice
  }
}

val p = PriceCalculator.calculatePrice("Some thing", "NY")


//------------Building blocks for Monads

//flatMap and map combination

//monad is a container, map and flatMap are the only two ways to get to get into the value stored in the container
//both take a function as a parameter and create a new instance of a monad by applying the function to allow composability
//in both cases you end up with a new instance of a monad

//why do you need both map and flatMap?
//see calculatePrice2 method above for use w/o for comprehension


//Recipe to build your own monad

//  1. Define both flatMap and map for the interface
//  2. Decide on a way to get the value of the monad (pattern matching or apply)
//  3. Confirm to the monadic laws
