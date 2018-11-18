package cml.view

/**
  * Define a Trait useful to switch a root view element in ancestor given the element
  * @tparam A type of element
  * @tparam B type of ancestor element where set new element
  */
trait Switch[A,B] {

  /**
    * Activate the element in ancestor
    * @param element view element
    * @param ancestor entity where setting view element
    */
  def activate(element: A, ancestor: B): Unit

}