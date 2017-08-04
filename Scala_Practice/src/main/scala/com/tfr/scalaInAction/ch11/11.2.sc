val a = 1
//Using Scala classes in Java

//traits:
//  with only abstract methods -> Java Interface
//  with concrete methods ->

trait Persistable[T]{
  def getEntity: T

  def save(): T = {
    persistToDb(getEntity)
    getEntity
  }
  private def persistToDb(t: T) = {
    //some stuff
  }
}

// => java

//Persistable interface
//and Persistable$class.class //concrete methods

//scala classes don't have bean-style get and set methods


//-------Using Scala annotations

//declare members as var and annotate
//class ScalaBean(@scala.reflect.BeanProperty var name:String)
//  generates both java and scala style getter ans setter methods

//scala doesn't have the throws keyword to describe methods
//annotations

trait RemoteLogger extends java.rmi.Remote {
  @throws(classOf[java.rmi.RemoteException])
  def log(m: String)
}

//only a getter

//import javax.persistence.Id
//class A {   @(Id @beanGetter) @BeanProperty val x = 0 }


