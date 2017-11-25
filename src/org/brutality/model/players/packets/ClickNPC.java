package org.brutality.model.players.packets;

import java.util.Objects;

import org.brutality.Config;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.npcs.NPC;
import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.NpcDefinition;
import org.brutality.model.npcs.boss.abyssalsire.AbyssalSireConstants;
import org.brutality.model.players.PacketType;
import org.brutality.model.players.Player;
import org.brutality.model.players.combat.AttackNPC;
import org.brutality.net.Packet;
import org.brutality.util.Stream;

/**
 * Click NPC
 */
public class ClickNPC implements PacketType {
	public static final int ATTACK_NPC = 72, MAGE_NPC = 131, FIRST_CLICK = 155, SECOND_CLICK = 17, THIRD_CLICK = 21,
			FOURTH_CLICK = 18;

	@Override
	public void processPacket(final Player c, Packet packet) {
		c.npcIndex = 0;
		c.npcClickIndex = 0;
		c.playerIndex = 0;
		c.clickNpcType = 0;
		c.getPA().resetFollow();
		if (!c.canUsePackets) {
			return;
		}

		if (c.doingTutorial) {
			// System.out.println("You cannot click NPC's whilst in the Tutorial
			// Phase.");
			return;
		}

		switch (packet.getOpcode()) {

		/**
		 * Attack npc melee or range
		 **/
		case ATTACK_NPC:

			/*if (!c.mageAllowed) {
				c.mageAllowed = true;
				c.sendMessage("I can't reach that.");
				break;
			}*/

			c.npcIndex = packet.getUnsignedShortA();
			if (c.npcIndex >= NPCHandler.npcs.length || c.npcIndex < 0) {
				return;
			}
			NPC npc = NPCHandler.npcs[c.npcIndex];
			if (NPCHandler.npcs[c.npcIndex] == null) {
				c.npcIndex = 0;
				break;
			}
			if (NpcDefinition.get(npc.npcType).getCombatLevel() < 1) {
				c.npcIndex = 0;
				break;
			}
			if (NPCHandler.npcs[c.npcIndex].maximumHealth == 0) {
				c.sendMessage("You can't attack this MOB: " + NPCHandler.npcs[c.npcIndex].npcType);
				c.npcIndex = 0;
				break;
			}
			if (c.autocastId > 0)
				c.autocasting = true;
			if (!c.autocasting && c.spellId > 0) {
				c.spellId = 0;
			}
			c.face(NPCHandler.npcs[c.npcIndex]);
			c.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 11785
					|| c.playerEquipment[c.playerWeapon] == 8880 || c.playerEquipment[c.playerWeapon] == 19481
					|| c.playerEquipment[c.playerWeapon] == 10156;
			if (c.playerEquipment[c.playerWeapon] >= 4214 && c.playerEquipment[c.playerWeapon] <= 4223)
				usingBow = true;
			for (int bowId : c.BOWS) {
				if (c.playerEquipment[c.playerWeapon] == bowId) {
					usingBow = true;
					for (int arrowId : c.ARROWS) {
						if (c.playerEquipment[c.playerArrows] == arrowId) {
							usingArrows = true;
						}
					}
				}
			}
			for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
				if (c.playerEquipment[c.playerWeapon] == otherRangeId) {
					usingOtherRangeWeapons = true;
				}
			}
			/*
			 * if ((usingBow || c.autocasting) && c.goodDistance(c.getX(),
			 * c.getY(), NPCHandler.npcs[c.npcIndex].getX(),
			 * NPCHandler.npcs[c.npcIndex].getY(), 7)) { c.stopMovement(); } if
			 * (usingOtherRangeWeapons && c.goodDistance(c.getX(), c.getY(),
			 * NPCHandler.npcs[c.npcIndex].getX(),
			 * NPCHandler.npcs[c.npcIndex].getY(), 7)) { c.stopMovement(); }
			 */

			if ((usingOtherRangeWeapons || usingBow || c.autocasting) && (c.goodDistance(c.getX(), c.getY(),
					NPCHandler.npcs[c.npcIndex].getX(), NPCHandler.npcs[c.npcIndex].getY(), 7)
					|| AttackNPC.SKIP_DISTANCE_CHECK.contains(c.npcIndex))) {
				c.stopMovement();
			}

			if (!usingCross && !usingArrows && usingBow && c.playerEquipment[c.playerWeapon] < 4212
					&& c.playerEquipment[c.playerWeapon] > 4223 && !usingCross) {
				c.sendMessage("You have run out of arrows!");
				break;
			}
			if (c.getCombat().correctBowAndArrows() < c.playerEquipment[c.playerArrows] && Config.CORRECT_ARROWS
					&& usingBow && !c.getCombat().usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 9185
					&& c.playerEquipment[c.playerWeapon] != 19481 && c.playerEquipment[c.playerWeapon] != 11785
					&& c.playerEquipment[c.playerWeapon] != 8880 && c.playerEquipment[c.playerWeapon] != 10156) {
				c.sendMessage("You can't use "
						+ c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase() + "s with a "
						+ c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + ".");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.playerEquipment[c.playerWeapon] == 9185 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 11785 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 8880 && !c.getCombat().properBolts()) {
				c.sendMessage("You must use bolts with a crossbow.");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.playerEquipment[c.playerWeapon] == 10156 && c.playerEquipment[c.playerArrows] != 10158 && c.playerEquipment[c.playerArrows] != 10159){
				c.sendMessage("You must use kebbit bolts or long kebbit bolts with this crossbow.");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.playerEquipment[c.playerWeapon] == 19481 && !c.getCombat().usingJavelins()) {
				c.sendMessage("You must use javelins with a ballista.");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.followId > 0) {
				c.getPA().resetFollow();
			}
			if (c.attackTimer <= 0) {
				c.getCombat().attackNpc(c.npcIndex);
				c.attackTimer++;
			}

			break;

		/**
		 * Attack npc with magic
		 **/
		case MAGE_NPC:
			if (!c.mageAllowed) {
				c.mageAllowed = true;
				c.sendMessage("I can't reach that.");
				break;
			}
			c.npcIndex = packet.getLEShortA();
			int castingSpellId = packet.getShortA();
			c.usingMagic = false;

			if (c.npcIndex >= NPCHandler.npcs.length || c.npcIndex < 0 || NPCHandler.npcs[c.npcIndex] == null) {
				c.npcIndex = 0;
				break;
			}

			npc = NPCHandler.npcs[c.npcIndex];

			if (NpcDefinition.get(npc.npcType).getCombatLevel() < 1) {
				c.npcIndex = 0;
				break;
			}

			if (NPCHandler.npcs[c.npcIndex].maximumHealth == 0 || NPCHandler.npcs[c.npcIndex].npcType == 944) {
				c.npcIndex = 0;
				c.sendMessage("You can't attack this npc.");
				break;
			}

			for (int i = 0; i < c.MAGIC_SPELLS.length; i++) {
				if (castingSpellId == c.MAGIC_SPELLS[i][0]) {
					c.spellId = i;
					c.usingMagic = true;
					break;
				}
			}
			if (castingSpellId == 1171) { // crumble undead
				for (int npcId : Config.UNDEAD_NPCS) {
					if (NPCHandler.npcs[c.npcIndex].npcType != npcId) {
						c.sendMessage("You can only attack undead monsters with this spell.");
						c.usingMagic = false;
						c.stopMovement();
						break;
					}
				}
			}

			if (c.autocasting)
				c.autocasting = false;

			if (c.usingMagic) {
				if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcIndex].getX(),
						NPCHandler.npcs[c.npcIndex].getY(), 6)) {
					c.stopMovement();
				}
				if (c.attackTimer <= 0) {
					c.getCombat().attackNpc(c.npcIndex);
					c.attackTimer++;
				}
			}

			break;

		case FIRST_CLICK:
			c.npcClickIndex = packet.getLEShort();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}
			if (NPCHandler.npcs[c.npcClickIndex] != null) {
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
				if (c.npcType == AbyssalSireConstants.SLEEPING_NPC_ID) {
					c.npcIndex = c.npcClickIndex;
					c.getCombat().attackNpc(c.npcClickIndex);
					break;
				}
				if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
						c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getSize())) {
					c.face(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
					NPCHandler.npcs[c.npcClickIndex].face(c);
					c.getActions().firstClickNpc(c.npcType, c.npcClickIndex);
				} else {
					c.clickNpcType = 1;
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if ((c.clickNpcType == 1) && NPCHandler.npcs[c.npcClickIndex] != null) {
								if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY(),
										NPCHandler.npcs[c.npcClickIndex].getSize())) {
									c.face(NPCHandler.npcs[c.npcClickIndex].getX(),
											NPCHandler.npcs[c.npcClickIndex].getY());
									NPCHandler.npcs[c.npcClickIndex].face(c);
									c.getActions().firstClickNpc(c.npcType, c.npcClickIndex);
									container.stop();
								}
							}
							if (c.clickNpcType == 0 || c.clickNpcType > 1)
								container.stop();
						}

						@Override
						public void stop() {
							c.clickNpcType = 0;
						}
					}, 1);
				}
			}
			break;

		case SECOND_CLICK:
			//c.npcClickIndex = packet.getUnsignedShortA();
			c.npcClickIndex = packet.getLEShortA();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			if (NPCHandler.npcs[c.npcClickIndex] != null)
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
			if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
					c.getX(), c.getY(), 1)) {
				c.face(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
				NPCHandler.npcs[c.npcClickIndex].face(c);
				c.getActions().secondClickNpc(c.npcType, c.npcClickIndex);
			} else {
				c.clickNpcType = 2;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if ((c.clickNpcType == 2) && NPCHandler.npcs[c.npcClickIndex] != null) {
							if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
									NPCHandler.npcs[c.npcClickIndex].getY(), 1)) {
								c.face(NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY());
								NPCHandler.npcs[c.npcClickIndex].face(c);
								c.getActions().secondClickNpc(c.npcType, c.npcClickIndex);
								container.stop();
							}
						}
						if (c.clickNpcType < 2 || c.clickNpcType > 2)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
			}
			break;

		case THIRD_CLICK:
			c.npcClickIndex = packet.getShort();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			if (NPCHandler.npcs[c.npcClickIndex] != null)
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
			if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
					c.getX(), c.getY(), 1)) {
				c.face(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
				NPCHandler.npcs[c.npcClickIndex].face(c);
				c.getActions().thirdClickNpc(c.npcType);
			} else {
				c.clickNpcType = 3;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if ((c.clickNpcType == 3) && NPCHandler.npcs[c.npcClickIndex] != null) {
							if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
									NPCHandler.npcs[c.npcClickIndex].getY(), 1)) {
								c.face(NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY());
								NPCHandler.npcs[c.npcClickIndex].face(c);
								c.getActions().thirdClickNpc(c.npcType);
								container.stop();
							}
						}
						if (c.clickNpcType < 3)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
			}
			break;

		case ClickNPC.FOURTH_CLICK:
			c.npcClickIndex = packet.getUnsignedByte();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			if (Objects.isNull(NPCHandler.npcs[c.npcClickIndex])) {
				break;
			}
			if (NPCHandler.npcs[c.npcClickIndex] != null)
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
			if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
					c.getX(), c.getY(), 1)) {
				c.face(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
				NPCHandler.npcs[c.npcClickIndex].face(c);
				c.getActions().operateNpcAction4(c.npcType);
			} else {
				c.clickNpcType = 4;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if ((c.clickNpcType == 4) && NPCHandler.npcs[c.npcClickIndex] != null) {
							if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
									NPCHandler.npcs[c.npcClickIndex].getY(), 1)) {
								c.face(NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY());
								NPCHandler.npcs[c.npcClickIndex].face(c);
								c.getActions().operateNpcAction4(c.npcType);
								container.stop();
							}
						}
						if (c.clickNpcType < 4)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
			}
			break;
		}

	}
}
