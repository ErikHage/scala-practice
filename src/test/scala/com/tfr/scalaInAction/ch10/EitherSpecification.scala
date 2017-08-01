package com.tfr.scalaInAction.ch10

import org.scalacheck.{Prop, Properties}

/**
  * Created by Erik on 7/31/2017.
  */
object EitherSpecification extends Properties("Either") {

  // 1.Either will have value on either Left or Right, but not both at the same time
  property("isLeft or isRight not both") = Prop.forAll((e: Either[Int, Int]) =>
    e.isLeft != e.isRight)

  // 2.fold on the Left should produce the value contained by Left
  property("left value") = Prop.forAll { (n: Int) =>
    Left(n).fold(x => x, b => sys.error("fail")) == n
  }

  // 3.fold on the Right should produce the value contained by Right
  property("right value") = Prop.forAll { (n: Int) =>
    Right(n).fold(b => sys.error("fail"), x => x) == n
  }

  // 4.swap returns the Left value to the Right and vice versa
  property("swap values") = Prop.forAll { (e: Either[Int, Int]) =>
    e match {
      case Left(a) => e.swap.right.get == a
      case Right(b) => e.swap.left.get == b
    }
  }

  // 5.getOrElse on Left returns the value from Left or the given argument if this is Right
  property("getOrElse") = Prop.forAll { (e: Either[Int, Int], or: Int) =>
    e.left.getOrElse(or) == (e match {
      case Left(a) => a
      case Right(_) => or
    })
  }

  // 6.forAll on Right returns true if Left or returns the result of the application of the given function to the right value
  property("forAll") = Prop.forAll { (e: Either[Int, Int]) =>
    e.right.forall(_ % 2 == 0) == (e.isLeft || e.right.get % 2 == 0)
  }

}
