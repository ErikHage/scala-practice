package com.tfr.scalaInAction.ch8.example

/**
  * Created by Erik on 7/29/2017.
  */
trait OrderingSystem {
  type O <: Order
  type I <: Inventory
  type S <: Shipping

  trait Order {
    def placeOrder(i: I): Unit
  }
  trait Inventory {
    def itemExists(order: O): Boolean
  }
  trait Shipping {
    def scheduleShipping(order: O): Long
  }

  trait Ordering { this: I with S =>
    def placeOrder(o:O): Option[Long] = {
      if(itemExists(o)) {
        o.placeOrder(this)
        Some(scheduleShipping(o))
      } else {
        None
      }

    }
  }
}

//example implementation
object BookOrderingSystem extends OrderingSystem {
  type O = BookOrder
  type I = AmazonBookStore
  type S = UPS

  class BookOrder extends Order {
    def placeOrder(i: AmazonBookStore): Unit = {
      //some stuff
    }
  }

  trait AmazonBookStore extends Inventory {
    override def itemExists(order: BookOrder) = true
  }

  trait UPS extends Shipping {
    override def scheduleShipping(order: BookOrder) = 1
  }

  object BookOrdering extends Ordering with AmazonBookStore with UPS
}
