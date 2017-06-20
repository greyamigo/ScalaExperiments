#### Recursive function needs a return type

With a recursive function, you must specify the return type. For example,
```def fac(n: Int): Int = if (n <= 0) 1 else n * fac(n - 1)```
Without the return type, the Scala compiler couldn’t verify that the type of
``n * fac(n - 1)`` is an Int.

Some programming languages (such as ML and Haskell) can infer
the type of a recursive function, using the Hindley-Milner algorithm. However,
this doesn’t work well in an object-oriented language. Extending the
Hindley-Milner algorithm so it can handle subtypes is still a research problem