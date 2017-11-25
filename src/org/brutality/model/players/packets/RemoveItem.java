package org.brutality.model.players.packets;

import java.util.Objects;
import java.util.Optional;

import org.brutality.Server;
import org.brutality.model.content.PriceChecker;
import org.brutality.model.items.GameItem;
import org.brutality.model.minigames.hunger.HungerManager;
import org.brutality.model.minigames.hunger.PlayerWrapper;
import org.brutality.model.multiplayer_session.MultiplayerSession;
import org.brutality.model.multiplayer_session.MultiplayerSessionFinalizeType;
import org.brutality.model.multiplayer_session.MultiplayerSessionStage;
import org.brutality.model.multiplayer_session.MultiplayerSessionType;
import org.brutality.model.multiplayer_session.duel.DuelSession;
import org.brutality.model.multiplayer_session.trade.TradeSession;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.model.players.skills.Smithing;
import org.brutality.model.players.skills.crafting.JewelryMaking;
import org.brutality.net.Packet;

/**
 * Remove Item
 **/
public class RemoveItem implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		int interfaceId = packet.getUnsignedShortA();
		int removeSlot = packet.getUnsignedShortA();
		int removeId = packet.getUnsignedShortA();
		
		if (!c.canUsePackets) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if(c.hungerGames && removeId == 2405) {
			PlayerWrapper play = HungerManager.getSingleton().getWrapper(c);
			if(play != null && !play.betrayedLanthus()) {
				c.getDH().sendNpcChat3("No, no! You must keep the mark on!", "Otherwise our lord will not accept", "your sacrifice!", 5721, "Lanthus");
				return;
			} else if(play != null && play.betrayedLanthus()) {
				c.getDH().sendNpcChat1("You will regret the day you betrayed me...", 4178, "Lanthus");	
			}
		}
		
		switch (interfaceId) {
		case 7423:
			if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				Server.getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot add items to the deposit box whilst trading.");
				return;
			}
			c.getItems().addToBank(removeId, 1, false);
			c.getItems().resetItems(7423);
			break;
		case 4233:
		case 4239:
		case 4245:
			JewelryMaking.mouldItem(c, removeId, 1);
			break;
		case 1688:
			if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.TRADE).
					finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot remove items whilst trading, trade declined.");
				return;
			}
			DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST &&
					duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERACTION) {
				c.sendMessage("Your actions have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			c.getItems().removeItem(removeId, removeSlot);
			break;

			//Remove item from price checker interface
		case 43933:
			PriceChecker.withdrawItem(c, removeId, removeSlot, 1);
			break;
			
		case 5064:
			if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				Server.getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot add items to the bank whilst trading.");
				return;
			}
			duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST &&
					duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERACTION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.isChecking){
				PriceChecker.depositItem(c, removeId, 1);
			}
			if (c.isBanking) {
				c.getItems().addToBank(removeId, 1, true);
			}
			break;
		case 5382:
			if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				Server.getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot remove items from the bank whilst trading.");
				return;
			}
			duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST &&
					duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERACTION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
        	if(c.getBank().getBankSearch().isSearching()) {
        		c.getBank().getBankSearch().removeItem(removeId, 1);
        		return;
        	}
            c.getItems().removeFromBank(removeId, 1, true);
			break;

		case 3900:
			c.getShops().buyFromShopPrice(removeId, removeSlot);
			break;

		case 3823:
			c.getShops().sellToShopPrice(removeId, removeSlot);
			break;

		case 3322:
			MultiplayerSession session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession || session instanceof DuelSession) {
				session.addItem(c, new GameItem(removeId, 1));
			}
			break;

		case 3415:
			session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession) {
				session.removeItem(c, removeSlot, new GameItem(removeId, 1));
			}
			break;

		case 6669:
			session = Server.getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof DuelSession) {
				session.removeItem(c, removeSlot, new GameItem(removeId, 1));
			}
			break;

		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			Smithing.readInput(c.playerLevel[Player.playerSmithing],
					Integer.toString(removeId), c, 1);
			break;

		}
	}

}
