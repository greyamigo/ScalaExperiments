// Fail In Scala

val source = Math.round(Math.random*100)

def nextNmberPlease(): Long = {
  if(source <= 60) source
  else throw new Exception("above 60")
}

nextNmberPlease()

