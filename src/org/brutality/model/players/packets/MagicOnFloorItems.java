package org.brutality.model.players.packets;

import java.util.Objects;

import org.brutality.Server;
import org.brutality.model.multiplayer_session.MultiplayerSessionFinalizeType;
import org.brutality.model.multiplayer_session.MultiplayerSessionStage;
import org.brutality.model.multiplayer_session.MultiplayerSessionType;
import org.brutality.model.multiplayer_session.duel.DuelSession;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.net.Packet;

/**
 * Magic on floor items
 **/
public class MagicOnFloorItems implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		int itemY = packet.getLEShort();
		int itemId = packet.getUnsignedShort();
		int itemX = packet.getLEShort();
		int spellId = packet.getUnsignedShortA();
		if (!c.canUsePackets) {
			return;
		}
		if (!Server.itemHandler.itemExists(itemId, itemX, itemY, c.heightLevel)) {
			c.stopMovement();
			return;
		}
		c.usingMagic = true;
		if (!c.getCombat().checkMagicReqs(51)) {
			c.stopMovement();
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
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
		if (c.goodDistance(c.getX(), c.getY(), itemX, itemY, 12)) {
			/*
			 * int offY = (c.getX() - itemX) * -1; int offX = (c.getY() - itemY)
			 * * -1; c.teleGrabX = itemX; c.teleGrabY = itemY; c.teleGrabItem =
			 * itemId; c.turnPlayerTo(itemX, itemY); c.teleGrabDelay =
			 * System.currentTimeMillis();
			 * c.startAnimation(c.MAGIC_SPELLS[51][2]);
			 * c.gfx100(c.MAGIC_SPELLS[51][3]);
			 * c.getPA().createPlayersStillGfx(144, itemX, itemY, 0, 72);
			 * c.getPA().createPlayersProjectile(c.getX(), c.getY(), offX, offY,
			 * 50, 70, c.MAGIC_SPELLS[51][4], 50, 10, 0, 50);
			 * c.getPA().addSkillXP(c.MAGIC_SPELLS[51][7], 6);
			 * c.getPA().refreshSkill(6); //c.stopMovement();
			 */
			c.sendMessage("Telegrab is disabled.");
		}
	}

}
