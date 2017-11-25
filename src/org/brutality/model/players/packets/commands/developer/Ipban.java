package org.brutality.model.players.packets.commands.developer;

import java.util.Optional;

import org.brutality.Connection;
import org.brutality.Server;
import org.brutality.model.multiplayer_session.MultiplayerSession;
import org.brutality.model.multiplayer_session.MultiplayerSessionFinalizeType;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.packets.commands.Command;

/**
 * IP-Ban a given player.
 * 
 * @author Emiel
 */
public class Ipban implements Command {

	@Override
	public void execute(Player c, String input) {
		try {
			String[] args = input.split("-");
			if (args.length != 2) {
				throw new IllegalArgumentException();
			}
			String name = args[0];
			String reason = args[1];
			Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(name);
			if (optionalPlayer.isPresent()) {
				Player c2 = optionalPlayer.get();
				if (c.playerName == c2.playerName) {
					c.sendMessage("You cannot IP Ban yourself.");
					return;
				} 
				if (c2.getRights().isBetween(2,  3)) {
					c.sendMessage("You cannot ban this player.");
					return;
				}
				if (!Connection.isIpBanned(c2.connectedFrom)) {
					Connection.addNameToBanList(name, Long.MAX_VALUE);
					Connection.addNameToFile(name, Long.MAX_VALUE);
					Connection.addIpToBanList(c2.connectedFrom);
					Connection.addIpToFile(c2.connectedFrom);
					if (Server.getMultiplayerSessionListener().inAnySession(c2)) {
						MultiplayerSession session = Server.getMultiplayerSessionListener().getMultiplayerSession(c2);
						session.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
					}
					c2.disconnected = true;
					c.sendMessage("You have IP banned the user: " + c2.playerName + " with the host: " + c2.connectedFrom);
				} else {
					c.sendMessage("This user is already IP Banned.");
				}
			} else {
				c.sendMessage(name + " is not online. Use ::banip instead to IP-Ban an offline player.");
			}
		} catch (Exception e) {
			c.sendMessage("Error. Correct syntax: ::ipban-player-reason");
		}
	}
}
