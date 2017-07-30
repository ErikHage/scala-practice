package com.tfr.scalaInAction.ch8.example.expression

/**
  * Created by Erik on 7/29/2017.
  */
trait PayrollSystem {
  case class Employee(name: String, id: Long)

  type P <: Payroll

  trait Payroll {
    def processEmployees(employees: Vector[Employee]): Either[String, Throwable]
  }

  def processPayroll(p: P): Either[String, Throwable]
}


trait USPayrollSystem extends PayrollSystem {
  class USPayroll extends Payroll {
    def processEmployees(employees: Vector[Employee]) = Left("US Payroll")
  }
}
trait CanadaPayrollSystem extends PayrollSystem {
  class CanadaPayroll extends Payroll {
    def processEmployees(employees: Vector[Employee]) = Left("Canada Payroll")
  }
}
//easily add a new country
trait JapanPayrollSystem extends PayrollSystem {
  class JapanPayroll extends Payroll {
    def processEmployees(employees: Vector[Employee]) = Left("Japan Payroll")
  }
}
//adding new functionality
trait ContractorPayrollSystem extends PayrollSystem {
  type P <: Payroll
  case class Contractor(name: String)
  trait Payroll extends super.Payroll {
    def processContractors(contractors: Vector[Contractor]): Either[String, Throwable]
  }
}
//extending the new functionality for contractors
trait USContractorPayrollSystem extends USPayrollSystem with
  ContractorPayrollSystem {
  class USPayroll extends super.USPayroll with Payroll {
    def processContractors(contractors: Vector[Contractor]) =
      Left("US contract payroll")
  }
}
trait CanadaContractorPayrollSystem extends CanadaPayrollSystem with
  ContractorPayrollSystem {
  class CanadaPayroll extends super.CanadaPayroll with Payroll {
    def processContractors(contractors: Vector[Contractor]) =
      Left("Canada contract payroll")
  }
}
trait JapanContractorPayrollSystem extends JapanPayrollSystem with
  ContractorPayrollSystem {
  class JapanPayroll extends super.JapanPayroll with Payroll {
    def processContractors(contractors: Vector[Contractor]) =
      Left("Japan contract payroll")
  }
}

//object USPayrollInstance extends USPayrollSystem {
//  type P = USPayroll
//  def processPayroll(p: USPayroll): Unit = {
//    val employees: Vector[Employee] = Vector()
//    val result = p.processEmployees(employees)
//    println(result)
//  }
//}

object RunNewPayroll {

  object USNewPayrollInstance extends USContractorPayrollSystem {
    type P = USPayroll
    def processPayroll(p: USPayroll) = {
      p.processEmployees(Vector(Employee("Erik",1)))
      p.processContractors(Vector(Contractor("James")))
      Left("Payroll processed successfully")
    }
  }
  import USNewPayrollInstance._
  def main(args: Array[String]): Unit = run()
  def run(): Unit = {
    val usPayroll = new USPayroll
    println(USNewPayrollInstance.processPayroll(usPayroll))
  }
}

