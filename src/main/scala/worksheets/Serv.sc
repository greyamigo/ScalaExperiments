def spicyChicken(min: Int, max: Int) = (min to max).map((x:Int)=>
   if (x%5 ==0 && x%3 ==0) s"$x SpicyChicken!"
   else if (x % 3 ==0) s"$x Spicy"
   else if (x% 5 ==0) s"$x Chicken"
  else s"$x"
).map(println)


def spicyChicken2(min: Int, max: Int) = (min to max).map((x:Int)=>

  (x % 5, x % 3) match {
    case (0 , 0) => s"$x SpicyChicken!"
    case (0, _) =>  s"$x Chicken"
    case (_, 0) =>  s"$x Spicy"
    case _      =>  s"$x"
  }).map(println)




spicyChicken(1, 15)
spicyChicken2(1, 15)