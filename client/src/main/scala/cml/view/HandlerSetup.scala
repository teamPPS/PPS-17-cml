package cml.view

import akka.actor.ActorSelection
import cml.controller.actor.utils.AppActorSystem.system
import cml.controller.messages.VillageRequest.UpdateVillage
import cml.model.base.Habitat.Habitat
import cml.model.base._
import cml.utils.ModelConfig.Building.B_INIT_LEVEL
import cml.utils.ModelConfig.Elements.AIR
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import cml.view.utils.TileConfig._
import javafx.scene.control.{Button, TextArea}
import javafx.scene.image.ImageView
import javafx.scene.input._
import javafx.scene.layout.{GridPane, Pane}
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
    * @param upgrade actions to perform
    */
  def setupVillageHandlers(grid: GridPane, area: Pane, upgrade: Pane): Unit

  /**
    * Setup handlers for buildings menu
    * @param grid what we need to handle
    * @param upgrade actions to perform
    */
  def setupBuildingsHandlers(grid: GridPane, area: Pane, upgrade: Pane): Unit
}

trait Handler {
  def handle(elem: Node, area: Node, upgrade: Node): Unit
}

object Handler {

  val villageActor: ActorSelection = system actorSelection "/user/VillageActor"
  val structures: mutable.MutableList[Structure] = mutable.MutableList[Structure]()
  val village = VillageMap(structures)

  val handleVillage: Handler = {
    (elem: Node, area: Node, upgrade: Node) =>
      addClickHandler(elem, area, upgrade)
      addDragAndDropTargetHandler(elem, area)
  }

  val handleBuilding: Handler = {
    (_: Node, area: Node, _: Node) =>
      for(tile <- tileSet){
        addDragAndDropSourceHandler(tile, area)
      }
  }

  private def addClickHandler(n: Node, a:Node, up: Node): Unit = {
    n setOnMouseClicked(_ => {
      val y = GridPane.getColumnIndex(n)
      val x = GridPane.getRowIndex(n)
      a match {
        case info: TextArea => info setText "Mouse clicked in coords: ("+x+","+y+")\n"
        case _ => throw new ClassCastException
      }
      up match {
        case levelUp: Button =>
          levelUp.setDisable(false)
          //se è terrain il tipo non devo poter aumentare il livello
          levelUp.setOnMouseClicked(_ =>{
            //scorrere villageMap ficnhè non trovo un match con le coordinate
            for(s <- village.structures){
              println(s)
              if(s.getPosition equals Position(x,y)){
                s.levelUp()
                s.getClass.getName match{
                    //invio update al server!!!!
                  //controllo aumento di livello: se è habitat decremento risorsa cibo e denaro, se è struttura solo denaro
                  case "cml.model.base.Building" => println("cibo--")
                  case "cml.model.base.Habitat" => println("cibo-- soldi--")
                }
              }
            }
            println("Level up: $level \nfood-- \nmoney--") //da stampare in textarea livello
            levelUp.setDisable(true)
          })
        case _ => throw new ClassCastException
      }
    })
  }

  private def addDragAndDropSourceHandler(t: Tile, a: Node): Unit = {
    val canvas = t.imageSprite
    canvas setOnMouseClicked(_ => {
      a match {
        case info: TextArea => info setText "Element selected: "+ t.description + "\nPrice: $$$"
        case _ => throw new ClassCastException
      }
    })
    canvas setOnDragDetected((event: MouseEvent) => {
      val dragBoard: Dragboard = canvas startDragAndDrop TransferMode.COPY
      val image = canvas.snapshot(new SnapshotParameters, null)
      dragBoard setDragView image
      val content: ClipboardContent = new ClipboardContent
      content putString t.description
      dragBoard setContent content
      a match {
        case info: TextArea => info setText "Dragged element " + dragBoard.getString
        case _ => throw new ClassCastException
      }
      event consume()
    })
  }

  private def addDragAndDropTargetHandler(n: Node, a: Node): Unit = {
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

      setTileModel(newTile, x, y)

      a match {
        case info: TextArea => info setText "Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")"
        case _ => throw new ClassCastException
      }
      event consume()
      // UPDATE MODEL e send al server
    })
  }

  private def setTileModel(t: Tile, x: Int, y: Int): Unit = {
    t.description match {
      case "HABITAT" =>
        village.structures += Habitat(AIR, Position(x,y), H_INIT_LEVEL)
        println("habitat posizionato "+village.structures+"  "+t.json) //debug
        villageActor ! UpdateVillage(t.json)
      case "FARM" =>
        village.structures += Farm(Position(x,y), B_INIT_LEVEL)
        println("farm posizionato "+village.structures+"  "+t.json) //debug
        villageActor ! UpdateVillage(t.json)
      case "CAVE" =>
        village.structures += Cave(Position(x,y), B_INIT_LEVEL)
        println("cave posizionato "+village.structures+"  "+t.json) //debug
        villageActor ! UpdateVillage(t.json)
      case "TERRAIN" =>
        println("terrain posizionato") //debug
      case _ => throw new NoSuchElementException
    }
  }
}

object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, area: Pane, upgrade: Pane, handler: Handler): Unit = {
    val gridChildren = grid.getChildren
    val paneChildren = upgrade.getChildren
    val areaChildren = area.getChildren
    gridChildren forEach(g => {
      areaChildren forEach(a =>{
        paneChildren forEach(up =>
        handler.handle(g, a,  up)
      )}
    )})
  }

  override def setupVillageHandlers(grid: GridPane, area: Pane, upgrade: Pane): Unit = setHandlers(grid, area, upgrade, Handler.handleVillage)

  override def setupBuildingsHandlers(grid: GridPane, area: Pane, upgrade: Pane): Unit = setHandlers(grid, area, upgrade, Handler.handleBuilding)
}