package org.brutality.model.players.packets;

import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

public class Report implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		if (!c.canUsePackets) {
			return;
		}
		try {
			ReportHandler.handleReport(c, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}