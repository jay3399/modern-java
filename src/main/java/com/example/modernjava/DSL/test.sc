
def times(i: Int, f: => Unit): Unit = {
  f
  if (i > 1) times(i - 1, f)
}


//def timesV2(i : Int)(f: => Unit): Unit ={ f
//}
//


implicit def intToTimes(i : Int) = new {
  def times(f: => Unit) : Unit ={
    def times(i:Int , f: => Unit) : Unit ={
      f
      if(i>1) times(i -1, f )
    }
    times(i , f)
  }
}


//times(3, println("Hello"))

3 times(
  println("Hello World")
)

