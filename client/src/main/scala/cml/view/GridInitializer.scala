package cml.view

import cml.model.static_model.{StaticBuilding, StaticHabitat, StaticStructures}
import cml.utils.ModelConfig.Building.{B_INIT_LEVEL, TYPE_CAVE, TYPE_FARM}
import cml.utils.ModelConfig.Elements.AIR
import cml.utils.ModelConfig.Habitat.H_INIT_LEVEL
import cml.view.utils.TileConfig.{slicer, spriteSheet, tileSet}
import javafx.scene.SnapshotParameters
import javafx.scene.image.{Image, ImageView}
import javafx.scene.input._
import javafx.scene.layout.GridPane

/**
  * Initialize Game GridPanes with costume settings
  * @author ecavina
  */
trait GridInitializer {

  /**
    * Initialize a grid as a buildings menu
    * @param grid to initialize
    */
  def initializeBuildingsMenu(grid: GridPane)

  /**
    * Initialize a grid as a village
    * @param grid to initialize
    */
  def initializeVillage(grid: GridPane)

}

trait Setup {
  def configure(grid: GridPane): GridPane
}

object Setup {

  val setupVillage: Setup = {
    grid: GridPane => { // scorrere model

      val baseTile = tileSet.filter(t => t.description.equals("TERRAIN")).head

      loop(0, 10) foreach { // INVECE CHE GENERARE DA ZERO SCORRO IL MODEL E SETTO LE TILE CORRISPONDENTI
        case(x, y) =>
          val imageTile = new ImageView()
          imageTile.setImage(baseTile.imageSprite.snapshot(new SnapshotParameters, null))
          grid add(imageTile, x, y)
      }

      def loop(s: Int, e: Int) =
      for(
        x <- s until e;
        y <- s until e
      ) yield (x, y)

      grid
    }
  }

  val setupBuildingsMenu: Setup = {
    grid: GridPane => {
      var pos = 0
      for(tile <- tileSet) {
        tile.description match{
          case "HABITAT" =>
            new StaticHabitat(AIR, H_INIT_LEVEL)
            println("habitat")
            grid add(tile.imageSprite, 0, pos)
          case "FARM" =>
            new StaticBuilding(TYPE_FARM, B_INIT_LEVEL)
            println("farm")
            grid add(tile.imageSprite, 0, pos)
          case "CAVE" =>
            new StaticBuilding(TYPE_CAVE, B_INIT_LEVEL)
            println("cave")
            grid add(tile.imageSprite, 0, pos)
          case "TERRAIN" => grid add(tile.imageSprite, 0, pos)
          case _ => throw new NoSuchElementException
        }
        pos += 1
      }
      grid
    }
  }
}

object BaseGridInitializer extends GridInitializer {

  private def setupGrid(grid: GridPane, setup: Setup): Unit = {
    grid setHgap 10
    grid setVgap 10
    setup.configure(grid)
  }

  override def initializeBuildingsMenu(grid: GridPane): Unit = {
    setupGrid(grid, Setup.setupBuildingsMenu)
  }

  override def initializeVillage(grid: GridPane): Unit = {
    setupGrid(grid, Setup.setupVillage)
  }

}
