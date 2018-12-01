package cml.utils

import cml.model.base.Resource

case class BuildingJson(buildingType: String, buildingLevel: String, resource: Resource){

}

case class HabitatJson(habitatElem: String, habitatLevel: String, resource: Resource){
  
}
