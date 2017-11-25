package org.brutality.model.players.packets.action;

import org.brutality.Server;
import org.brutality.model.content.clan.Clan;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;
import org.brutality.util.Misc;

public class JoinChat implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		String owner = Misc.longToPlayerName2(packet.getLong())
				.replaceAll("_", " ");
		if (owner != null && owner.length() > 0) {
			if (player.clan == null) {
				/*if (player.inArdiCC) {
					return;
				}*/
				Clan clan = Server.clanManager.getClan(owner);
				if (clan != null) {
					clan.addMember(player);
				} else if (owner.equalsIgnoreCase(player.playerName)) {
					Server.clanManager.create(player);
				} else {
					player.sendMessage(Misc.formatPlayerName(owner)
							+ " has not created a clan yet.");
				}
				player.getPA().refreshSkill(21);
				player.getPA().refreshSkill(22);
				player.getPA().refreshSkill(23);
			}
		}
	}

}