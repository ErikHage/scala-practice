package com.tfr.scalaInAction.ch10

import org.scalacheck.{Prop, Properties}

/**
  * Created by Erik on 7/31/2017.
  */
object StringSpecification extends Properties("String") {

  property("reverse of reverse gives you the same string back") =
    Prop.forAll((a: String) => a.reverse.reverse == a)

  property("startsWith") = Prop.forAll { (x: String, y: String) =>
    x.startsWith(y) == x.reverse.endsWith(y)
  }

  property("string comparison - WILL FAIL") = Prop.forAll {
      (x: String, y: String) => (x > y) == (x.reverse > y.reverse)
  }

}
