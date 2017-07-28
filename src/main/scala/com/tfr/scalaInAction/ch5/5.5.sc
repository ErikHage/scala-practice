//Algebraic Data Types
//a type that represents a set of values (finite or infinite)

object ADT {
  sealed trait Account

  case class CheckingAccount(accountId: String) extends Account
  case class SavingAccount(accountId: String, limit: Double) extends Account
  case class PremiumAccount(corporateId: String, accountHolder: String) extends Account

  def printAccountDetails(account: Account): Unit = account match {
    case CheckingAccount(accountId) => println("Account id " + accountId)
    case SavingAccount(accountId, limit) => println("Account id " + accountId + " , " + limit)
      //missing case for PremiumAccount will cause compiler warning
  }
}


//total functions
//  a function that knows how to handle all the values of an algebraic data type and always produces a result