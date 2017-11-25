package org.brutality.model.players.combat;

import org.brutality.model.players.Player;

/**
 * 
 * @author Jason MacKeigan
 * @date Nov 6, 2014, 1:01:03 PM
 */
public class Damage {
	/**
	 * The amount of damage caused by the weapon
	 */
	private int amount;
	
	public int damage;
	
	public int getDamage() {
		return damage;
	}
	
	/**
	 * The weapon id used to create this damage
	 */
	private int weapon;
	
	/**
	 * The damage object will be stored in a queue. Every 600ms, or every game tick, the number of ticks
	 * will be reduced by 1 until the ticks is 0
	 */
	private int ticks;
	
	/**
	 * The mask of the update, this will determine if its displayed first
	 * or second in the player appearance update
	 */
	private int mask;
	
	/**
	 * The target that this damage will be inflicted upon
	 */
	private Player target;
	
	/**
	 * The time that the damage was created
	 */
	private long timestamp;
	
	/**
	 * The hitmark for this damage
	 */
	private Hitmark hitmark;
	
	/**
	 * Creates a new damage object with a specific amount registered
	 * @param amount	the amount of damage
	 */
	public Damage(int amount) {
		this.amount = amount;
		this.timestamp = System.currentTimeMillis();
	}
	
	/**
	 * Creates a new {@link Damage} object that will be added to the damage queue
	 * for the target
	 * @param target	the target being dealt the damage	
	 * @param amount	the amount of damage being dealt
	 * @param ticks		the amount of ticks until the damage is applied
	 * @param hitmark	the type of hitmark that is displayed
	 */
	public Damage(Player target, int amount, int ticks, Hitmark hitmark) {
		this(amount);
		if (ticks < 0 || mask < 0 || mask > 2) {
			throw new IllegalStateException("The number of ticks for the damage is negative, or mask > 2.");
		}
		this.target = target;
		this.ticks = ticks;
		this.hitmark = hitmark;
	}
	
	/**
	 * Returns the amount of damage
	 * @return	the amount of damage
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * Returns the weapon used to cause this damage
	 * @return	the weapon id
	 */
	public int getWeapon() {
		return weapon;
	}
	
	/**
	 * The target of the damage attack
	 * @return	the player target
	 */
	public Player getTarget() {
		return target;
	}
	
	/**
	 * The number of ticks this damage is alive for
	 * @return	the ticks
	 */
	public int getTicks() {
		return ticks;
	}
	
	/**
	 * Removes a single tick from the total number of ticks
	 */
	public void removeTick() {
		ticks--;
	}
	
	/**
	 * The time that the damage was created
	 * @return	the time the damage was created
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * The hitmark for this damage
	 * @return	the hitmark
	 */
	public Hitmark getHitmark() {
		return hitmark;
	}

}
