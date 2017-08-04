package com.tfr.scalaInAction.ch8.example

/**
  * Created by Erik on 7/29/2017.
  */
sealed trait OrderCompleted
sealed trait IncompleteOrder
sealed trait ItemProvided
sealed trait NoItem
sealed trait AddressProvided
sealed trait NoAddress

case class Order[A,B,C](itemId: Option[String], shippingAddress: Option[String])

object Order {
  def emptyOrder = Order[IncompleteOrder, NoItem, NoAddress](None, None)
}

object PhantomOrderingSystem {

  def addItem[A,B](item: String, o: Order[A, NoItem, B]) = {
    println("Adding item: " + item + " to order " + o)
    o.copy[A, ItemProvided, B](itemId = Some(item))
  }

  def addShipping[A,B](address: String, o: Order[A, B, NoAddress]) = {
    println("Adding shipping: " + address + " to order " + o)
    o.copy[A, B, AddressProvided](shippingAddress = Some(address))
  }

  def placeOrder(o: Order[IncompleteOrder, ItemProvided, AddressProvided]) = {
    println("Placing order " + o)
    o.copy[OrderCompleted, ItemProvided, AddressProvided]()
  }

  def main(args: Array[String]): Unit = {
    val o = Order.emptyOrder
    val o1 = addItem("Some book", o)
    val o2 = addShipping("some address", o1)
    placeOrder(o2)

//    val o5 = Order.emptyOrder
//    val o6 = addShipping("some address", o5)
//    val o7 = addItem("Some book", o6)
//    placeOrder(o5) //compiler error

  }
}

