#### Types of polymorphism

1. parametric
2. adhoc: Unlike parametric polymorphism, ad-hoc polymorphism is bound to a type. Depending on the type, different implementations of the method are invoked
3. subtype


Adhoc polymorphism can be implemented by
1. Implicits
2. and type classes

#### Implicits

```
trait Appendable[A] {
  def append(a: A): A
}

class AppendableInt(i: Int) extends Appendable[Int] {
  override def append(a: Int) = i + a
}

class AppendableString(s: String) extends Appendable[String] {
  override def append(a: String) = s.concat(a)
}

implicit def toAppendable(i: Int) = new AppendableInt(i)
implicit def toAppendable(s: String) = new AppendableString(s)

def appendItems[A](a: A, b: A)(implicit ev: A => Appendable[A]) =
  a append b

  /*
     named this implicit conversion “ev” as short for “evidence”,
     which is one of many common terms for an implicit parameter in
      scenarios like this (another quite common one is “witness”).
   */

val res1 = appendItems(2, 3) // res1 is an Int with value 5
val res2 = appendItems("2", "3") // res2 is a String with value "23"
```


#### Type classes

```
trait Appendable[A] {
  def append(a: A, b: A): A
}
object Appendable {
  implicit val appendableInt = new Appendable[Int] {
    override def append(a: Int, b: Int) = a + b
  }
  implicit val appendableString = new Appendable[String] {
    override def append(a: String, b: String) = a.concat(b)
  }
}
def appendItems[A](a: A, b: A)(implicit ev: Appendable[A]) =
  ev.append(a, b)
val res1 = appendItems(2, 3) // returns 5
val res2 = appendItems("2", "3") // returns "23"
```


So when writing your own type class, it’s a nice convention to provide
some initially supported default types in the type class companion object (it’s a convention, not a strict rule)

its easier to override functionality and or add new types in case classes

```
trait Appendable[A] {
  def append(a: A, b: A): A
}

object Appendable {
  implicit val appendableInt = new Appendable[Int] {
    override def append(a: Int, b: Int) = a + b
  }
  implicit val appendableString = new Appendable[String] {
    override def append(a: String, b: String) = a.concat(b)
  }
}

implicit val appendableInt2 = new Appendable[Int] {
  override def append(a: Int, b: Int) = a * b
}

def appendItems[A](a: A, b: A)(implicit ev: Appendable[A]) =
  ev.append(a, b)

val res1 = appendItems(2, 3) // returns 6
```


**Type class is a concept of having a type-dependent interface and
implicit implementations of that interface, with separate implementation
for each supported type. In Scala the best way to model this is to use a
parameterized trait and to put implicit implementations of that trait for
supported types in the trait’s companion object (instead of implicit
values you can also use implicit objects, but why pollute the namespace
with extra types if you don’t have to). Type classes make it easy to add
implementations for new types or to override implementations for
existing types. Since these implementations are implicit, one must take
into account precedence of implicits in Scala when reasoning about
which one will be actually used.**


Further : https://medium.com/@sinisalouc/ad-hoc-polymorphism-and-type-classes-442ae22e5342
