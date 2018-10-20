package helloworld

private object HelloWorld { //findbugs dava warning e lo voleva private ma non credo abbia senso, controllerò meglio
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
  }
}
