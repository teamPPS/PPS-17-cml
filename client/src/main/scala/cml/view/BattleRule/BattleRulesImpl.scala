package cml.view.BattleRule

/**
  * This trait define basic battle rules
  *
  * @author Chiara Volonnino
  */

trait BattleRule {

  /**
    * Game initialization
    */
  def initialization()

  /**
    * Define attack method
    */
  def attack()

  /**
    * Define charge method
    */
  def charge()

  /**
    * Define protection method
    */
  def protection()

  /**
    * Define battle engine
    * @param powerAttackValue is power attack in base of method implemented
    * @return actual creature life
    */
  def gameEngine(powerAttackValue: Int): Int


}

/**
  * This class implements BattleRule
  */

case class BattleRulesImpl() extends BattleRule {

  private var creatureLife: Int = _
  private var attackPoint: Int = _
  private var isProtect: Boolean = _
  private var isAttack: Boolean = _
  private var isCharge: Boolean = _

  def initialization(): Unit = {
    creatureLife = 100
    attackPoint = 0
    isProtect = false
    isAttack = false
    isCharge = false
  }

  override def attack(): Unit = if(isPossibleAttack) decrementAttackPoint(); println("AttackPoint --> " + attackPoint)

  override def charge(): Unit = {
    incrementAttackPoint()
    isCharge = true
  }

  override def protection(): Unit = isProtect = true

  override def gameEngine(powerAttackValue: Int): Int = {
    var powerAttack = 0
    if(isProtect) powerAttack = 0
    else if(isCharge) powerAttack = 0
    else powerAttack = powerAttackValue
    creatureLife -= powerAttack
    creatureLife // in teoria deve tornare la potenza
  }

  private def decrementAttackPoint(): Unit = {
    if(_attackPoint > 0) attackPoint -= 1
    println("AttackPoint --> " + attackPoint)
  }

  private def incrementAttackPoint(): Unit = attackPoint += 1

  private def isPossibleAttack: Boolean = {
    if(_attackPoint > 0) isAttack = true
    else isAttack = false
    isAttack
  }

  def _creatureLife(): Int = creatureLife
  def _attackPoint: Int = attackPoint
  def isProtect_(): Unit = isProtect = false
  def _isProtection(): Boolean = isProtect
  def isCharge_(): Unit = isCharge = false
}
