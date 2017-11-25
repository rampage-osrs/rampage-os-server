package org.brutality.model.players.packets;

import java.util.Objects;

import org.brutality.Server;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.minigames.FishingTourney;
import org.brutality.model.multiplayer_session.MultiplayerSessionFinalizeType;
import org.brutality.model.multiplayer_session.MultiplayerSessionStage;
import org.brutality.model.multiplayer_session.MultiplayerSessionType;
import org.brutality.model.multiplayer_session.duel.DuelSession;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

/**
 * Pickup Item
 **/
public class PickupItem implements PacketType {

	@Override
	public void processPacket(final Player c, Packet packet) {
		c.walkingToItem = false;
		c.pItemY = packet.getLEShort();
		c.pItemId = packet.getUnsignedShort();
		c.pItemX = packet.getLEShort();
		if (!c.canUsePackets) {
			return;
		}
		if (Math.abs(c.getX() - c.pItemX) > 25
				|| Math.abs(c.getY() - c.pItemY) > 25) {
			c.resetWalkingQueue();
			return;
		}
		DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERACTION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}		
		if(c.fishTourneySession != null) {
			c.sendMessage("You cannot pickup items during a fishing tourney!");
			return;
		}
		if(c.pItemId == 3438) {
			c.sendMessage("These logs look too heavy to pickup!");
			return;
		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}
		c.getCombat().resetPlayerAttack();
		if (c.getX() == c.pItemX && c.getY() == c.pItemY) {
			Server.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX,
					c.pItemY, c.heightLevel, true);
		} else {
			c.walkingToItem = true;
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (!c.walkingToItem)
						container.stop();
					if (c.getX() == c.pItemX && c.getY() == c.pItemY) {
						Server.itemHandler.removeGroundItem(c, c.pItemId,
								c.pItemX, c.pItemY, c.heightLevel, true);
						container.stop();
					}
				}

				@Override
				public void stop() {
					c.walkingToItem = false;
				}
			}, 1);
		}

	}

}
