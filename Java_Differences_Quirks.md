#### Break and Continue
Scala has no break or continue statements to break out of a loop.
What to do if you need a break? Here are a few options:
1. Use a Boolean control variable instead.
2. Use nested functions—you can return from the middle of a function.
3. Use the break method in the Breaks object:
```
import scala.util.control.Breaks._
breakable {
for (...) {
if (...) break; // Exits the breakable block
...
}
}
```

Here, the control transfer is done by throwing and catching an exception,
so you should avoid this mechanism when time is of the essence.

#### Procedures

Scala has a special notation for a function that returns no value. If the function
body is enclosed in braces without a preceding = symbol, then the return type is Unit.
Such a function is called a procedure. A procedure returns no value, and you only
call it for its side effect. For example, the following procedure prints a string inside
a box
Because the procedure doesn’t return any value, we omit the = symbol.
def box(s : String) { // Look carefully: no =
```
val border = "-" * s.length + "--\n"
println(border + "|" + s + "|\n" + border)
}
```


#### Exceptions

As in Java, the objects that you throw need to belong to a subclass of
java.lang.Throwable. However, unlike Java, Scala has no “checked” exceptions—you
never have to declare that a function or method might throw an exception.

In Java, “checked” exceptions are checked at compile time. If your
method might throw an IOException, you must declare it. This forces
programmers to think where those exceptions should be handled, which is
a laudable goal. Unfortunately, it can also give rise to monstrous method
signatures such as
```void doSomething() throws IOException, InterruptedException, ClassNotFoundException```