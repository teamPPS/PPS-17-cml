package cml.view

/**
  * Define a Trait useful to switch a view element in an ancestor given ID of the element
  * @tparam A type of id element
  * @tparam B type of ancestor element where set new element
  */
trait Switch[A,B] {

  /**
    * Activate the element identify by an id in root element
    * @param id id of a view element
    * @param ancestor entity where setting view element
    */
  def activate(id: A, ancestor: B): Unit

}