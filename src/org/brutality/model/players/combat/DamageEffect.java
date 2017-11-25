package org.brutality.model.players.combat;

import org.brutality.model.npcs.NPC;
import org.brutality.model.players.Player;
/**
 * 
 * @author Jason MacKeigan
 * @date Nov 24, 2014, 9:47:29 PM
 */
public interface DamageEffect {

	/**
	 * Executes some effect during the damage step of a player attack
	 * @param attacker	the attacking player in combat
	 * @param defender	the defending player in combat
	 * @param damage	the damage dealt during this step
	 */
	void execute(Player attacker, Player defender, Damage damage);
	
	/**
	 * Executes some effect during the damage step of a player attack
	 * @param attacker	the attacking player in combat
	 * @param defender	the defending npc in combat
	 * @param damage	the damage dealt during this step
	 */
	void execute(Player attacker, NPC defender, Damage damage);
	
	/**
	 * Determines if the event is executable by the operator
	 * @param operator	the player executing the effect
	 * @return	true if it can be executed based on some operation, otherwise false 
	 */
	boolean isExecutable(Player operator);
	
}
