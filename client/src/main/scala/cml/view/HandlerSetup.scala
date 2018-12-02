package cml.view

import akka.actor.ActorSelection
import cml.controller.actor.utils.AppActorSystem.system
import cml.controller.fx.VillageViewController
import cml.controller.messages.VillageRequest.UpdateVillage
import cml.model.base._
import cml.model.static_model.StaticStructure
import cml.utils.ModelConfig.Elements._
import cml.utils.ModelConfig.ModelClass._
import cml.utils.{BuildingJson, HabitatJson}
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
  val structures: mutable.MutableList[Structure] = mutable.MutableList[Structure]()
  val village = VillageMap(structures)

  val handleVillage: Handler = {
    (elem: Node, controller: VillageViewController) =>
      addClickHandler(elem, controller)
      addDragAndDropTargetHandler(elem, controller)
  }

  val handleBuilding: Handler = {
    (_: Node, controller: VillageViewController) =>
      for(tile <- tileSet){
        addDragAndDropSourceHandler(tile, controller)
      }
  }

  private def addClickHandler(n: Node, c: VillageViewController): Unit = {
    n setOnMouseClicked(_ => {
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)

      //take() delle risorse

      c.selectionInfo setText "Mouse clicked in coords: ("+x+","+y+")\n"
      val btn = c.levelUpButton
      btn.setDisable(false)
          //se è terrain il tipo non devo poter aumentare il livello
      c.levelUpButton.setOnMouseClicked(_ => {
        for (s <- village.structures) {
          if (s.position equals Position(x, y)) {
            s.levelUp()
            s.getClass.getName match {
              //controllo aumento di livello: se è habitat decremento risorsa cibo e denaro, se è struttura solo denaro
              case FARM => //decrementare risorse globali + update
                val json = BuildingJson(FARM, s.level).json
                villageActor ! UpdateVillage(json)
              case CAVE => //decrementare risorse globali + update
                val json = BuildingJson(CAVE, s.level).json
                villageActor ! UpdateVillage(json)
              case HABITAT => //decrementare risorse globali cibo + denaro+ update
                val jsonHabitat = HabitatJson(FIRE, s.level).json
                villageActor ! UpdateVillage(jsonHabitat)
              //creature json aumento livello creatura
              //                    val jsonCreature = CreatureJson()
              //                    villageActor ! UpdateVillage(jsonCreature)
            }
          }
        }
        println("Level up: $level \nfood-- \nmoney--") //da stampare in textarea livello
        c.levelUpButton.setDisable(true)
      })
    })
  }

  private def addDragAndDropSourceHandler(t: Tile, c: VillageViewController): Unit = {
    val canvas = t.imageSprite
    canvas setOnMouseClicked(_ => c.selectionInfo setText "Element selected: "+ t.description + "\nPrice: $$$")
    canvas setOnDragDetected((event: MouseEvent) => {
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

      val structure = StaticStructure(newTile,x,y)
      val json = structure.json
      village.structures += structure.getStructure
      villageActor ! UpdateVillage(json)

      //Decremento denaro in base al prezzo, update modello remoto e locale
      c.selectionInfo setText "Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")"

      event consume()
    })
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