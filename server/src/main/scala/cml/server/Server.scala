package cml.server
/**
  * This trait it for creation and management of server.
  *
  * @author Chiara Volonnino
  */

trait Server {

  def iniRouter //: (router: Router)
  def initServer
  def getPort

}
