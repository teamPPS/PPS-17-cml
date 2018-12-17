package cml.view

import akka.actor.ActorSelection
import cml.controller.actor.utils.ActorUtils.ActorSystemInfo.system
import cml.controller.fx.VillageViewController
import cml.controller.messages.VillageRequest.{SetUpdateVillage, UpdateVillage}
import cml.model.base.{Creature, Position, Structure, VillageMap}
import cml.model.dynamic_model.{RetrieveResource, StructureUpgrade}
import cml.model.static_model.{StaticCreatures, StaticStructure}
import cml.utils.ModelConfig.ModelClass.{CAVE_CLASS, FARM_CLASS, HABITAT_CLASS}
import cml.utils.ModelConfig.Resource.{FOOD, INIT_VALUE, MONEY}
import cml.utils.{FoodJson, MoneyJson}
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

trait Handler {
  def handle(elem: Node, controller: VillageViewController): Unit
}

object Handler {

  val VillageActorPath: String= "/user/VillageActor"
  val villageActor: ActorSelection = system actorSelection VillageActorPath
  val Price = 30

  implicit val check: (Int,Int) => Boolean = _ > _
  implicit val modifier: (Int,Int) => Int = _ * _
  def enoughResource(current: Int, baseCost: Int, toLevel: Int)
                    (implicit modifier: (Int,Int) => Int,
                     check: (Int,Int) => Boolean): Boolean = check(current, modifier(baseCost, toLevel))
  def enoughResourceForBuild(currentResource: Int): Boolean = enoughResource(currentResource, Price, 1)
  def enoughResourceForLevelUp(currentResource: Int, toLevel: Int): Boolean = enoughResource(currentResource, Price, toLevel)

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

      for (s <- VillageMap.instance().get.villageStructure) {
        if (s.position equals Position(x, y)) {
          if (s.creatures.nonEmpty && s.creatures.get.isEmpty) {
            c.addCreatureButton setDisable false
            c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
            c.addCreatureButton.setOnMouseClicked(_ => addNewCreature(s, c))
          } else {
            c.levelUpButton setDisable false
            c.levelUpButton setOnMouseClicked (_ => upgradeStructure(s, c))

            c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)

            if (s.resource.amount > INIT_VALUE) {
              c.takeButton setDisable false
              c.takeButton setOnMouseClicked (_ => retrieveResource(s, c))
            }
          }
        }
      }
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

        val structure = StaticStructure(newTile, x, y)
        val json = structure.json
        VillageMap.instance().get.villageStructure += structure.getStructure
        villageActor ! UpdateVillage(json)
        c.selectionInfo setText "Dropped element " + dragBoard.getString + " in coordinates (" + x + " - " + y + ")"

        decrementMoney(gold, Price, c)

      } else c.selectionInfo setText "You can't build a structure if you don't have money"
      event consume()
    })
  }

  private def addNewCreature(s: Structure, c: VillageViewController): Unit ={
    val creature = StaticCreatures(s)
    villageActor ! SetUpdateVillage(creature json)
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
        case None => villageActor ! SetUpdateVillage(StructureUpgrade(s).structureJson)
        case _ =>
          if(enoughResourceForLevelUp(food, s.level+1))
            villageActor ! SetUpdateVillage(StructureUpgrade(s).creatureJson.get)
            decrementFood(food, Price*s.level, c)
      }
      decrementMoney(gold, Price*s.level, c)
      c.selectionInfo setText displayText(getClassName(s), s.level, s.resource.amount, s.creatures)
    } else {
      c.levelUpButton setDisable true
      c.selectionInfo setText "You can't upgrade a structure if you don't have money"
    }
  }

  private def retrieveResource(s: Structure, c: VillageViewController): Unit ={
    val retrieve = RetrieveResource(s)
    villageActor ! SetUpdateVillage(retrieve resourceJson)

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
    Thread.sleep(200) //TODO controllo invio di messaggi future
    val resourceJson = MoneyJson(gold - price).json
    VillageMap.instance().get.gold = gold - price
    c.goldLabel.setText((gold - price).toString)
    villageActor ! SetUpdateVillage(resourceJson)
  }

  private def decrementFood(food: Int, price: Int, c: VillageViewController): Unit = {
    Thread.sleep(200)
    val resourceJson = FoodJson(food - price).json
    VillageMap.instance().get.food = food - price
    c.foodLabel.setText((food - price).toString)
    villageActor ! SetUpdateVillage(resourceJson)
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

  private def getClassName(s: Structure): String = {
    var name: String = ""
    s.getClass.getName match {
      case FARM_CLASS => name = "FARM"
      case CAVE_CLASS => name = "CAVE"
      case HABITAT_CLASS => name = "HABITAT - Element: " + s.habitatElement
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