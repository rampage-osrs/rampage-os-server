package org.brutality.model.players.packets.commands.moderator;

import org.brutality.Connection;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.packets.commands.Command;
import org.brutality.util.Misc;

/**
 * Send a global message.
 * 
 * @author Emiel
 */
public class Yell implements Command {
	
	static final String[] ILLEGAL_ARGUMENTS = {
		":tradereq:", "<img", "@cr", "<", "<tran", "#url#", ":duelreq:", ":chalreq:"
	};

	@Override
	public void execute(Player c, String input) {
		String rank = "";
		String message = input;
		if (Connection.isMuted(c)) {
			c.sendMessage("You are muted and can therefore not yell.");
			return;
		}
		if (System.currentTimeMillis() < c.muteEnd) {
			c.sendMessage("You are muted and can therefore not yell.");
			return;
		}
		if (!c.lastYell.elapsed(5000) && c.getRights().isContributor()) {
			c.sendMessage("You are a @red@Contributor@bla@ and must wait 5 seconds between each yell.");
			return;
		}
		if (!c.lastYell.elapsed(5000) && c.getRights().isSponsor()) {
			c.sendMessage("You are a @blu@Sponsor@bla@ and must wait 5 seconds between each yell.");
			return;
		}
		if (!c.lastYell.elapsed(5000) && c.getRights().isSupporter()) {
			c.sendMessage("You are a Supporter and must wait 5 seconds between each yell.");
			return;
		}
		if (!c.lastYell.elapsed(5000) && c.getRights().isVIP()) {
			c.sendMessage("You are a <col=FF00CD>VIP</col>@bla@ and must wait 5 seconds between each yell.");
			return;
		}
		
		String playerTitle = c.getTitles().getCurrentTitle();
		
		if (c.getRights().isContributor()) {
			rank = "[<img=4>@red@" + playerTitle + "@bla@][@blu@" + c.playerName + "@bla@]:@dre@";
		}
		if (c.getRights().isSponsor()) {
			rank = "[<img=5>@blu@" + playerTitle + "@bla@][@blu@" + c.playerName + "@bla@]:@dre@";
		}
		if (c.getRights().isSupporter()) {
			rank = "[<img=6><col=148200>" + playerTitle + "</col>@bla@][@blu@" + c.playerName + "@bla@]:@dre@";
		}
		if (c.getRights().isVIP()) {
			rank = "[<img=7><col=FF00CD>" + playerTitle + "</col>@bla@][@blu@" + c.playerName + "@bla@]:@dre@";
		}
		if (c.getRights().isHelper()) {
			rank = "[<img=10>@blu@" + playerTitle + "@bla@][@blu@" + c.playerName + "@bla@]:@dre@";
		}
		if (c.getRights().isModerator()) {
			rank = "[<img=0><col=148200>Moderator</col>@bla@][@blu@" + c.playerName + "@bla@]:@dre@";
		}
		if(c.getRights().isYoutuber()) {
			rank = "[<col=B0171F><img=9>Youtuber]</col>[" + Misc.ucFirst(c.playerName) + "]:<col=0000FF>";
		}
		if (c.getRights().isAdministrator()) {
			rank = "[<img=2>@yel@Administrator@bla@][@blu@" + Misc.ucFirst(c.playerName) + "@bla@]:@dre@";
		}
		if (c.getRights().isOwner() && !c.playerName.equalsIgnoreCase("Pures1")) {
			rank = "[<col=A67711><img=2>Owner</col>][" + Misc.ucFirst(c.playerName) + "]:<col=0000FF>";
		}
		if (c.getRights().isDeveloper()) {
			rank = "[<col=5E14A7><img=8>Developer]</col>[" + Misc.ucFirst(c.playerName) + "]:<col=0000FF>";
		}
		if(c.getRights().isStreamer()) {
			rank = "[<col=B0171F><img=13>Streamer]</col>[" + Misc.ucFirst(c.playerName) + "]:<col=0000FF>";
		}
		
		message = message.toLowerCase();
		for (String argument : ILLEGAL_ARGUMENTS) {
			if (message.contains(argument)) {
				c.sendMessage("Your message contains an illegal set of characters, you cannot yell this.");
				return;
			}
		}
		message = Misc.ucFirst(message);
		PlayerHandler.executeGlobalMessage(rank + message);
	}
}
