package cml.model.base

import scala.collection.mutable

  /**
    * This class implements a VillageMap
    *
    * @param structures list of structures composing the village
    * @author Monica Gondolini
    */
  case class VillageMap(structures: mutable.MutableList[Structure])


/**
  * Companion Object
  */
  object VillageMap {
    val structures: mutable.MutableList[Structure] = mutable.MutableList[Structure]()
    val village = VillageMap(structures)
  }