val tryList = List(-85,-23,66,55,84,-46,-96,5,1,1,-47,-89)

// Length of List without using count, size or length operators (or their equivalents)
def f(arr:List[Int]):Int = {
  arr.foldLeft(0)((acc:Int,x:Int) => acc + 1)
}

f(tryList)
// Update the values of a list with their absolute values.
def f1(arr:List[Int]):List[Int] = {
  arr.foldRight(List[Int]())((x: Int, acc:List[Int])=> {
    if(x > 0) x::acc else (x * -1)::acc
  })
}

f1(tryList)

// Compute and Return the value of e^x

def f(x: Float):Float= {
  (0 to 9).foldRight(0.0)((y:Int, acc)=>
    acc + Math.pow(x, y)/(((1 to y) :\ 1) ( _ * _ ))
  ).toFloat
}

f(20.00.toFloat)