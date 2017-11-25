package org.brutality.model.players.combat;

import java.util.LinkedList;
import java.util.Queue;

import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.model.players.Player;
/**
 * 
 * @author Jason MacKeigan
 * @date Nov 9, 2014, 10:02:13 AM
 */
public class DamageQueueEvent extends CycleEvent {
	/**
	 * The queue containing all of the damage being dealt by the player
	 */
	private Queue<Damage> damageQueue = new LinkedList<>();
	
	/**
	 * The damage dealer, the owner of the queue
	 */
	private Player player;
	
	/**
	 * Creates a new class that will manage all damage dealt by the player
	 * @param player	the player dealing the damage
	 */
	public DamageQueueEvent(Player player) {
		this.player = player;
	}
	
	/**
	 * Adds a damage object to the end of the queued damage list
	 * @param damage the damage to be dealt
	 */
	public void add(Damage damage) {
		damageQueue.add(damage);
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (damageQueue.size() <= 0) {
			return;
		}
		Damage damage;
		Queue<Damage> updatedQueue = new LinkedList<>();
		while ((damage = damageQueue.poll()) != null) {
			damage.removeTick();
			if (damage.getTicks() == 1) {
				AttackPlayer.playerDelayedHit(player, damage.getTarget().index, damage);
			} else if (damage.getTicks() > 1) {
				updatedQueue.add(damage);
			}
		}
		damageQueue.addAll(updatedQueue);
	}
}
