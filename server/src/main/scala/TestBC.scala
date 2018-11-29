object TestBC {

  import java.security.Security

    def main(args: Array[String]): Unit = {
      import java.security.Security
      val providers = Security.getProviders
      var i = 0
      while ( {i < providers.length}) {
        println(providers(i).toString)
          i += 1; i - 1
        }
 }}
