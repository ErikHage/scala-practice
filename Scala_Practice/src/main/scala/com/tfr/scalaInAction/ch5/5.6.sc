//Why does functional programming matter?

//composability

def even: Int => Boolean = _ % 2 == 0
def not: Boolean => Boolean = !_
def filter[A](criteria: A => Boolean)(col: Traversable[A])= col.filter(criteria)
def map[A, B](f: A => B)(col: Traversable[A]) = col.map(f)

def evenFilter = filter(even) _
def double: Int => Int = _ * 2

def doubleAllEven = evenFilter andThen map(double)

def odd: Int => Boolean = not compose even
def oddFilter = filter(odd) _
def doubleAllOdd = oddFilter andThen map(double)

//compose vs andThen
//  compose is right-associative

//philosophy
//  Write pure functions that do one thing and do it well
//  Write functions that can compose with other functions