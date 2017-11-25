package org.brutality.model.minigames.BlastMine;

import org.brutality.Server;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.model.npcs.NPC;
import org.brutality.model.players.Player;
import org.brutality.util.Location3D;
import org.brutality.util.Misc;
import org.brutality.world.objects.GlobalObject;

/**
 * Represents a singular event that is executed when a player attempts to mine. 
 * 
 * @author Jason MacKeigan
 * @date Feb 18, 2015, 6:17:11 PM
 */
public class VeinEvent extends CycleEvent {
	
	/**
	 * The amount of cycles that must pass before the animation is updated
	 */
	private final int ANIMATION_CYCLE_DELAY = 15;
	
	/**
	 * The value in cycles of the last animation
	 */
	private int lastAnimation;
	
	/**
	 * The player attempting to mine
	 */
	private final Player player;
	
	/**
	 * The pickaxe being used to mine
	 */
	private final Spade pickaxe;
	
	/**
	 * The mineral being mined
	 */
	private final Vein mineral;
	
	/**
	 * The object that we are mning
	 */
	private int objectId;
	
	/**
	 * The location of the object we're mining
	 */
	private Location3D location;
	
	/**
	 * The npc the player is mining, if any
	 */
	private NPC npc;
	
	/**
	 * Constructs a new {@link MiningEvent} for a single player
	 * @param player	the player this is created for
	 * @param objectId	the id value of the object being mined from
	 * @param location	the location of the object being mined from
	 * @param mineral	the mineral being mined
	 * @param pickaxe	the pickaxe being used to mine
	 */
	public VeinEvent(Player player, int objectId, Location3D location, Vein mineral, Spade pickaxe) {
		this.player = player;
		this.objectId = objectId;
		this.location = location;
		this.mineral = mineral;
		this.pickaxe = pickaxe;
	}
	
	/**
	 * Constructs a new {@link MiningEvent} for a single player
	 * @param player	the player this is created for
	 * @param npc		the npc being from from
	 * @param location	the location of the npc
	 * @param mineral	the mineral being mined
	 * @param pickaxe	the pickaxe being used to mine
	 */
	public VeinEvent(Player player, NPC npc, Location3D location, Vein mineral, Spade pickaxe) {
		this.player = player;
		this.npc = npc;
		this.location = location;
		this.mineral = mineral;
		this.pickaxe = pickaxe;
	}
	
	@Override 
	public void update(CycleEventContainer container) {
		if (player == null) {
			container.stop();
			return;
		}
		if (!player.getItems().playerHasItem(pickaxe.getItemId())
				&& !player.getItems().isWearingItem(pickaxe.getItemId())) {
			player.sendMessage("That is strange! The pickaxe could not be found.");
			container.stop();
			return;
		}
		if (player.getItems().freeSlots() == 0) {
			player.getDH().sendStatement("You have no more free slots.");
			container.stop();
			return;
		}
		if (Misc.random(100) == 0 && player.getInterfaceEvent().isExecutable()) {
			player.getInterfaceEvent().execute();
			container.stop();
			return;
		}
		if (objectId > 0) {
			if (Server.getGlobalObjects().exists(Vein.EMPTY_VEIN, location.getX(), location.getY(), location.getZ())) {
				player.sendMessage("This vein contains no more minerals.");
				container.stop();
				return;
			}
		} else {
			if (npc == null || npc.isDead) {
				player.sendMessage("This vein contains no more minerals.");
				container.stop();
				return;
			}
		}
		if (container.getTotalTicks() - lastAnimation > ANIMATION_CYCLE_DELAY) {
			player.animation(pickaxe.getAnimation());
			lastAnimation = container.getTotalTicks();
		}
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (player == null) {
			container.stop();
			return;
		}
		if (Misc.random(mineral.getDepletionProbability()) == 0 || mineral.getDepletionProbability() == 0) {
			if (objectId > 0) {
				Server.getGlobalObjects().add(new GlobalObject(Vein.EMPTY_VEIN, location.getX(),
						location.getY(), location.getZ(), 0, 10, mineral.getRespawnRate(), objectId));
			} else {
				npc.isDead = true;
				npc.actionTimer = 0;
				npc.needRespawn = false;
			}
		}
		player.face(location.getX(), location.getY());
		player.getItems().addItem(mineral.getMineral(), 1);
	}
	
	@Override
	public void stop() {
		if (player == null) {
			return;
		}
		player.stopAnimation();
	}
}
