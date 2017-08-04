package com.tfr.scalaInAction.ch8.example.expressionTypeClass

/**
  * Created by Erik on 7/29/2017.
  */
object PayrollSystemWithTypeClass {

  case class Employee(name:String, id:Long)

  trait PayrollProcessor[C[_], A] {
    def processPayroll(payees: Seq[A]): Either[String, Throwable]
  }

  case class USPayroll[A](
    payees: Seq[A])(implicit processor: PayrollProcessor[USPayroll, A]) {
    def processPayroll = processor.processPayroll(payees)
  }

  case class CanadaPayroll[A](
    payees: Seq[A])(implicit processor: PayrollProcessor[CanadaPayroll, A]) {
    def processPayroll = processor.processPayroll(payees)
  }

}

object PayrollProcessors {
  import PayrollSystemWithTypeClass._

  implicit object USPayrollProcessor extends PayrollProcessor[USPayroll, Employee] {
    def processPayroll(payees: Seq[Employee]) = Left("US employees processed")
  }

  implicit object CanadaPayrollProcessor extends PayrollProcessor[CanadaPayroll, Employee] {
    def processPayroll(payees: Seq[Employee]) = Left("Canada employees processed")
  }
}

object PayrollSystemWithTypeClassExtension {
  import PayrollSystemWithTypeClass._

  case class Contractor(name:String)

  case class JapanPayroll[A](
    payees: Seq[A])(implicit processor: PayrollProcessor[JapanPayroll, A]) {
    def processPayroll = processor.processPayroll(payees)
  }
}


object PayrollProcessorsExtension {
  import PayrollSystemWithTypeClassExtension._
  import PayrollSystemWithTypeClass._

  implicit object JapanPayrollProcessor extends PayrollProcessor[JapanPayroll, Employee] {
    def processPayroll(payees: Seq[Employee]) = Left("Japan employees processed")
  }

  implicit object USContractorPayrollProcessor extends PayrollProcessor[USPayroll, Contractor] {
    def processPayroll(payees: Seq[Contractor]) = Left("US contractors processed")
  }

  implicit object CanadaContractorPayrollProcessor extends PayrollProcessor[CanadaPayroll, Contractor] {
    def processPayroll(payees: Seq[Contractor]) = Left("Canada contractors processed")
  }

  implicit object JapanContractorPayrollProcessor extends PayrollProcessor[JapanPayroll, Contractor] {
    def processPayroll(payees: Seq[Contractor]) = Left("Japan contractors processed")
  }
}


object RunPayroll {
  import PayrollSystemWithTypeClass._
  import PayrollProcessors._

  def main(args: Array[String]): Unit = {
    val r = USPayroll(Vector(Employee("Erik", 2))).processPayroll
    println(r)
  }
}

object RunNewPayroll {
  import PayrollSystemWithTypeClass._
  import PayrollProcessors._
  import PayrollSystemWithTypeClassExtension._
  import PayrollProcessorsExtension._

  def main(args: Array[String]): Unit = {
    val r1 = JapanPayroll(Vector(Employee("Erik", 3))).processPayroll
    println(r1)
    val r2 = JapanPayroll(Vector(Contractor("Jimmy"))).processPayroll
    println(r2)
  }
}
