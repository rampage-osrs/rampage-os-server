package org.brutality.model.npcs.boss.Kalphite;

import java.util.HashMap;
import java.util.Map;

import org.brutality.event.CycleEventHandler;
import org.brutality.model.npcs.NPC;
import org.brutality.model.npcs.boss.Kalphite.impl.RespawnNpc;
import org.brutality.model.npcs.boss.Kalphite.impl.SpawnKalphite;
import org.brutality.model.npcs.boss.instances.InstancedArea;
import org.brutality.model.npcs.boss.instances.InstancedAreaManager;
import org.brutality.model.npcs.boss.instances.SingleInstancedArea;
import org.brutality.model.npcs.boss.instances.impl.SingleInstancedKalphite;
import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;

public class Kalphite {
	
	private final Object EVENT_LOCK = new Object();

	private final Player player;
	
	private SingleInstancedArea kalphiteInstance;
	
	public static final Boundary BOUNDARY = new Boundary(3452, 9466, 3524, 9536);
	private NPC npc;

	private Map<Integer, Stage> stages = new HashMap<>();

	public Kalphite(Player player) {
		this.player = player;
		stages.put(0, new SpawnKalphite(this, player));
		stages.put(1, new RespawnNpc(this, player));
	}
	
	public void initialize() {
		if (kalphiteInstance != null) {
			InstancedAreaManager.getSingleton().disposeOf(kalphiteInstance);
		}
		int height = InstancedAreaManager.getSingleton().getNextOpenHeight(BOUNDARY);
		kalphiteInstance = new SingleInstancedKalphite(player, BOUNDARY, height);
		InstancedAreaManager.getSingleton().add(height, kalphiteInstance);
		if (kalphiteInstance == null) {
			player.sendMessage("An error occured while trying to enter Bandos instanced. Please try again.");
			return;
		}
		player.getPA().removeAllWindows();
		player.stopMovement();
		player.getPA().sendScreenFade("Welcome to Kalphite...", 1, 3);
		player.KALPHITE_INSTANCE = true;
		CycleEventHandler.getSingleton().addEvent(player, stages.get(0), 1);
	}
	
	public void RespawnNpc() {
		if (kalphiteInstance != null) {
			InstancedAreaManager.getSingleton().disposeOf(kalphiteInstance);
		}
		int height = InstancedAreaManager.getSingleton().getNextOpenHeight(BOUNDARY);
		kalphiteInstance = new SingleInstancedKalphite(player, BOUNDARY, height);
		InstancedAreaManager.getSingleton().add(height, kalphiteInstance);
		if (kalphiteInstance == null) {
			player.sendMessage("An error occured while trying to enter Bandos instanced. Please try again.");
			return;
		}
		player.getPA().removeAllWindows();
		player.KALPHITE_INSTANCE = true;
		CycleEventHandler.getSingleton().addEvent(player, stages.get(1), 1);
	}

	public void stop() {
		try {
		CycleEventHandler.getSingleton().stopEvents(EVENT_LOCK);
		kalphiteInstance.onDispose();
		InstancedAreaManager.getSingleton().disposeOf(kalphiteInstance);
		kalphiteInstance = null;
		} catch (Exception e) {
		}
	}

	public InstancedArea getInstancedKalphite() {
		return kalphiteInstance;
	}

	public NPC getNpc() {
		return npc;
	}
	
	public void setNpc(NPC npc) {
		this.npc = npc;
	}
	
	public static void enter(Player player) {
		if (player.wildLevel > 20) {
			player.sendMessage("You cannot teleport above 20 wilderness.");
			player.getPA().closeAllWindows();
			return;
		}
		if (player.KALPHITE_CLICKS >= 1) {
			player.sendMessage("You already have an active instance!");
			player.getPA().closeAllWindows();
			return;
		}
		if (player.getKalphite().getInstancedKalphite() != null) {
			player.sendMessage("You already have an active instance!");
			player.getPA().closeAllWindows();
			return;
		}
		player.getKalphite().initialize();
		player.KALPHITE_CLICKS = 1;
	}
	
}
