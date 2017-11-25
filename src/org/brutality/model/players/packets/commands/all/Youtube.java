package org.brutality.model.players.packets.commands.all;

import org.brutality.Config;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.players.Player;
import org.brutality.model.players.packets.commands.Command;
import org.brutality.util.Misc;

public class Youtube implements Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendFrame126("www.youtube.com/watch?v=Y8gjDEGQcVc", 12000);
		c.sendMessage("Watch video till the end to claim 10 PK Points.");
		
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.sendMessage("Thank you for watching today's YouTube video!");
				
				if (System.currentTimeMillis() - c.youtubeTimestamp < 86400000) {
					c.sendMessage("You need to wait 24 hours to receive another reward.");
				} else {
					c.sendMessage("You received 50 platinum tokens a reward.");
					c.pkp += 10;
					c.youtubeTimestamp = System.currentTimeMillis();
				}
				
				container.stop();
			}
		}, Misc.random(180) + 120);
		
		
	}

}