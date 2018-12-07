package cml.view

import cml.model.base.Habitat.Habitat
import cml.model.base.{Cave, Farm, VillageMap}
import cml.view.utils.TileConfig.{tileSet, baseTileSet}
import javafx.scene.SnapshotParameters
import javafx.scene.image.ImageView
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

  val MapSide = 10

  val setupVillage: Setup = {
    grid: GridPane => {

      def createTile(description: String, set: Set[Tile]): ImageView =  {
        val image: ImageView = new ImageView()
        image.setImage(
          set.filter(t => t.description.equals(description))
            .head
            .imageSprite
            .snapshot(new SnapshotParameters, null)
        )
        image
      }

      val terrainImage: ImageView = new ImageView()
      terrainImage.setImage(
        baseTileSet.filter(t => t.description.equals("TERRAIN"))
          .head
          .imageSprite
          .snapshot(new SnapshotParameters(), null)
      )
      val baseTile = terrainImage //TODO mettere a posto Tile.scala in modo che baseTile e Tile siano accumunabili ad un interfaccia comune
      val farmTile = createTile("FARM", tileSet)
      val caveTile = createTile("CAVE", tileSet)
      val fireTile = createTile("FIRE_HABITAT", tileSet)
      val waterTile = createTile("WATER_HABITAT", tileSet)
      val airTile = createTile("AIR_HABITAT", tileSet)
      val earthTile = createTile("EARTH_HABITAT", tileSet)

      loop(0, MapSide) foreach {
        case(x, y) =>
          val imageTile = new ImageView()
          imageTile.setImage(baseTile.snapshot(new SnapshotParameters(), null))
          grid add(imageTile, x, y)
      }

      VillageMap.instance().get.villageStructure.foreach {
        case f: Farm => grid.add(farmTile, f.position.y, f.position.x)
        case c: Cave => grid.add(caveTile, c.position.y, c.position.x)
        case h: Habitat => h.element match {
          case "fire" => grid.add(fireTile, h.position.y, h.position.x)
          case "water" => grid.add(waterTile, h.position.y, h.position.x)
          case "air" => grid.add(airTile, h.position.y, h.position.x)
          case "earth" => grid.add(earthTile, h.position.y, h.position.x)
        }
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
    grid setHgap 1
    grid setVgap 1
    setup.configure(grid)
  }

  override def initializeBuildingsMenu(grid: GridPane): Unit = {
    setupGrid(grid, Setup.setupBuildingsMenu)
  }

  override def initializeVillage(grid: GridPane): Unit = {
    setupGrid(grid, Setup.setupVillage)
  }

}
