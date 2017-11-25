package org.brutality.model.players.packets;

import org.brutality.model.players.Boundary;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

/**
 * Dialogue
 **/
public class Dialogue implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		final Boundary resourcearena = new Boundary(3182, 3942, 3184, 3944);
		if (!c.canUsePackets) {
			return;
		}
		if (!handleDialogue(c) && c.nextChat > 0) {
			c.getDH().sendDialogues(c.nextChat, c.talkingNpc);
		} 
		else {
			c.getDH().sendDialogues(0, -1);
		}
	}
	public boolean handleDialogue(Player c) {
		if ((c.getDialogue() == null) || (c.getDialogue().getNext() == -1)) {
			c.getPA().removeAllWindows();
		} else if (c.getDialogue().getNext() > -1) {
			c.getDialogue().execute();
			return true;
		}
		return false;
	}
}
