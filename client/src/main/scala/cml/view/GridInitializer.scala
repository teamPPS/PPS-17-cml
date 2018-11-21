package cml.view

import cml.view.utils.TileConfig.{tileSet,slicer,spriteSheet}

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
        grid add(tile.imageSprite, 0, pos)
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
