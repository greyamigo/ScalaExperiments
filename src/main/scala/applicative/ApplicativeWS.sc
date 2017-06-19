val curriedFunc = List(1, 2, 3, 4) map {(_: Int) * (_:Int)}.curried


curriedFunc map {_(9)}

import scalaz._,Scalaz._


val testOption = Option(5)

//map --> takes a function and a functor(testOption) and applies the function inside the functor value

testOption.map((x:Int)=> (x+1))

// <*> takes a functor that has a function in it and another functor and extracts that function from first functor and then maps it over second one

testOption <*> ((x:Int) => x + 1).some



1.some <* 2.some

none <* 2.some

1.some *> 2.some

none *> 2.some



^(3.some, 5.some) {_ + _}

^(3.some, none[Int]) {_ + _}


/*
  The new ^(f1, f2) {...} style is not without the problem though.
  It doesn’t seem to handle Applicatives that takes two type parameters like Function1, Writer, and Validation.
  There’s another way called Applicative Builder, which apparently was the way it worked in Scalaz 6,
  got deprecated in M3, but will be vindicated again because of ^(f1, f2) {...}’s issues.
 */

(3.some |@| 5.some) {_ + _}