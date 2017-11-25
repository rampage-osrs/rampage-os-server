package org.brutality.model.players.packets;

import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

public class ChangeRegion implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		c.getPA().removeObjects();
		// Server.objectManager.loadObjects(c);
	}

}
