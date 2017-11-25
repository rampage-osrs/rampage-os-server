package org.brutality.model.players.packets;

import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;
import org.brutality.util.Misc;

/**
 * Chat
 **/
public class ClanChat implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		String textSent = Misc.longToPlayerName2(packet.getLong());
		textSent = textSent.replaceAll("_", " ");
	}
}