package cml.view

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.fx.VillageViewController
import cml.controller.messages.VillageRequest.UpdateVillage
import cml.model.base._
import cml.model.dynamic_model.{RetrieveResource, StructureUpgrade}
import cml.model.static_model.{StaticCreatures, StaticStructure}
import cml.utils.ModelConfig.Resource._
import cml.utils.ModelConfig.ModelClass._
import cml.utils.MoneyJson
import cml.view.utils.TileConfig._
import javafx.scene.image.ImageView
import javafx.scene.input._
import javafx.scene.layout.GridPane
import javafx.scene.{Node, SnapshotParameters}

import scala.collection.mutable

/**
  * Setup handlers with costume settings
  * @author Monica Gondolini, ecavina
  */
trait HandlerSetup {

  /**
    * Setup handlers for the village
    * @param grid what we need to handle
    */
  def setupVillageHandlers(grid: GridPane, controller: VillageViewController): Unit

  /**
    * Setup handlers for buildings menu
    * @param grid what we need to handle
    */
  def setupBuildingsHandlers(grid: GridPane, controller: VillageViewController): Unit
}

trait Handler {
  def handle(elem: Node, controller: VillageViewController): Unit
}

object Handler {

  val villageActor: ActorSelection = system actorSelection "/user/VillageActor"
//  val structures: mutable.MutableList[Structure] = mutable.MutableList[Structure]()
  val village: VillageMap = VillageMap.village
  val price = 30 //prezzo iniziale deve essere globale ?

  val handleVillage: Handler = {
    (elem: Node, control: VillageViewController) =>
      addClickHandler(elem, control)
      addDragAndDropTargetHandler(elem, control)
  }

  val handleBuilding: Handler = {
    (_: Node, control: VillageViewController) =>
      for(tile <- tileSet){
        addDragAndDropSourceHandler(tile, control)
      }
  }

  private def addClickHandler(n: Node, c: VillageViewController): Unit = {
    n setOnMouseClicked(_ => {
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)
      disableButtons(c)
      c.selectionInfo setText "Coordinates (" + x + ", " + y + ")"

      for (s <- village.structures) {
        if (s.position equals Position(x, y)) {
          if (s.creatures != null && s.creatures.isEmpty) {
            c.addCreatureButton setDisable false
            c.selectionInfo setText "Structure: " + getClassName(s)
            c.addCreatureButton.setOnMouseClicked(_ => {
              val creature = StaticCreatures(s)
              s.addCreature(creature getCreature)
              villageActor ! UpdateVillage(creature json)
              c.addCreatureButton setDisable true
              c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
            })
          } else {
            c.levelUpButton setDisable false
            c.levelUpButton setOnMouseClicked (_ => {
              val upgrade = StructureUpgrade(s)
              villageActor ! UpdateVillage(upgrade structureJson)
              upgrade creatureJson match {
                case null => println("Not an habitat")
                case _ => villageActor ! UpdateVillage(upgrade creatureJson)
              }
              //Decremento denaro in base al prezzo, update modello remoto e locale
              val resourceJson = MoneyJson(INIT_VALUE - price).json
              villageActor ! UpdateVillage(resourceJson)

              c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
            })

            s.resource.inc(s.level) //debug
            c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)

            if (s.resource.amount > INIT_VALUE) { //settare un current value?
              c.takeButton setDisable false
              c.takeButton setOnMouseClicked (_ => {
                val retrieve = RetrieveResource(s)
                villageActor ! UpdateVillage(retrieve resourceJson)
                c.takeButton setDisable true
                c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
              })
            }
          }
        }
      }
    })
  }

  private def addDragAndDropSourceHandler(t: Tile, c: VillageViewController): Unit = {
    val canvas = t.imageSprite
    canvas setOnMouseClicked (_ => c.selectionInfo setText "Element selected: "+ t.description + "\nPrice: "+price)
    canvas setOnDragDetected ((event: MouseEvent) => {
      val dragBoard: Dragboard = canvas startDragAndDrop TransferMode.COPY
      val image = canvas.snapshot(new SnapshotParameters, null)
      dragBoard setDragView image
      val content: ClipboardContent = new ClipboardContent
      content putString t.description
      dragBoard setContent content
      c.selectionInfo setText "Dragged element " + dragBoard.getString
      event consume()
    })
  }

  private def addDragAndDropTargetHandler(n: Node, c: VillageViewController): Unit = {
    n setOnDragOver ((event: DragEvent) => {
      event acceptTransferModes TransferMode.COPY
      event consume()
    })

    n setOnDragDropped ((event: DragEvent) => {
      val dragBoard: Dragboard = event getDragboard()
      val newTile = tileSet.filter(t => t.description.equals(dragBoard.getString)).head
      n match {
        case i: ImageView => i setImage newTile.imageSprite.snapshot(new SnapshotParameters, null)
        case _ => throw new ClassCastException
      }
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)

      val structure = StaticStructure(newTile, x, y)
      val json = structure.json
      village.structures += structure.getStructure
      villageActor ! UpdateVillage(json)

      //Decremento denaro in base al prezzo, update modello remoto e locale
      val resourceJson = MoneyJson(INIT_VALUE-price).json
      villageActor ! UpdateVillage(resourceJson)

      c.selectionInfo setText "Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")"
      event consume()
    })
  }

  private def displayText(name: String, level: Int, resourceAmount: Int, creatures: mutable.MutableList[Creature]): String = {
    var text:String = ""
    if(creatures != null && creatures.nonEmpty) {
      text = "Structure " + name + "\n" +
        "Level: " + level + "\n" +
        "Resources: " + resourceAmount + "\n" +
        "Creature: " + creatures.head.name + "\nType: "+ creatures.head.creatureType +"\n"+
        "Creature level: " + creatures.head.currentLevel
    }else{
      text = "Structure " + name + "\n" +
        "Level: " + level + "\n" +
        "Resources: " + resourceAmount + "\n"
    }
    text
  }

  private def disableButtons(c: VillageViewController): Unit = {
    c.levelUpButton setDisable true
    c.takeButton setDisable true
    c.addCreatureButton setDisable true
  }

  private def getClassName(s: Structure): String = {
    var name: String = ""
    s.getClass.getName match {
      case FARM => name = "FARM"
      case CAVE => name = "CAVE"
      case HABITAT => name = "HABITAT - Element: " + s.element
    }
    name
  }
}

object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, controller: VillageViewController, handler: Handler): Unit = {
    val gridChildren = grid.getChildren
    gridChildren forEach(g => handler.handle(g, controller))
  }

  override def setupVillageHandlers(grid: GridPane, controller: VillageViewController): Unit = setHandlers(grid, controller, Handler.handleVillage)

  override def setupBuildingsHandlers(grid: GridPane, controller: VillageViewController): Unit = setHandlers(grid, controller, Handler.handleBuilding)
}