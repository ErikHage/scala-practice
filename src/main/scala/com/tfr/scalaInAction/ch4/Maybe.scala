package com.tfr.scalaInAction.ch4

/**
  * Created by Erik on 7/14/2017.
  */
sealed abstract class Maybe[+A] {
  def isEmpty(): Boolean
  def get:A
}

final case class Just[A](value:A) extends Maybe[A] {
  def isEmpty() = false
  def get = value
}

case object Nil extends Maybe[Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("Nil.get")
}
