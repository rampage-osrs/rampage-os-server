package org.brutality.model.players.packets;

import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.net.Packet;

public class FollowPlayer implements PacketType {
	
	@Override
	public void processPacket(Player c, Packet packet) {
		int followPlayer = packet.getLEShort();
		if (!c.canUsePackets) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (c.doingTutorial) {
			return;
		}
		if (PlayerHandler.players[followPlayer] != null) {
			c.playerIndex = 0;
			c.npcIndex = 0;
			c.mageFollow = false;
			c.usingBow = false;
			c.usingRangeWeapon = false;
			c.followDistance = 1;
			c.followId = followPlayer;
		}
	}	
}