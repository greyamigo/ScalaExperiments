import org.joda.time.LocalDate

implicit def toDate(dateStr: String): LocalDate = new LocalDate(dateStr)

def getDatesToProcessAndDateMap(

 availableSrcDates: List[LocalDate],
 startDate: LocalDate,
 endDate: LocalDate
 ): (List[LocalDate], Map[LocalDate, List[LocalDate]])
= {
 val keyList = if (availableSrcDates.contains(startDate))
   availableSrcDates.filter( _.compareTo(startDate) >= 1)
   else {
   val newStartDate =availableSrcDates.filter( _.compareTo(startDate) < 1).last
   availableSrcDates.filter( _.compareTo(newStartDate) >= 1)
 }

 (keyList,keyList.map((f: LocalDate) => { (f , internalFunction(keyList, f))}).toMap)

 def internalFunction(keyList:List[LocalDate], f:LocalDate ):List[LocalDate] ={








  List(new LocalDate("2017-11-22"))
 }

}

// sort list
// start date in list --> {P
//   start date key  till it hits next value in list , all values , incremented by 1 ( all missing dates)
//   second elemnt will be the next processing date in the list

//start date not in list , first key will be the last source availble date go back


getDatesToProcessAndDateMap(
 List("2016-12-30", "2016-12-31","2017-01-03"),
 "2017-01-01",
 "2016-12-30")


getDatesToProcessAndDateMap(List("2016-12-30", "2017-01-03", "2017-01-04", "2017-01-06"),
"2016-12-30",
"2017-01-06"
)

getDatesToProcessAndDateMap(List("2016-12-30", "2017-01-03", "2017-01-04", "2017-01-06"),
  "2016-12-31",
  "2017-01-06"
 )


getDatesToProcessAndDateMap(List("2016-12-30", "2017-01-03", "2017-01-04", "2017-01-06", "2017-01-07"),
  "2017-01-06",
  "2017-01-07"
 )

getDatesToProcessAndDateMap( List("2016-12-30", "2017-01-03", "2017-01-04", "2017-01-06", "2017-01-07"),
  "2017-01-07",
  "2017-01-08"
 )