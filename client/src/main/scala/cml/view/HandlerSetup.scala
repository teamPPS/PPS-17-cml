package cml.view

import cml.controller.fx.VillageViewController
import cml.model.base.{Creature, Position, Structure, VillageMap}
import cml.utils.ModelConfig.Resource.{FOOD, INIT_VALUE, MONEY}
import cml.view.utils.TileConfig.tileSet
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

/**
  * Trait define handler
  */
trait Handler {

  /**
    * Define a handler
    * @param elem element in input
    * @param controller controller identifier
    */
  def handle(elem: Node, controller: VillageViewController): Unit
}

/**
  * Object implements Handler
  */
object Handler {
  import cml.controller.fx.HandlerLogic._

  val Price = 30
  implicit val check: (Int,Int) => Boolean = _ >= _
  implicit val modifier: (Int,Int) => Int = _ * _
  def enoughResource(current: Int, baseCost: Int, toLevel: Int)
                    (implicit modifier: (Int,Int) => Int,
                     check: (Int,Int) => Boolean): Boolean = check(current, modifier(baseCost, toLevel))
  def enoughResourceForBuild(currentResource: Int): Boolean = enoughResource(currentResource, Price, 1)
  def enoughResourceForLevelUp(currentResource: Int, level: Int): Boolean = enoughResource(currentResource, Price, level+1)

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

      VillageMap.instance().get.villageStructure
        .filter(s => s.position.equals(Position(x, y)))
        .map(s => {
          if (s.creatures.nonEmpty && s.creatures.get.isEmpty) {
            c.addCreatureButton setDisable false
            c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
            c.addCreatureButton.setOnMouseClicked(_ => addNewCreature(s, c))
          } else {
            c.levelUpButton setDisable false
            c.levelUpButton setOnMouseClicked (_ => upgradeStructure(s, c))
            c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
          }
          s
        })
        .filter(s => s.resource.amount > INIT_VALUE)
        .foreach(s => {
          c.takeButton setDisable false
          c.takeButton setOnMouseClicked (_ => retrieveResource(s, c))
        })
    })
  }

  private def addDragAndDropSourceHandler(t: Tile, c: VillageViewController): Unit = {
    val canvas = t.imageSprite
    canvas setOnMouseClicked (_ => c.selectionInfo setText "Element selected: "+ t.description + "\nPrice: "+Price)
    canvas setOnDragDetected ((event: MouseEvent) => {
      val dragBoard: Dragboard = canvas startDragAndDrop TransferMode.COPY
      val image = canvas.snapshot(new SnapshotParameters, null)
      dragBoard setDragView image
      val content: ClipboardContent = new ClipboardContent
      content putString t.description
      dragBoard setContent content
      val gold = VillageMap.instance().get.gold
      if(enoughResourceForBuild(gold)) c.selectionInfo setText "Dragged element " + dragBoard.getString
      else c.selectionInfo setText "You can't build a structure if you don't have money"
      event consume()
    })
  }

  private def addDragAndDropTargetHandler(n: Node, c: VillageViewController): Unit = {
    n setOnDragOver ((event: DragEvent) => {
      event acceptTransferModes TransferMode.COPY
      event consume()
    })

    n setOnDragDropped ((event: DragEvent) => {
      val gold = VillageMap.instance().get.gold
      if(enoughResourceForBuild(gold)){
        val dragBoard: Dragboard = event getDragboard()
        val newTile = tileSet.filter(t => t.description.equals(dragBoard.getString)).head
        n match {
          case i: ImageView => i setImage newTile.imageSprite.snapshot(new SnapshotParameters, null)
          case _ => throw new ClassCastException
        }
        val y = GridPane.getColumnIndex(n)
        val x = GridPane.getRowIndex(n)
        updateVillage(newTile, x, y)
        decrementMoney(gold, Price, c)
        c.selectionInfo setText "Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")"
      } else c.selectionInfo setText "You can't build a structure if you don't have money"
      event consume()
    })
  }

  private def addNewCreature(s: Structure, c: VillageViewController): Unit ={
    addNewCreatureToVillage(s)
    c.addCreatureButton setDisable true
    c.levelUpButton setDisable false
    c.battleButton setDisable false
    c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
  }

  private def upgradeStructure(s: Structure, c: VillageViewController): Unit = {
    val gold =  VillageMap.instance().get.gold
    val food =  VillageMap.instance().get.food
    if(enoughResourceForLevelUp(gold, s.level)) {
      s.creatures match {
        case None => {
          upgradeBuildings(s)
          decrementMoney(gold, Price*s.level, c)
          c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
        }
        case _ =>
          if(enoughResourceForLevelUp(food, s.level)) {
            upgradeHabitat(s)
            decrementFood(food, Price * s.level, c)
            decrementMoney(gold, Price*s.level, c)
            c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
          } else {
            c.selectionInfo setText "You can't upgrade an habitat if you don't have food"
          }
      }
    } else {
      c.levelUpButton setDisable true
      c.selectionInfo setText "You can't upgrade a structure if you don't have money"
    }
  }

  private def retrieveResource(s: Structure, c: VillageViewController): Unit ={
    val retrieve = retrieveLocalResource(s)
    val gold = VillageMap.instance().get.gold
    val food = VillageMap.instance().get.food
    
    retrieve resourceType match{
      case FOOD => c.foodLabel.setText(food.toString)
      case MONEY => c.goldLabel.setText(gold.toString)
    }
    c.takeButton setDisable true
    c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
  }

  private def decrementMoney(gold: Int, price: Int, c: VillageViewController): Unit = {
    decrementAndUpdateMoney(gold, price)
    c.goldLabel.setText((gold - price).toString)
  }

  private def decrementFood(food: Int, price: Int, c: VillageViewController): Unit = {
    decrementAndUpdateFood(food, price)
    c.foodLabel.setText((food - price).toString)
  }

  private def displayText(name: String, level: Int, resourceAmount: Int, creatures: Option[mutable.MutableList[Creature]]): String = {
    var text: String = ""
    if(creatures.nonEmpty && creatures.get.nonEmpty) {
      text = "Structure " + name + "\n" +
        "Level: " + level + "\n" +
        "Resources: " + resourceAmount + "\n" +
        "Creature: " + creatures.get.head.name + "\nType: "+ creatures.get.head.creatureType +"\n"+
        "Creature level: " + creatures.get.head.level
    } else {
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
}

/**
  * Object implements HandlerSetup
  */
object ConcreteHandlerSetup extends HandlerSetup {

  private def setHandlers(grid: GridPane, controller: VillageViewController, handler: Handler): Unit = {
    val gridChildren = grid.getChildren
    gridChildren forEach(g => handler.handle(g, controller))
  }

  override def setupVillageHandlers(grid: GridPane, controller: VillageViewController): Unit = setHandlers(grid, controller, Handler.handleVillage)

  override def setupBuildingsHandlers(grid: GridPane, controller: VillageViewController): Unit = setHandlers(grid, controller, Handler.handleBuilding)
}