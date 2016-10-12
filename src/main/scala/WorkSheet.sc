def f(num:Int,arr:List[Int]) ={
arr.foldLeft(List[Int]())((x, y)=>
  x ::: (1 to num).map(_ => y).toList)
}

f(4, List(1,2))
