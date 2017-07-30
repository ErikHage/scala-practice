package com.tfr.scalaInAction.ch8.example

/**
  * Created by Erik on 7/29/2017.
  */
trait Connection {
  def query(q: String): String
}
trait Logger {
  def log(l:String): Unit
}
trait RequiredServices { //declares all the services that could be used by the product finder
def makeDatabaseConnection: Connection
  def logger: Logger
}
trait TestServices extends RequiredServices {
  def makeDatabaseConnection = new Connection {
    def query(q:String): String = {
      println("query: " + q)
      "Test"
    }
  }
  def logger = new Logger {
    def log(l:String): Unit = println(l)
  }
}
trait ProductFinder { this: RequiredServices =>
  def findProduct(productId: String): Unit = {
    val c = makeDatabaseConnection
    c.query("find the lowest price")
    logger.log("querying database...")
  }
}
object FinderSystem extends ProductFinder with TestServices {
  def main(args: Array[String]): Unit = {
    findProduct("test")
  }
}
