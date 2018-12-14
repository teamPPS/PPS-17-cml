package cml.view

import java.util.Optional

import javafx.scene.control.{Alert, ButtonType}
import javafx.scene.control.Alert.AlertType

/**
  * This class create  a confirmation dialog
  *
  * @author Chiara Volonnino
  */

case class DialogPaneUtils() {

  val ConfirmationDialogTitle = "Confirmation Dialog"
  val ConfirmationDialogContentTest = "Are you sure want to confirm?"
  val InformationDialogTitle = "Information Dialog"
  val InformationDialogContentTest = "Resume?"
  var alert: Alert = _

  def createConfirmationPane(headerText: String): Unit = {
    alert = new Alert(AlertType.CONFIRMATION) {
      setTitle(ConfirmationDialogTitle)
      setHeaderText(headerText)
      setContentText(ConfirmationDialogContentTest)
    }
  }

  def crateInformationPane(headerText: String): Unit = {
    alert = new Alert(AlertType.INFORMATION) {
      setTitle(InformationDialogTitle)
      setHeaderText(headerText)
      setContentText(InformationDialogContentTest)
    }
  }

  def showPane(): Optional[ButtonType] = {
    alert.showAndWait()
  }
}
