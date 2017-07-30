package com.tfr.scalaInAction.ch8.example

/**
  * Created by Erik on 7/29/2017.
  */
trait XmlConverter[A] {
  def toXml(a: A): String
}

case class Movie(name:String, year:Int, rating:Double)

object Converters {
  implicit object MovieConverter extends XmlConverter[Movie] {
    override def toXml(a: Movie): String =
<movie>
  <name>{a.name}</name>
  <year>{a.year}</year>
  <rating>{a.rating}</rating>
</movie>.toString
  }

  object MovieConverterWithoutRating extends XmlConverter[Movie] {
    override def toXml(a: Movie): String =
<movie>
  <name>{a.name}</name>
  <year>{a.year}</year>
</movie>.toString
  }
}

object ConvertersMain {
  import Converters._

  def toXml[A](a: A)(implicit converter: XmlConverter[A]): String = converter.toXml(a)

  def main(args: Array[String]): Unit = {
    val m = Movie("Inception", 2010, 10)
    val xml = toXml(m)
    println(xml)

    //or you can pass your own
    val xml2 = toXml(m)(MovieConverterWithoutRating)
    println(xml2)
  }
}
