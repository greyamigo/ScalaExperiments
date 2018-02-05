class MyClass(number: Int, text: String) {

  private val classSecret = 42

  def x = MyClass.objectSecret + "?" // MyClass.objectSecret is accessible because it's inside the class. External classes/objects can't access it
}

object MyClass { // it's a companion object because it has the same name
  private val objectSecret = "secret"

  val somethingElse = "somethingElse"

  def y(arg: MyClass) = arg.classSecret -1// arg.classSecret is accessible because it's inside the companion object
  def y1 = objectSecret + 2
}

//MyClass.objectSecret // won't compile
//MyClass.classSecret // won't compile

//new MyClass(-1, "random").objectSecret // won't compile
//new MyClass(-1, "random").classSecret // won't compile

MyClass.y(new MyClass(23,"text"))
MyClass.somethingElse
new MyClass(-1, "random")
MyClass.y1

Nil

(1 to 5).map(i => i * 2)

for (i <- 1 to 5) yield i * 2

for {
  i <- 1 to 5
  _ = 1
} yield i * 2

/*
for {
  x <- c1
  y <- c2
  z <- c3 if z > 0
} yield {...}

c1.flatMap(x => c2.flatMap(y => c3.withFilter(z => z > 0).map(z => {...})))

*/

def sum(n: Int): Int = { // computes the sum of the first n natural numbers
  if(n == 0) {
    n
  } else {
    n + sum(n - 1)
  }
}

def oddNumbers(l: Int, r: Int): Array[Int] = {

  (l to r).filter(_%2==1).toArray

}
