//---------Working with List and ListBuffer

val languages = Seq("Scala", "Haskell", "OCaml", "ML")
languages(1)
//languages(10) // IndexOutOfBoundsException - partial function behavior

//PartialFunction methods andThen, orElse to avoid when you don't have elements
val default: PartialFunction[Int, String] = {
  case _ => "Is it a functional language?"
}
val languagesWithDefault = languages orElse default
languagesWithDefault(1)
languagesWithDefault(10)

//ListBuffer is a mutable Sequence
import scala.collection.mutable.ListBuffer
val buf = ListBuffer(1.2, 3.4, 5.6)
//assignment and update call are identical
buf(2) = 10
buf.update(1,5)
buf


//-----------------Working with Set and SortedSet

val frameworks = Set("Lift", "Akka", "Playframework", "Scalaz")
frameworks contains "Lift"
frameworks contains "Scalacheck"
frameworks("Lift")

//other Set methods: contains, ++, &, intersect, |, union, &~, diff

val frameworks2 = Set() + "Akka" + "Lift" + "Scalaz"
val frameworks3 = frameworks2 - "Lift"

val mFrameworks = collection.mutable.Set[String]()
mFrameworks += "Akka" += "Lift"
mFrameworks += "Scalacheck"
mFrameworks -= "Lift"

Set(1,2,3) ++ Set(3,4,5) //order not maintained
import collection.immutable.SortedSet
SortedSet(1,2,3) ++ SortedSet(3,4,5) //order maintained


//-----------------Working with Map and Tuple

val m = Map((1,"1st"),(2,"2nd"))
val m2 = Map(1 -> "1st", 2 -> "2nd")

//appply method:
m(1)
//m(3) //NoSuchElementException

//better way is to use get, which returns an Option
m.get(1)
m.get(3)
m.keys
m.values

//other Map methods: getOrElse, +, ++, filterKeys, mapValues, getOrElseUpdate

val artists  = Map(
  "Pink Floyd" -> "Rock",
  "Led Zeppelin" -> "Rock",
  "Michael Jackson" -> "Pop",
  "Above & Beyond" -> "Trance")

artists filter { t => t._2 == "Rock" }
//alternately
for(t <- artists; if t._2 == "Rock") yield t


//----------------Under the hood of for-comprehension

case class Artist(name: String, genre: String)
case class ArtistWithAlbums(artist: Artist, albums: List[String])

val artists2 = List(Artist("Pink Floyd", "Rock"),
  Artist("Led Zeppelin", "Rock"),
  Artist("Michael Jackson", "Pop"),
  Artist("Above & Beyond", "trance")
)
for(Artist(name, genre) <- artists2; if genre == "Rock") yield name
//under the hood would get translated to something like this:
artists2 withFilter {
  case Artist(name, genre) => genre == "Rock"
} map {
  case Artist(name, genre) => name
}

val artistsWithAlbums = List(
  ArtistWithAlbums(Artist("Pink Floyd", "Rock"),
    List("Dark side of the moon", "Wall")),
  ArtistWithAlbums(Artist("Led Zeppelin", "Rock"),
    List("Led Zeppelin IV", "Presence")),
  ArtistWithAlbums(Artist("Michael Jackson", "Pop"),
    List("Bad", "Thriller")),
  ArtistWithAlbums(Artist("Above & Beyond", "trance"),
    List("Tri-State", "Sirens of the Sea"))
)
for { ArtistWithAlbums(artist, albums) <- artistsWithAlbums
      album <- albums
      if artist.genre == "Rock"
} yield album
//under the hood:
artistsWithAlbums flatMap {
  case ArtistWithAlbums(artist, albums) => albums withFilter {
    album => artist.genre == "Rock"
  } map {
    album => album
  }
}


//------------------------Use Option, not Null

//scala convention to return Option when possible null value
artists.get("Pink Floyd")
artists.get("Abba")

//scala.Either represents one of two possible meaningful results
def throwableToLeft[T](block: => T): Either[Throwable,T] = {
  try {
    Right(block)
  } catch {
    case ex: Throwable => Left(ex)
  }
}

val r = throwableToLeft {
  new java.net.Socket("localhost", 4444)
}
r match {
  case Left(e) => e.printStackTrace()
  case Right(e) => println(e)
}