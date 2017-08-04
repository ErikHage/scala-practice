//Types of types in Scala
//---------Structural Types

def close(closable: { def close: Unit }) = {
  closable.close
}

//the parameter is defined by the type { def close:Unit } structure
//you can pass instances of any type to this function as long as it implements the def close: Unit method

//can provide a name to this type:
type Closable = { def close(): Unit }
def close(closable: Closable) = {
  closable.close()
}

//can be done with multiple methods

type Profile = {
  def name: String
  def address: String
}

//create new values of a structural type:

val erikHage = new {
  def name = "Erik"
  def address = "Astoria, NY"
}

//use it to reduce class hierarchies and simplify a code base
//before:
//trait Worker {
//  def salary: BigDecimal
//  def bonusPercentage: Double
//}
//trait HourlyWorker {
//  def hours: Int
//  def salary: BigDecimal
//}
//case class FullTimeWorker(val salary: BigDecimal, ...)
//extends Worker
//case class PartTimeWorker(val hours: Int, val salary: BigDecimal, ...)
//extends HourlyWorker
//case class StudentWorker(val hours: Int, val salary: BigDecimal, ...)
//extends HourlyWorker

//after:
trait SalariedWorker {
  def salary: BigDecimal
}
trait Worker extends SalariedWorker {
  def bonusPercentage: Double
}
trait HourlyWorker extends SalariedWorker {
  def hours: Int
}
def amountPaidAsSalary(workers: Vector[SalariedWorker]) = {
}
type Salaried = {def salary: BigDecimal }
//using structural type to define a function without defining new types:
def amountPaidAsSalary2(workers: Vector[Salaried]) = {

}

//DOWNSIDE -> comparatively slow bc it uses reflection under the hood


//-------------Higher-Kinded Types
//also known as type constructors: they accept other types as a parameter and create a new type
//kinds are to types as types are to values

//you have a Vector of elements and want to apply a function to each element of the vector
def fmap[A,B](xs: Vector[A], f: A=>B): Vector[B] = xs map f

//fmap applies the given f function to all the elements of vector. if you want to apply the function to Option, you need to create another function:
def fmap[A,B](r: Option[A], f: A=>B): Option[B] = r map f

//both look similar and only differ in the first parameter
//how to define a common fmap for both?
trait Mapper[F[_]] {
  def fmap[A,B](xs: F[A], f: A=>B): F[B]
}

//the Mapper type is parametrized by the F[_] type
//F is a higher kinded type because it takes another type parameter denoted by _
//implement fmap for Vector and Option:
def VectorMapper = new Mapper[Vector] {
  def fmap[A,B](xs:Vector[A], f: A=>B): Vector[B] = xs map f
}
def OptionMapper = new Mapper[Option] {
  def fmap[A,B](r: Option[A], f: A=>B): Option[B] = r map f
}

//raises abstraction level higher and define interfaces to work across various types

//for Function0
def Function0Mapper = new Mapper[Function0] {
  def fmap[A,B](r: Function0[A], f: A=>B) = new Function0[B] {
    def apply(): B = f(r.apply)
  }
}

//use that to compose 2 functions
val newFunction = Function0Mapper.fmap(() => "one", (s:String) => s.toUpperCase)

//TYPE PROJECTION
//type projection T#x references the type member x of type T

trait X {
  type E
}
type EE = X#E

//how is this useful?
//look at Either
Either.cond(test = true, "one", new RuntimeException)
//can use type projection to hide one type parameter and make it constant
def fmap[A,B](r: Either[X,A], f: A=>B): Either[X,B] = r match {
  case Left(a) => Left(a)
  case Right(a) => Right(f(a))
}

def EitherMapper[X] = new Mapper[({type E[A] = Either[X,A]})#E] {
  def fmap[A,B](r: Either[X,A], f: A=>B): Either[X,B] = r match {
    case Left(a) => Left(a)
    case Right(a) => Right(f(a))
  }
}
//Left is hidden, Right is exposed


//--------Phantom Types

//don't provide constructors
//you only need these types during compile time to enforce constraints
//see example.PhantomTypes







