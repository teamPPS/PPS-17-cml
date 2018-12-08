package cml.view.BattleRule

case class BattleRulesImpl() {

  private var creatureLife: Int = _
  private var attackPoint: Int = _
  private var isProtect: Boolean = _
  private var isAttack: Boolean = _

  def initialization(): Unit = {
    creatureLife = 100
    attackPoint = 0
    isProtect = false
    isAttack = false
  }

  def attack(): Unit = {
    if(isPossibleAttack) decrementAttackPoint()
  }

  def charge(): Unit = incrementAttackPoint()

  def protection(): Unit = isProtect = true

  def gameEngine(): Unit = {
  }

  private def decrementAttackPoint(): Unit = {
    if(_attackPoint > 0) attackPoint -= 1
    println("AttackPoint --> " + attackPoint)
  }

  private def incrementAttackPoint(): Unit = attackPoint += 1

  def _attackPoint: Int = attackPoint

  private def isPossibleAttack: Boolean = {
    if(_attackPoint > 0) isAttack = true
    else isAttack = false
    println("IS possibile attaccare? -- " + isAttack)
    isAttack
  }
}
