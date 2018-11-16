package cml.controller.fx

import cml.utils.Configuration.{AuthenticationWindow, BattleWindow}
import cml.view.ViewSwitch
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
    villageMap setHgap 10
    villageMap setVgap 10
    villagePane setContent villageMap
    initVillageMap()

    buildingsMenu = new GridPane
    buildingsMenu setHgap 5
    buildingsMenu setVgap 5
    buildingsGrid setContent buildingsMenu
    initBuildingsMenu()

  }

  // testing purpose
  def initBuildingsMenu(): Unit = {
    // builder/factory per i tile di menu, devono estendere Node, con anche gli handler
    var farmLabel = new Label("FARM")
    addDragAndDropSourceHandler(farmLabel)
    var habitatLabel = new Label("HABITAT")
    addDragAndDropSourceHandler(habitatLabel)
    var caveLabel = new Label("CAVE")
    addDragAndDropSourceHandler(caveLabel)
    var terrain = new Label("terrain")
    addDragAndDropSourceHandler(terrain)
    buildingsMenu add(farmLabel, 0, 0)
    buildingsMenu add(habitatLabel, 0, 1)
    buildingsMenu add(caveLabel, 1, 0)
    buildingsMenu add(terrain, 1, 1)
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
