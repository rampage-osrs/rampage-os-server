package org.brutality.model.npcs.boss.abstract_bosses;

import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.players.Player;
import org.brutality.model.players.combat.Hitmark;
import org.brutality.util.Misc;

public class Ability {
	
	public static void KnockBack(Player c, int x, int y) {
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if(container.getTotalTicks() == 1) {
					c.animation(807);
				}
				
				if(container.getTotalTicks() == 2) {
					c.resetWalkingQueue();
					c.teleportToX = x;
					c.teleportToY = y;
					c.getPA().requestUpdates();
					container.stop();
				}
			}
			
			public void stop() {
				c.appendDamage(Misc.random(10), Hitmark.HIT);
				c.sendMessage("You feel yourself get knocked back and take some damage!");
			}
		}, 2);
	}
}