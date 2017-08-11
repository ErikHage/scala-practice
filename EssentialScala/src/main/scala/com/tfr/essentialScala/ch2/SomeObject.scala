package com.tfr.essentialScala.ch2

/**
  * Created by Erik on 8/6/2017.
  */
object SomeObject extends App {

  def square(in: Double): Double = in * in

  assert(square(2.0) == 4.0)
  assert(square(3.0) == 9.0)
  assert(square(-2.0) == 4.0)

}
