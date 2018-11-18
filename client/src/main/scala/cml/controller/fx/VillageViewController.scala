package cml.controller.fx

import cml.utils.Configuration.{AuthenticationWindow, BattleWindow}
import cml.view.{BaseGridInitializer, ViewSwitch}
import javafx.fxml.FXML
import javafx.scene.control._
import javafx.scene.input._
import javafx.scene.layout.GridPane

class VillageViewController {

  @FXML var playerLevelLabel: Label = _
  @FXML var goldLabel: Label = _
  @FXML var foodLabel: Label = _
  @FXML var settingsMenuItem: MenuItem = _
  @FXML var logoutMenuItem: MenuItem = _
  @FXML var aboutSelection: TextArea = _
  @FXML var battleButton: Button = _
  @FXML var villagePane: ScrollPane = _
  @FXML var buildingsGrid: ScrollPane = _
  var villageMap: GridPane = _
  var buildingsMenu: GridPane = _

  def initialize(): Unit = {
    settingsMenuItem setOnAction (_ => println("Pressed settings submenu button")) // open settings dialog
    logoutMenuItem setOnAction (_ => ViewSwitch.activate(AuthenticationWindow.path, logoutMenuItem.getParentPopup.getOwnerWindow.getScene))
    battleButton setOnAction (_ => ViewSwitch.activate(BattleWindow.path, battleButton.getScene))

    villageMap = new GridPane
    BaseGridInitializer.initializeVillage(villageMap)
    villagePane setContent villageMap

    buildingsMenu = new GridPane
    BaseGridInitializer.initializeBuildingsMenu(buildingsMenu)
    buildingsGrid setContent buildingsMenu
  }

  // testing purpose
  def initVillageMap(): Unit = {
    // flyweight tile https://www.baeldung.com/java-flyweight https://refactoring.guru/design-patterns/flyweight/java/example

    loop(0, 20) foreach {
      case(x, y) =>
        var mapLabel = new Label("terrain")
        addDragAndDropTargetHandler(mapLabel)
        villageMap add(mapLabel, x, y)
    }

    def loop(s: Int, e: Int) =
      for(
        x <- s until e;
        y <- s until e
      ) yield (x, y)
  }


  // https://examples.javacodegeeks.com/desktop-java/javafx/event-javafx/javafx-drag-drop-example/
  def addDragAndDropSourceHandler(n: Label): Unit = { //al posto di Label ci andrà il tile personalizzato

    n setOnDragDetected((event: MouseEvent) => {
      val dragBoard: Dragboard = n startDragAndDrop TransferMode.COPY
//      dragBoard setDragView(image) icona vicino mouse mentre si fa il drag
      val content: ClipboardContent = new ClipboardContent
      content putString(n getText)
      dragBoard setContent content
      event consume()
    })

  }

  def addDragAndDropTargetHandler(n: Label): Unit = {

    n setOnDragOver((event: DragEvent) => {
      event acceptTransferModes TransferMode.COPY
      event consume()
    })

    n setOnDragDropped((event: DragEvent) => {
      var dragBoard: Dragboard = event getDragboard()
      n setText dragBoard.getString
      event consume()
    })
  }
}
