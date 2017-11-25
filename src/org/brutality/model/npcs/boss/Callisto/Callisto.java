package org.brutality.model.npcs.boss.Callisto;

import java.util.Random;

import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.npcs.NPC;
import org.brutality.model.players.Player;

/**
 * Callisto Ability class
 * @author Micheal/01053
 *
 */

public class Callisto {
	
	public static void KnockBack(Player c, int x, int y) {
		c.animation(807);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				c.resetWalkingQueue();
				c.teleportToX = x;
				c.teleportToY = y;
				c.getPA().requestUpdates();
				container.stop();
			}
		}, 2);
	}
}
