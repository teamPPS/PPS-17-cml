package cml.model.base

import scala.collection.mutable

/**
  * This class implements a VillageMap
 *
  * @author Monica Gondolini
  * @param structures list of structures composing the village
  */
case class VillageMap(structures: mutable.MutableList[Structure])
