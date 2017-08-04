package com.tfr.scalaInAction.ch5.examples

/**
  * Created by Erik on 7/28/2017.
  */
object Combinators {
  implicit def kestrel[A](a:A) = new {
    def tap(sideEffect: A=>Unit): A = {
      sideEffect(a)
      a
    }
  }

  case class Person(firstName:String, lastName:String)
  case class Mailer(mailAddress:String) {
    def mail(body:String) = {
      println("send mail here... " + mailAddress)
    }
  }

  object Main2 {
    def main(args: Array[String]): Unit = {
      Person("Erik", "Hage").tap(p => {
        println("FirstName: " + p.firstName)
        Mailer("some address").mail("")
      }).lastName
    }
  }

}
