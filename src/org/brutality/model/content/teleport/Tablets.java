package org.brutality.model.content.teleport;

import org.brutality.Config;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.players.Player;

/**
 * @author Micheal http://www.rune-server.org/members/01053/
 */

public class Tablets {

	private enum TabletData {
		Lumbridge(8008, 3222, 3219),
		Varrock(8007, 3210, 3424),
		Falador(8009, 2964, 3378),
		Camelot(8010, 2757, 3478),
		Ardougne(8011, 2662, 3305),
		Watchtower(8012, 2549, 3112),
		House(8013, 3087, 3500),
		Lumbridge_Graveyard(19613, 3241, 3194),
		Draynor_Manor(19615, 3108, 3350),
		Cemetery(19627, 2980, 3764),
		Barrows(19629, 3565, 3313);

		private int itemID, coordX, coordY;

		TabletData(int itemID, int coordX, int coordY) {
			this.itemID = itemID;
			this.coordX = coordX;
			this.coordY = coordY;
		}

		public int getItemId() {
			return itemID;
		}

		public int getCoordinateX() {
			return coordX;
		}

		public int getCoordinateY() {
			return coordY;
		}
	}

	public static boolean isTab(int itemId) {
		for (TabletData data : TabletData.values()) {
			if (itemId == data.getItemId()){
				return true;
			}
		}
		return false;
	}
	
	public static void teleport(final Player c, final int itemId) {
		if (c == null || c.disconnected || c.teleporting || c.isDead) {
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		for (TabletData data : TabletData.values()) {
			if (itemId == data.getItemId()) {
				if(data.equals(TabletData.Cemetery) && c.teleAction != 6666) {
					c.getDH().sendOption2("Teleport to the wilderness.", "No thanks.");
					c.teleAction = 6666;
					return;
				}
				c.teleporting = true;
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.getPA().removeAllWindows();
				c.npcIndex = c.playerIndex = 0;
				c.face(null);
				c.getItems().deleteItem(data.getItemId(), 1);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(container.getTotalTicks() == 1) {
							c.animation(4069);
							c.sendMessage("You break the teleport tablet.");
						}
						
						if(container.getTotalTicks() == 2) {
							c.gfx0(678);
							c.animation(4071);
						}
						
						if(container.getTotalTicks() == 4) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						c.getPA().movePlayer(data.getCoordinateX(), data.getCoordinateY(), 0);
						c.animation(65535);
						c.teleporting = false;
					}
				}, 1);
			}
		}
	}
}
