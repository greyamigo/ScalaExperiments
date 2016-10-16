def f(num:Int,arr:List[Int]) ={
arr.foldLeft(List[Int]())((x, y)=>
  x ::: (1 to num).map(_ => y).toList)
}

f(4, List(1,2))


def f1(delim:Int,arr:List[Int]):List[Int] = arr.filter(_ < delim)

f1(3,List(1,2,3,4,5,6,7,8))

def f2(arr:List[Int]):List[Int] = {
  var i = 1
  arr.filter(f=> {
    val x = if(i%2 == 1) false else true
    i = i+1
    x
  })
}


f2(List(1,2,3,4,5,6,7,8))

def f3(num:Int) : List[Int] = (1 to num).map(f => f).toList
f3(10)

val tryList = List(3,22,1,4,5,6,9,8,7,6,0,3,5,6,8,8,55,8)
tryList.reverse
tryList.foldLeft(List[Int]())((x: List[Int], y:Int)=> y::x)

tryList.foldRight(0)((x:Int , y:Int)=> if(x%2!=0) x+y else y)