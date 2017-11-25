package org.brutality.model.players.combat;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.brutality.Config;
import org.brutality.Server;
import org.brutality.clip.PathChecker;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.minigames.pest_control.PestControl;
import org.brutality.model.minigames.warriors_guild.WarriorsGuild;
import org.brutality.model.npcs.NPC;
import org.brutality.model.npcs.NPCHandler;
import org.brutality.model.npcs.boss.Gorillas.DemonicGorilla;
import org.brutality.model.npcs.boss.Kalphite.Queen;
import org.brutality.model.npcs.boss.Kraken.impl.SpawnEntity;
import org.brutality.model.players.Boundary;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.combat.effects.AmuletOfTheDamnedKarilEffect;
import org.brutality.model.players.combat.range.RangeData;
import org.brutality.model.players.combat.range.RangeExtras;
import org.brutality.model.players.skills.Skill;
import org.brutality.model.players.skills.mining.Pickaxe;
import org.brutality.model.players.skills.slayer.EasyTask.Task;
import org.brutality.model.players.skills.slayer.HardTask.Hard;
import org.brutality.model.players.skills.slayer.MediumTask.Medium;
import org.brutality.util.Misc;

public class AttackNPC {

	public static boolean kalphite1(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 1158:
			return true;
		}
		return false;
	}

	public int id;

	public static int spearDamage(Player c) {
		int damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
		return damage *= 1.45;
	}

	public static int kerisDamage(Player c) {
		int damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
		return damage *= 1.65;
	}

	public static boolean kalphite2(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 1160:
			return true;
		}
		return false;
	}

	public static int salveDamage(Player c) {
		int damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
		return damage *= 1.15;
	}

	public static int adjustDamageTaken(NPC npc, Player player, int damage) {
		if (DemonicGorilla.isDemonicGorilla(npc) && DemonicGorilla.isProtecting(player, npc)) {
			return 0;
		}
		
		boolean melee = !player.usingCross && !player.usingBow && !player.usingMagic
				&& !player.usingOtherRangeWeapons;
		
		boolean corporealBeast = npc.npcType == 319;
		boolean cerberus = npc.npcType == 5862;
		if (!melee) {
			if (corporealBeast) {
				player.sendMessage("Only a sharp weapon can pierce through a Corporeal Beast.");
				return 0;
			} else if (cerberus) {
				player.sendMessage("Your current attack style is ineffective against Cerberus.");
				return 0;
			}
		}
		
		return damage;
	}

	public static void applyNpcMeleeDamage(Player c, int i, int damageMask, int damage, int defence) {
//		if (!c.usingClaws) {
//			damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
//		}
		if (NPCHandler.npcs[i].npcType == 6600) {
			Pickaxe pickaxe = Pickaxe.getBestPickaxe(c);
			if (pickaxe != null && c.getItems().isWearingItem(pickaxe.getItemId())) {
				damage += Misc.random(pickaxe.getPriority() * 4);
			}
		}

		damage = adjustDamageTaken(NPCHandler.npcs[i], c, damage);

		c.previousDamage = damage;

		boolean fullVeracsEffect = c.getPA().fullVeracs() && Misc.random(3) == 1;
		if (NPCHandler.npcs[i].HP - damage < 0) {
			damage = NPCHandler.npcs[i].HP;
		}
		if (NPCHandler.npcs[i].npcType == 5666) {
			damage = damage / 4;
			if (damage < 0) {
				damage = 0;
			}
		}

		if (NPCHandler.npcs[i].npcType == 7153) {
			damage = damage / 4;
		} else if (damage < 0) {
			damage = 0;
		}

		if (c.getPA().salveAmulet()) {
			if (Server.npcHandler.isUndead(i) && damage < 0) {
				damage = salveDamage(c);
				if (damage > NPCHandler.npcs[i].HP) {
					damage = NPCHandler.npcs[i].HP;
				} else {
					damage = damage;
				}
			}
		}
		if (!fullVeracsEffect) {
			if (Misc.random(defence) > 10 + Misc.random(c.getCombat().calculateMeleeAttack())) {
				damage = 0;
			} else if (NPCHandler.npcs[i].npcType == 2882 || NPCHandler.npcs[i].npcType == 2883) {
				damage = 0;
			}
		}

		boolean guthansEffect = false;
		if (c.getPA().fullGuthans()) {
			if (Misc.random(3) == 1) {
				guthansEffect = true;
			}
		}

		if (c.getPA().corpSpear()) {
			if (NPCHandler.npcs[i].npcType == 319 || NPCHandler.npcs[i].npcType == 320 && damage > 0) {
				damage = spearDamage(c);
				if (damage > NPCHandler.npcs[i].HP) {
					damage = NPCHandler.npcs[i].HP;
				} else {
					damage = damage;
				}
			}
		}

		if (c.getPA().Keris()) {
			if (damage < 0 && NPCHandler.npcs[i].npcType == 963) {
				damage = kerisDamage(c);
				c.sendMessage("Damage: " + kerisDamage(c));
				if (damage > NPCHandler.npcs[i].HP) {
					damage = NPCHandler.npcs[i].HP;
				} else {
					damage = damage;
				}
			}
		}

		if (c.playerEquipment[c.playerWeapon] == 4718 && c.playerEquipment[c.playerHat] == 4716
				&& c.playerEquipment[c.playerChest] == 4720 && c.playerEquipment[c.playerLegs] == 4722) {
			damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
		}

		damage = adjustDamageTaken(NPCHandler.npcs[i], c, damage);

		if (c.playerEquipment[c.playerHat] == 11865 && c.slayerTask == NPCHandler.npcs[i].npcType) {
			// Slayer Helm (i)
			damage *= 1.15;
		}

		if (NPCHandler.npcs[i].HP - damage < 0) {
			damage = NPCHandler.npcs[i].HP;
		}
		if (c.fightMode == 3) {
			c.getPA().addSkillXP((damage * Config.MELEE_EXP_RATE / 3), 3);
			c.getPA().addSkillXP((damage * Config.MELEE_EXP_RATE / 3), 0);
			c.getPA().addSkillXP((damage * Config.MELEE_EXP_RATE / 3), 1);
			c.getPA().addSkillXP((damage * Config.MELEE_EXP_RATE / 3), 2);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(1);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
		} else {
			c.getPA().addSkillXP((damage * Config.MELEE_EXP_RATE), 3);
			c.getPA().addSkillXP((damage * Config.MELEE_EXP_RATE), c.fightMode);
			c.getPA().refreshSkill(c.fightMode);
			c.getPA().refreshSkill(3);
		}
		if (damage > 0) {
			// Pest Control shit was here
		}
		if (damage > 0 && guthansEffect) {
			c.playerLevel[3] += damage;
			if (c.playerLevel[3] > c.getMaximumHealth())
				c.playerLevel[3] = c.getMaximumHealth();
			c.getPA().refreshSkill(3);
			NPCHandler.npcs[i].gfx0(398);
		}
		if (damage > 0 && guthansEffect && c.getPA().wearingGuthan(c)
				&& c.playerLevel[3] == c.getPA().getLevelForXP(c.playerXP[3])) {
			c.playerLevel[3] += 11;
			// c.playerLevel[3] = c.getMaximumHealth();
			c.getPA().refreshSkill(3);
			NPCHandler.npcs[i].gfx0(398);
			c.sendMessage("@blu@You feel your amulet start to empower and boost your maximum health.");
		}

		NPCHandler.npcs[i].underAttack = true;
		c.killingNpcIndex = c.npcIndex;
		c.lastNpcAttacked = i;

		switch (c.specEffect) {
		case 4:
			if (damage > 0) {
				if (c.playerLevel[3] + damage > c.getMaximumHealth())
					c.playerLevel[3] = c.getMaximumHealth();
				else
					c.playerLevel[3] += damage / 2;
				c.playerLevel[5] += damage / 4;
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(5);
			}
			break;

		case 3:
			if (damage > 0) {
				NPCHandler.npcs[i].defence -= NPCHandler.npcs[i].defence * 0.30;
				c.sendMessage("You lower the monsters defence by " + NPCHandler.npcs[i].defence * 0.30 + ".");
				if (NPCHandler.npcs[i].defence < 1)
					NPCHandler.npcs[i].defence = 1;
			}
			break;

		case 5:
			if (damage > 0) {
				c.playerLevel[5] += damage;
				if (c.playerLevel[5] < 0)
					c.playerLevel[5] = 0;
				else if (c.playerLevel[5] > c.getPA().getLevelForXP(c.playerXP[5]))
					c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
			}
			break;
		}
		c.specEffect = 0;
		switch (damageMask) {
		case 1:
			NPCHandler.npcs[i].hitDiff = damage;
			NPCHandler.npcs[i].HP -= damage;
			c.totalDamageDealt += damage;
			NPCHandler.npcs[i].hitUpdateRequired = true;
			NPCHandler.npcs[i].updateRequired = true;
			if (Boundary.isIn(c, PestControl.GAME_BOUNDARY)) {
				c.pestControlDamage += damage;
			}
			if (NPCHandler.npcs[i].npcType == 7101) {
				c.bossDamage += damage;
			}
			break;

		case 2:
			NPCHandler.npcs[i].hitDiff2 = damage;
			NPCHandler.npcs[i].HP -= damage;
			c.totalDamageDealt += damage;
			NPCHandler.npcs[i].hitUpdateRequired2 = true;
			NPCHandler.npcs[i].updateRequired = true;
			if (Boundary.isIn(c, PestControl.GAME_BOUNDARY)) {
				c.pestControlDamage += damage;
			}
			if (NPCHandler.npcs[i].npcType == 7101) {
				c.bossDamage += damage;
			}
			c.doubleHit = false;
			break;

		}
	}

	private static int getBonusDefence(Player player, NPC npc, CombatType type) {
		if (type.equals(CombatType.MELEE)) {

		} else if (type.equals(CombatType.MAGE)) {
			switch (npc.npcType) {
			case 2042:
				return -100;
			case 2044:
				return +100;
			}
		} else if (type.equals(CombatType.RANGE)) {
			switch (npc.npcType) {
			case 2042:
				return 100;
			case 2044:
				return -100;
			}
		}
		return 0;
	}

	Damage damage;

	public static void delayedHit(final Player c, final int i) {
		NPC npc = NPCHandler.npcs[i];
		if (npc == null || npc.isDead || npc.HP <= 0) {
			return;
		}
		CombatType style = !c.castingMagic && c.projectileStage > 0 ? CombatType.RANGE
				: c.projectileStage > 0 ? CombatType.MAGE : CombatType.MELEE;
		int defence = npc.defence + getBonusDefence(c, npc, style);
		if (defence < 0) {
			defence = 0;
		}

		if (NPCHandler.npcs[i] != null) {
			if (NPCHandler.npcs[i].isDead) {
				c.npcIndex = 0;
				return;
			}
			boolean rejectsFaceUpdate = false;
			if (npc.npcType >= 2042 && npc.npcType <= 2044 || npc.npcType == 6720) {
				if (c.getZulrahEvent().getNpc() != null && c.getZulrahEvent().getNpc().equals(npc)) {
					if (c.getZulrahEvent().getStage() == 1) {
						rejectsFaceUpdate = true;
					}
				}
				if (c.getZulrahEvent().isTransforming()) {
					return;
				}
			}
			if (!rejectsFaceUpdate) {
				NPCHandler.npcs[i].face(c);
			}

			if (NPCHandler.npcs[i].underAttackBy > 0 && Server.npcHandler.getsPulled(i)) {
				NPCHandler.npcs[i].killerId = c.index;
			} else if (NPCHandler.npcs[i].underAttackBy < 0 && !Server.npcHandler.getsPulled(i)) {
				NPCHandler.npcs[i].killerId = c.index;
			}

			c.lastNpcAttacked = i;

			if (c.projectileStage == 0 && !c.usingMagic) {
				if (!c.usingClaws) {
					c.getCombat().applyNpcMeleeDamage(i, 1, Misc.random(c.getCombat().calculateMeleeMaxHit()), defence);
					if (c.doubleHit) {
						c.getCombat().applyNpcMeleeDamage(i, 2, Misc.random(c.getCombat().calculateMeleeMaxHit()),
								defence);
					}
				} else {
					c.usingClaws = false;
					final int hit1 = Misc.random(c.getCombat().calculateMeleeMaxHit());
					final int hit2 = hit1 / 2;
					final int hit3 = (int) Math.floor((double) hit2 / 2);
					final int hit4 = (int) Math.ceil((double)hit2 / 2);
					final int clawsDamage = hit1 + hit2 + hit3 + hit4;
					if (c.fightMode == 3) {
						c.getPA().addSkillXP((clawsDamage * Config.MELEE_EXP_RATE / 3), 3);
						c.getPA().addSkillXP((clawsDamage * Config.MELEE_EXP_RATE / 3), 0);
						c.getPA().addSkillXP((clawsDamage * Config.MELEE_EXP_RATE / 3), 1);
						c.getPA().addSkillXP((clawsDamage * Config.MELEE_EXP_RATE / 3), 2);
						c.getPA().refreshSkill(0);
						c.getPA().refreshSkill(1);
						c.getPA().refreshSkill(2);
						c.getPA().refreshSkill(3);
					} else {
						c.getPA().addSkillXP((clawsDamage * Config.MELEE_EXP_RATE), 3);
						c.getPA().addSkillXP((clawsDamage * Config.MELEE_EXP_RATE), c.fightMode);
						c.getPA().refreshSkill(c.fightMode);
						c.getPA().refreshSkill(3);
					}
					npc.dealDamage(hit1);
					npc.handleHitMask(hit1);
					npc.dealDamage(hit2);
					npc.handleHitMask(hit2);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

						@Override
						public void execute(CycleEventContainer container) {
							if (container.getOwner() == null) {
								container.stop();
								return;
							}
							npc.dealDamage(hit3);
							npc.handleHitMask(hit3);
							npc.dealDamage(hit4);
							npc.handleHitMask(hit4);
							container.stop();
						}

					}, 1);
				}
			} else if (!c.castingMagic && c.projectileStage > 0) { // range hit
																	// damage
				int damage = Misc.random(c.getCombat().rangeMaxHit());
				int damage2 = -1;

				damage = adjustDamageTaken(NPCHandler.npcs[i], c, damage);

				if (c.playerEquipment[c.playerHat] == 11865 && c.slayerTask == NPCHandler.npcs[i].npcType) {
					// Slayer Helm (i)
					damage *= 1.15;
				}
				if (c.lastWeaponUsed == 11235 || c.lastWeaponUsed == 12765 || c.lastWeaponUsed == 12766
						|| c.lastWeaponUsed == 12767 || c.lastWeaponUsed == 12768 || c.bowSpecShot == 1)
					damage2 = Misc.random(c.getCombat().rangeMaxHit());
				if (damage > 0 && RangeExtras.wearingCrossbow(c)) {
					if (RangeExtras.wearingBolt(c)) {
						if (RangeExtras.boltSpecialAvailable(c)) {
							RangeExtras.crossbowSpecial(c, i);
						}
					}
				}

				if (NPCHandler.npcs[i].npcType == 7151) {
					damage = damage / 4;
				} else if (damage < 0) {
					damage = 0;
				}

				c.newDamage = damage;
				for (int bowId : c.BOWS) {
					if (damage > 0 && Misc.random(8) == 1 && c.lastArrowUsed == 9242
							&& c.playerEquipment[c.playerWeapon] == bowId) {
						if (c.playerLevel[3] - c.playerLevel[3] * 0.10 < 1) {
							return;
						}
						c.playerLevel[3] -= c.playerLevel[3] * 0.10;
						c.sendMessage("<col=255>Your health was drained...");
						damage += Misc.getCurrentHP(NPCHandler.npcs[i].HP, NPCHandler.npcs[i].maximumHealth,
								NPCHandler.npcs[i].HP) * 0.20;
						NPCHandler.npcs[i].gfx0(754);
					}
					if (damage > 0 && Misc.random(5) == 1 && c.lastArrowUsed == 9244
							&& c.playerEquipment[c.playerWeapon] == bowId) {
						if (c.playerEquipment[c.playerWeapon] == 11785)
							damage *= 1.5;
						else
							damage *= 1.45;
						NPCHandler.npcs[i].gfx0(756);
					}
				}
				if (c.lastWeaponUsed == 11235 || c.lastWeaponUsed == 12765 || c.lastWeaponUsed == 12766
						|| c.lastWeaponUsed == 12767 || c.lastWeaponUsed == 12768 || c.bowSpecShot == 1) {
					if (Misc.random(NPCHandler.npcs[i].defence) > Misc
							.random(10 + c.getCombat().calculateRangeAttack()))
						damage2 = 0;
				}
				if (c.dbowSpec) {
					NPCHandler.npcs[i].gfx100(c.lastArrowUsed == 11212 ? 1100 : 1103);
					if (damage < 8)
						damage = 8;
					if (damage2 < 8)
						damage2 = 8;
					c.dbowSpec = false;
				}

				if (NPCHandler.npcs[i].HP - damage < 0) {
					damage = NPCHandler.npcs[i].HP;
				}
				if (damage2 > 0) {
					if (damage == NPCHandler.npcs[i].HP && NPCHandler.npcs[i].HP - damage2 > 0) {
						damage2 = 0;
					}
				}
				if (c.fightMode == 3) {
					c.getPA().addSkillXP((damage * Config.RANGE_EXP_RATE / 3), 4);
					c.getPA().addSkillXP((damage * Config.RANGE_EXP_RATE / 3), 1);
					c.getPA().addSkillXP((damage * Config.RANGE_EXP_RATE / 3), 3);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				} else {
					c.getPA().addSkillXP((damage * Config.RANGE_EXP_RATE), 4);
					c.getPA().addSkillXP((damage * Config.RANGE_EXP_RATE / 3), 3);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				}
				if (damage > 0) {

				}
				boolean dropArrows = true;

				for (int noArrowId : c.NO_ARROW_DROP) {
					if (c.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {
					c.getItems().dropArrowNpc();
					if (c.playerEquipment[3] == 11235 || c.playerEquipment[3] == 12765 || c.playerEquipment[3] == 12766
							|| c.playerEquipment[3] == 12767 || c.playerEquipment[3] == 12768) {
						c.getItems().dropArrowNpc();
					}
				}
				if (npc.defenceAnimation > 0 && npc.attackTimer > 3) {
					npc.doAnimation(npc.defenceAnimation);
				}
				c.rangeEndGFX = RangeData.getRangeEndGFX(c);

				if ((c.playerEquipment[3] == 10034 || c.playerEquipment[3] == 10033)) {
					for (int j = 0; j < NPCHandler.npcs.length; j++) {
						if (NPCHandler.npcs[j] != null && NPCHandler.npcs[j].maximumHealth > 0) {
							int nX = NPCHandler.npcs[j].getX();
							int nY = NPCHandler.npcs[j].getY();
							int pX = NPCHandler.npcs[i].getX();
							int pY = NPCHandler.npcs[i].getY();
							if ((nX - pX == -1 || nX - pX == 0 || nX - pX == 1)
									&& (nY - pY == -1 || nY - pY == 0 || nY - pY == 1)) {
								if (NPCHandler.npcs[i].inMulti()) {
									Player p = PlayerHandler.players[c.index];
									c.getCombat().appendMutliChinchompa(j);
									Server.npcHandler.attackPlayer(p, j);
								}
							}
						}
					}
				}
				if (!c.multiAttacking) {
					NPCHandler.npcs[i].underAttack = true;
					NPCHandler.npcs[i].hitDiff = damage;
					NPCHandler.npcs[i].HP -= damage;
					if (damage2 > -1) {
						NPCHandler.npcs[i].hitDiff2 = damage2;
						NPCHandler.npcs[i].HP -= damage2;
						c.totalDamageDealt += damage2;
					}
					if (Boundary.isIn(c, PestControl.GAME_BOUNDARY)) {
						c.pestControlDamage += damage;
						if (damage2 > 0) {
							c.pestControlDamage += damage;
						}

					}
				}
				if (NPCHandler.npcs[i].npcType == 7101) {
					c.bossDamage += damage;
					if (damage2 > 0) {
						c.bossDamage += damage;
					}
				}
				c.ignoreDefence = false;
				c.multiAttacking = false;

				if (c.rangeEndGFX > 0) {
					if (c.rangeEndGFXHeight) {
						NPCHandler.npcs[i].gfx100(c.rangeEndGFX);
					} else {
						NPCHandler.npcs[i].gfx0(c.rangeEndGFX);
					}
				}
				if (c.killingNpcIndex != c.oldNpcIndex) {
					c.totalDamageDealt = 0;
				}
				c.killingNpcIndex = c.oldNpcIndex;
				c.totalDamageDealt += damage;
				NPCHandler.npcs[i].hitUpdateRequired = true;
				if (damage2 > -1)
					NPCHandler.npcs[i].hitUpdateRequired2 = true;
				NPCHandler.npcs[i].updateRequired = true;

			} else if (c.projectileStage > 0) { // magic hit damage
				if (NPCHandler.npcs[i].HP <= 0) {
					return;
				}
				if (c.spellSwap) {
					c.spellSwap = false;
					c.setSidebarInterface(6, 16640);
					c.playerMagicBook = 2;
					c.gfx0(-1);
				}
				int damage = 0;
				c.usingMagic = true;
				if ((c.hasFullVoidMage() || c.hasFullEliteVoidMage()) && c.playerEquipment[c.playerWeapon] == 8841) {
					damage = Misc.random(c.getCombat().magicMaxHit() + 10);
				} else {
					damage = Misc.random(c.getCombat().magicMaxHit());
				}
				if (NPCHandler.npcs[i].npcType == 7152) {
					damage = c.getCombat().magicMaxHit();
					damage = damage / 4;
				} else if (damage < 0) {
					damage = 0;
				}
				if (c.getCombat().godSpells()) {
					if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
						damage += Misc.random(10);
					}
				}

				damage = adjustDamageTaken(NPCHandler.npcs[i], c, damage);

				boolean magicFailed = false;
				if (Misc.random(defence) > 10 + Misc.random(c.getCombat().mageAtk())) {
					damage = 0;
					magicFailed = true;
				} else if (NPCHandler.npcs[i].npcType == 2881 || NPCHandler.npcs[i].npcType == 2882) {
					damage = 0;
					magicFailed = true;
				}
				if (c.playerEquipment[c.playerHat] == 11865 && c.slayerTask == NPCHandler.npcs[i].npcType) {
					// Slayer Helm (i)
					damage *= 1.15;
				}
				for (int j = 0; j < NPCHandler.npcs.length; j++) {
					if (NPCHandler.npcs[j] != null && NPCHandler.npcs[j].maximumHealth > 0) {
						if (NPCHandler.npcs[i].heightLevel != c.heightLevel) {
							continue;
						}
						int nX = NPCHandler.npcs[j].getX();
						int nY = NPCHandler.npcs[j].getY();
						int pX = NPCHandler.npcs[i].getX();
						int pY = NPCHandler.npcs[i].getY();
						if ((nX - pX == -1 || nX - pX == 0 || nX - pX == 1)
								&& (nY - pY == -1 || nY - pY == 0 || nY - pY == 1)) {
							if (c.getCombat().multis() && NPCHandler.npcs[i].inMulti()
									&& NPCHandler.npcs[i].heightLevel == c.heightLevel) {
								Player p = PlayerHandler.players[c.index];
								c.getCombat().appendMultiBarrageNPC(j, c.magicFailed);
								Server.npcHandler.attackPlayer(p, j);
							}
						}
					}
				}
				if (NPCHandler.npcs[i].HP - damage < 0) {
					damage = NPCHandler.npcs[i].HP;
				}
				if (c.magicDef) {
					c.getPA().addSkillXP((damage * Config.MELEE_EXP_RATE / 2), 1);
					c.getPA().refreshSkill(1);
				}
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage * Config.MAGIC_EXP_RATE), 6);
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage * Config.MAGIC_EXP_RATE / 3), 3);
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(6);
				if (c.getCombat().getEndGfxHeight() == 100 && !magicFailed) { // end
																				// GFX
					NPCHandler.npcs[i].gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
					/*
					 * if (Server.npcHandler.getNPCs()[i].attackTimer > 3) { if
					 * (npc.npcType != 2042 && npc.npcType != 2043 & npc.npcType
					 * != 2044 && npc.npcType != 3127) {
					 * NPCHandler.startAnimation(c.getCombat().npcDefenceAnim(i)
					 * , i); //TODO c.sendMessage("We've made it 2"); } }
					 */
					if (npc.defenceAnimation > 0 && npc.attackTimer > 3) {
						npc.doAnimation(npc.defenceAnimation);
					}
				} else if (!magicFailed) {
					NPCHandler.npcs[i].gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
				}

				if (magicFailed) {
					/*
					 * if (Server.npcHandler.getNPCs()[i].attackTimer > 3) { if
					 * (npc.npcType != 2042 && npc.npcType != 2043 & npc.npcType
					 * != 2044) {
					 * NPCHandler.startAnimation(c.getCombat().npcDefenceAnim(i)
					 * , i); //TODO c.sendMessage("We've made it 3"); } }
					 */
					if (npc.defenceAnimation > 0 && npc.attackTimer > 3) {
						npc.doAnimation(npc.defenceAnimation);
					}
					NPCHandler.npcs[i].gfx100(85);
				}
				if (c.playerEquipment[c.playerWeapon] == 11907) {
					c.setTridentCharge(c.getTridentCharge() - 1);
				} else if (c.playerEquipment[c.playerWeapon] == 12899) {
					c.setToxicTridentCharge(c.getToxicTridentCharge() - 1);
				}
				if (!magicFailed) {
					int freezeDelay = c.getCombat().getFreezeTime();// freeze
					if (freezeDelay > 0 && NPCHandler.npcs[i].freezeTimer == 0) {
						NPCHandler.npcs[i].freezeTimer = freezeDelay;
					}
					switch (c.MAGIC_SPELLS[c.oldSpellId][0]) {
					case 12901:
					case 12919: // blood spells
					case 12911:
					case 12929:
						int heal = Misc.random(damage / 2);
						if (c.playerLevel[3] + heal >= c.getMaximumHealth()) {
							c.playerLevel[3] = c.getMaximumHealth();
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPA().refreshSkill(3);
						break;
					}

				}
				NPCHandler.npcs[i].underAttack = true;
				if (c.getCombat().magicMaxHit() != 0) {
					if (!c.multiAttacking) {
						NPCHandler.npcs[i].hitDiff = damage;
						NPCHandler.npcs[i].HP -= damage;
						NPCHandler.npcs[i].hitUpdateRequired = true;
						c.totalDamageDealt += damage;
					}
					if (Boundary.isIn(c, PestControl.GAME_BOUNDARY)) {
						c.pestControlDamage += damage;
					}
					if (NPCHandler.npcs[i].npcType == 7101) {
						c.bossDamage += damage;
					}
				}
				c.multiAttacking = false;
				c.killingNpcIndex = c.oldNpcIndex;
				NPCHandler.npcs[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				c.oldSpellId = 0;
			}
		}
		c.getCombat().checkVenomousItems();
		Degrade.degrade(c);
		if (c.bowSpecShot <= 0) {
			c.oldNpcIndex = 0;
			c.projectileStage = 0;
			c.doubleHit = false;
			c.lastWeaponUsed = 0;
			c.bowSpecShot = 0;
		}
		if (c.bowSpecShot != 0) {
			c.bowSpecShot = 0;
		}
		/*
		if (c.bowSpecShot >= 2) {
			c.bowSpecShot = 0;
		}
		if (c.bowSpecShot == 1) {
			c.getCombat().fireProjectileNpc();
			c.hitDelay = 2;
			c.bowSpecShot = 0;
		}*/
		/*if (c.usingSpecial) {
			c.usingSpecial = false;
			c.getItems().updateSpecialBar();
		}*/
		c.specAccuracy = 1.0;
		c.specDamage = 1.0;
	}

	public static boolean armaNpc(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 965:
		case 6229:
		case 6230:
		case 6231:
		case 6232:
		case 6233:
		case 6234:
		case 6222:
		case 3162:
		case 3163:
		case 3164:
		case 3165:
		case 6235:
		case 6236:
		case 6237:
		case 6238:
		case 6239:
		case 6240:
		case 6241:
		case 6242:
		case 6243:
		case 6244:
		case 6245:
		case 6246:
			return true;
		}
		return false;
	}

	public static boolean KQ(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 963:
			return true;
		}
		return false;
	}

	/*
	 * public static boolean Kalphite(int i) { switch
	 * (NPCHandler.npcs[i].npcType) { case 963: return true; } return false; }
	 */

	public static void resetSpells(Player c) {
		if (c.playerMagicBook == 0) {
			c.setSidebarInterface(6, 1151); // modern
		}
		if (c.playerMagicBook == 1) {
			c.setSidebarInterface(6, 12855); // ancient
		}
		if (c.playerMagicBook == 2) {
			c.setSidebarInterface(6, 29999); // lunar
		}
	}

	public static final List<Integer> SKIP_DISTANCE_CHECK = Arrays.asList(496, 493, 2042, 2043, 2044);

	public static void attackNpc(Player c, int i) {
		if (NPCHandler.npcs[i] == null) {
			return;
		}
		NPC npc = NPCHandler.npcs[i];
		if (npc.npcType == 5867 || npc.npcType == 5868 || npc.npcType == 5869) {
			return;
		}
		c.lastSent = 0;
		if (Server.task != null) {
			if (Server.task.getNPC() != null && Server.task.getNPC() == npc)
				Server.task.addContributingPlayer(c);
		}
		Task task = Task.forNpc(NPCHandler.npcs[i].npcType);
		if (task != null) {
			if (c.playerLevel[Skill.SLAYER.getId()] < task.getLevelReq() && !c.hungerGames) {
				c.sendMessage("You need at least " + task.getLevelReq() + " slayer to attack this slayer npc.");
				c.getCombat().resetPlayerAttack();
				return;
			}
		}
	
		Hard hard = Hard.forNpc(NPCHandler.npcs[i].npcType);
		if (hard != null) {
			if (c.playerLevel[Skill.SLAYER.getId()] < hard.getLevelReq() && !c.hungerGames) {
				c.sendMessage("You need at least " + hard.getLevelReq() + " slayer to attack this slayer npc.");
				c.getCombat().resetPlayerAttack();
				return;
			}
		}
		
		Medium medium = Medium.forNpc(NPCHandler.npcs[i].npcType);
		if (medium != null) {
			if (c.playerLevel[Skill.SLAYER.getId()] < medium.getLevelReq() && !c.hungerGames) {
				c.sendMessage("You need at least " + medium.getLevelReq() + " slayer to attack this slayer npc.");
				c.getCombat().resetPlayerAttack();
				return;
			}
		}

		if (npc.npcType == 493 || npc.npcType == 496) {
			if (npc.npcType == 496 && c.krakenTent < 4) {
				c.sendMessage("You must disturb all of the tentacles before disturbing the head!");
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.absY <= 5806) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			c.stopMovement();
			SpawnEntity.spawnEntity(c, i);
			NPCHandler.npcs[i].face(c);
		}

		if (npc.npcType == 6767 && c.Jumped) {
			c.getCombat().resetPlayerAttack();
			return;
		}

		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			c.getCombat().resetPlayerAttack();
			return;
		}

		if (npc.npcType >= 2042 && npc.npcType <= 2044 || npc.npcType == 6720) {
			if (c.getZulrahEvent().isTransforming()) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.getZulrahEvent().getStage() == 0 && !c.getRights().isOwner() && !c.getRights().isDeveloper()) {
				c.getCombat().resetPlayerAttack();
				return;
			}
		}
		if (npc.npcType >= 2042 && npc.npcType <= 2044) {
			if (c.usingMelee) {
				c.getCombat().resetPlayerAttack();
				return;
			}
		}

		c.slayerHelmetEffect = c.playerEquipment[c.playerHat] == 11864 && c.slayerTask == i;
		//c.usingMagic = false;
		resetSpells(c);
		if (NPCHandler.npcs[i] != null) {
			c.getCombat().strBonus = c.playerBonus[10];
			if (NPCHandler.npcs[i].isDead || NPCHandler.npcs[i].maximumHealth <= 0) {
				c.usingMagic = false;
				c.face(null);
				c.npcIndex = 0;
				return;
			}
			if (c.teleTimer > 0) {
				return;
			}
			if (c.respawnTimer > 0) {
				c.npcIndex = 0;
				return;
			}
			if (c.playerEquipment[c.playerWeapon] == 4734 && c.playerEquipment[c.playerArrows] > 0
					&& c.playerEquipment[c.playerArrows] != 4740) {
				c.sendMessage("You must use bolt racks with the karil's crossbow.");
				c.npcIndex = 0;
				return;
			}
			if (NPCHandler.npcs[i].npcType == 6611 || NPCHandler.npcs[i].npcType == 6612) {
				List<NPC> minion = Arrays.asList(NPCHandler.npcs);
				if (minion.stream().filter(Objects::nonNull)
						.anyMatch(n -> n.npcType == 5054 && !n.isDead && n.HP > 0)) {
					c.sendMessage("You must kill Vet'ions minions before attacking him.");
					c.npcIndex = 0;
					return;
				}
			}
			if (NPCHandler.npcs[i].underAttackBy > 0 && NPCHandler.npcs[i].underAttackBy != c.index
					&& !NPCHandler.npcs[i].inMulti()) {
				c.npcIndex = 0;
				c.sendMessage("This monster is already in combat.");
				return;
			}
			if ((c.underAttackBy > 0 || c.underAttackBy2 > 0) && c.underAttackBy2 != i && !c.inMulti()) {
				c.getCombat().resetPlayerAttack();
				c.sendMessage("I am already under attack.");
				return;
			}

			if (!c.getCombat().goodSlayer(i)) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (NPCHandler.npcs[i].spawnedBy != c.index && NPCHandler.npcs[i].spawnedBy > 0) {
				c.getCombat().resetPlayerAttack();
				c.sendMessage("This monster was not spawned for you.");
				return;
			}
			if (c.getX() == NPCHandler.npcs[i].getX() && c.getY() == NPCHandler.npcs[i].getY()) {
				c.getPA().walkTo(0, 1);
			}
			if (Boundary.isIn(NPCHandler.npcs[i], Boundary.GODWARS_BOSSROOMS)
					&& !Boundary.isIn(c, Boundary.GODWARS_BOSSROOMS)) {
				c.getCombat().resetPlayerAttack();
				c.sendMessage("You cannot attack that npc from outside the room.");
				return;
			}
			/*
			 * public static int pets[] = {6644, 6643, 6633, 6634, 6641, 6628,
			 * 2055, 6636, 6640, 3504, 5558, 6651, 2128, 2129, 6637, 5560, 6629,
			 * 5557, 2127};
			 */
			int npcType = NPCHandler.npcs[i].npcType;
			if (npcType == 2184 || npcType == 1850 || npcType == 3220 || npcType == 637 || npcType == 732
					|| npcType == 520 || npcType == 3951 || npcType == 3789 || npcType == 559 || npcType == 2913
					|| npcType == 5792 || npcType == 3515 || npcType == 536 || npcType == 1785 || npcType == 1860
					|| npcType == 519 || npcType == 532 || npcType == 535 || npcType == 506 || npcType == 3894
					|| npcType == 3257 || npcType == 3078 || npcType == 315 || npcType == 316 || npcType == 3341
					|| npcType == 5523 || npcType == 1308 || npcType == 4771 || npcType == 527 || npcType == 814
					|| npcType == 4306 || npcType == 2713 || npcType == 2113 || npcType == 2112 || npcType == 2111
					|| npcType == 2110 || npcType == 2108 || npcType == 2040 /**
																				 * Pets
																				 **/
					|| npcType == 6644 || npcType == 6643 || npcType == 490 || npcType == 6633 || npcType == 6634
					|| npcType == 6641 || npcType == 6628 || npcType == 2055 || npcType == 6636 || npcType == 6640
					|| npcType == 3504 || npcType == 5558 || npcType == 6651 || npcType == 2128 || npcType == 2129
					|| npcType == 6637 || npcType == 5560 || npcType == 6629 || npcType == 5557 || npcType == 2127
					|| npcType == 5559) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (npcType == 2463 || npcType == 2464) {
				if (Boundary.isIn(c, WarriorsGuild.CYCLOPS_BOUNDARY)) {
					if (!c.getWarriorsGuild().isActive()) {
						c.sendMessage("You cannot attack a cyclops without talking to kamfreena.");
						c.getCombat().resetPlayerAttack();
						return;
					}
				}
			}
			// where is death handled?
			c.followId2 = i;
			c.followId = 0;
			if (c.attackTimer <= 0) {
				c.usingBow = false;
				c.usingArrows = false;
				c.usingOtherRangeWeapons = false;
				c.check = false;
				c.usingCross = c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 11785
						|| c.playerEquipment[c.playerWeapon] == 8880 || c.playerEquipment[c.playerWeapon] == 19481
						|| c.playerEquipment[c.playerWeapon] == 10156;
				c.bonusAttack = 0;
				c.rangeItemUsed = 0;
				c.projectileStage = 0;
				c.usingRangeWeapon = false;
				if (c.autocasting) {
					c.spellId = c.autocastId;
					c.usingMagic = true;
				}
				if (c.spellId > 0) {
					c.usingMagic = true;
				}
				if (c.playerEquipment[c.playerWeapon] == 11907) {
					if (c.getTridentCharge() <= 0) {
						c.sendMessage("Your trident of the seas has no more charges.");
						c.getCombat().resetPlayerAttack();
						return;
					}
					c.usingMagic = true;
					c.autocasting = true;
					c.castingMagic = true;
					c.spellId = 52;
				}
				if (c.playerEquipment[c.playerWeapon] == 12899) {
					if (c.getToxicTridentCharge() <= 0) {
						c.sendMessage("Your trident of the swamp has no more charges.");
						c.getCombat().resetPlayerAttack();
						return;
					}
					c.usingMagic = true;
					c.autocasting = true;
					c.castingMagic = true;
					c.spellId = 53;
				}
				c.attackTimer = c.getCombat()
						.getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.specAccuracy = 1.0;
				c.specDamage = 1.0;
				if (c.getItems().isWearingItem(12931)) {
					if (c.getSerpentineHelmCharge() <= 0) {
						c.sendMessage("Your serpentine helm has no charge, you need to recharge it.");
						c.getCombat().resetPlayerAttack();
						return;
					}
				}
				if (c.getItems().isWearingItem(12904)) {
					if (c.staffOfDeadCharge <= 0) {
						c.sendMessage("Your toxic staff of the dead has no charge, you need to recharge it.");
						c.getCombat().resetPlayerAttack();
						return;
					}
				}
				if (!c.usingMagic) {
					for (int bowId : c.BOWS) {
						if (c.playerEquipment[c.playerWeapon] == bowId && c.switchDelay.elapsed(100)) {
							c.usingBow = true;
							// c.rangeDelay = 3;
							for (int arrowId : c.ARROWS) {
								if (c.playerEquipment[c.playerArrows] == arrowId) {
									c.usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
						if (c.playerEquipment[c.playerWeapon] == otherRangeId) {
							c.usingOtherRangeWeapons = true;
						}
					}
				}

				if (c.getItems().isWearingItem(12926)) {
					if (c.getToxicBlowpipeAmmo() == 0 || c.getToxicBlowpipeAmmoAmount() == 0
							|| c.getToxicBlowpipeCharge() == 0) {
						c.sendMessage("Your blowpipe is out of ammo or charge.");
						c.getCombat().resetPlayerAttack();
						return;
					}
					c.usingBow = true;
					c.usingArrows = true;
				}
				if (armaNpc(i) && !c.usingCross && !c.usingBow && !c.usingMagic && !c.usingOtherRangeWeapons) {
					c.getCombat().resetPlayerAttack();
					c.sendMessage("You need to range attack this monster!");
					return;
				}

				if (KQ(i) && c.usingCross) {
					c.getCombat().resetPlayerAttack();
					c.sendMessage("You need to melee attack this monster!");
					return;
				}

				if (KQ(i) && c.usingBow) {
					c.getCombat().resetPlayerAttack();
					c.sendMessage("You need to melee attack this monster!");
					return;
				}

				if (KQ(i) && c.usingMagic) {
					c.getCombat().resetPlayerAttack();
					c.sendMessage("You need to melee attack this monster!");
					return;
				}

				if (KQ(i) && c.usingOtherRangeWeapons) {
					c.getCombat().resetPlayerAttack();
					c.sendMessage("You need to melee attack this monster!");
					return;
				}

				double distance = 1;
				if (c.getCombat().usingHally() && !c.usingOtherRangeWeapons && !c.usingBow && !c.usingMagic)
					distance = 2;
				if (c.usingOtherRangeWeapons && !c.usingBow && !c.usingMagic)
					distance = 4;
				if (c.usingBow || c.usingMagic)
					distance = 8;

				NPC theNPC = NPCHandler.npcs[i];

				if (theNPC.getDistance(c.getX(), c.getY()) > distance) {
					c.attackTimer = 1;
					return;
				}

				/*
				 * if ((!c.goodDistance(c.getX(), c.getY(),
				 * NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 2) &&
				 * (c.getCombat().usingHally() && !c.usingOtherRangeWeapons &&
				 * !c.usingBow && !c.usingMagic)) || (!c.goodDistance(c.getX(),
				 * c.getY(), NPCHandler.npcs[i].getX(),
				 * NPCHandler.npcs[i].getY(), 4) && (c.usingOtherRangeWeapons &&
				 * !c.usingBow && !c.usingMagic)) || (!c.goodDistance(c.getX(),
				 * c.getY(), NPCHandler.npcs[i].getX(),
				 * NPCHandler.npcs[i].getY(), 1) && (!c.usingOtherRangeWeapons
				 * && !c.getCombat().usingHally() && !c.usingBow &&
				 * !c.usingMagic)) || ((!c.goodDistance(c.getX(), c.getY(),
				 * NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 8) &&
				 * (c.usingBow || c.usingMagic)))) { c.attackTimer = 2; return;
				 * }
				 */

				if (c.usingOtherRangeWeapons || c.usingBow || c.usingMagic) {
					if (!NPCHandler.allowUnclippedDamage(i) && (!PathChecker.isProjectilePathClear(c.getX(), c.getY(),
							c.heightLevel, theNPC.getX(), theNPC.getY())
							|| !PathChecker.isProjectilePathClear(theNPC.getX(), theNPC.getY(), c.heightLevel, c.getX(),
									c.getY()))) {
						c.attackTimer = 1;
						return;
					}
				}

				if (!c.usingCross && !c.usingArrows && c.usingBow
						&& (c.playerEquipment[c.playerWeapon] < 4212 || c.playerEquipment[c.playerWeapon] > 4223)) {
					c.sendMessage("You have run out of arrows!");
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}
				if (c.getCombat().correctBowAndArrows() < c.playerEquipment[c.playerArrows] && Config.CORRECT_ARROWS
						&& c.usingBow && !c.getCombat().usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 9185
						&& c.playerEquipment[c.playerWeapon] != 19481 && c.playerEquipment[c.playerWeapon] != 8880
						&& c.playerEquipment[c.playerWeapon] != 11785 && !c.getItems().isWearingItem(12926)
						&& c.playerEquipment[c.playerWeapon] != 10156) {
					c.sendMessage("You can't use "
							+ c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase() + "s with a "
							+ c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + ".");
					c.stopMovement();
					c.npcIndex = 0;
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
				if (c.usingBow || c.castingMagic || c.usingOtherRangeWeapons || (c.getCombat().usingHally() && c
						.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 2))) {
					c.stopMovement();
				}
				if (!c.getCombat().checkMagicReqs(c.spellId)) {
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}
				c.face(NPCHandler.npcs[i]);
				NPCHandler.npcs[i].underAttackBy = c.index;
				NPCHandler.npcs[i].lastDamageTaken = System.currentTimeMillis();
				if (c.usingSpecial && !c.usingMagic) {
					if (c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
						c.lastArrowUsed = c.playerEquipment[c.playerArrows];
						c.getCombat().activateSpecial(c.playerEquipment[c.playerWeapon], i);
						c.followId = c.npcIndex;
						c.getItems().updateSpecialBar();
					} else {
						c.sendMessage("You don't have the required special energy to use this attack.");
						c.usingSpecial = false;
						c.getItems().updateSpecialBar();
						c.playerIndex = 0;
						c.getCombat().resetPlayerAttack();
						return;
					}
					if (c.usingSpecial) {
						c.usingSpecial = false;
						c.getItems().updateSpecialBar();
						return;
					}
				}

				c.mageFollow = c.usingBow || c.usingMagic || c.usingOtherRangeWeapons;

				c.specMaxHitIncrease = 0;
				if (c.playerLevel[3] > 0 && !c.isDead && NPCHandler.npcs[i].maximumHealth > 0) {
					if (!c.usingMagic) {
						c.animation(c.getCombat()
								.getWepAnim(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()));
						/*
						 * if (Server.npcHandler.getNPCs()[i].attackTimer > 3) {
						 * if (npcType != 2042 && npcType != 2043 & npcType !=
						 * 2044 && npcType != 3127) {
						 * NPCHandler.startAnimation(c.getCombat().
						 * npcDefenceAnim(i), i); //TODO
						 * c.sendMessage("We've made it 4"); } }
						 */
						if (npc.defenceAnimation > 0 && npc.attackTimer > 3) {
							npc.doAnimation(npc.defenceAnimation);
						}
					} else {
						c.animation(c.MAGIC_SPELLS[c.spellId][2]);
					}
				}
				c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
				c.lastArrowUsed = c.playerEquipment[c.playerArrows];

				if (!c.usingBow && !c.usingMagic && !c.usingOtherRangeWeapons) { // melee
																					// hit
																					// delay
					c.followId2 = NPCHandler.npcs[i].index;
					c.getPA().followNpc();
					c.hitDelay = c.getCombat().getHitDelay(i,
							c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 0;
					c.oldNpcIndex = i;
				}

				if (c.usingOtherRangeWeapons || c.usingBow) {
					if (c.fightMode == 2) {
						c.attackTimer--;
					}
				}

				if (c.usingBow && !c.usingOtherRangeWeapons && !c.usingMagic || c.usingCross) { // range
																								// hit
																								// delay
																								// //
																								// delay
					if (c.usingCross)
						c.usingBow = true;
					if (c.fightMode == 2)
						c.followId2 = NPCHandler.npcs[i].index;
					c.getPA().followNpc();
					c.lastArrowUsed = c.playerEquipment[c.playerArrows];
					c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
					c.gfx100(c.getCombat().getRangeStartGFX());
					c.usingKarils++;
					c.hitDelay = c.getCombat().getHitDelay(i,
							c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldNpcIndex = i;
					if (c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223) {
						c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
						c.crystalBowArrowCount++;
						c.lastArrowUsed = 0;
						c.getCombat().fireProjectileNpc();
					} else if (c.playerEquipment[c.playerWeapon] == 12926) {
						c.getCombat().fireProjectileNpc();
					} else {
						c.rangeItemUsed = c.playerEquipment[c.playerArrows];
						c.getItems().deleteArrow();
						if (c.playerEquipment[3] == 11235 || c.playerEquipment[3] == 12765
								|| c.playerEquipment[3] == 12766 || c.playerEquipment[3] == 12767
								|| c.playerEquipment[3] == 12768) {
							c.getItems().deleteArrow();
						}
						c.getCombat().fireProjectileNpc();
					}
				}

				/*
				 * Here
				 */

				if (c.usingOtherRangeWeapons && !c.usingMagic && !c.usingBow) {
					c.usingRangeWeapon = true;
					c.followId2 = NPCHandler.npcs[i].index;
					c.getPA().followNpc();
					c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
					c.getItems().deleteEquipment();
					c.gfx100(c.getCombat().getRangeStartGFX());
					c.lastArrowUsed = 0;
					c.hitDelay = c.getCombat().getHitDelay(i,
							c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldNpcIndex = i;
					c.getCombat().fireProjectileNpc();
				}

				if (c.newDamage > 0 && c.getPA().wearingKaril(c) && Misc.random(8) == 1) {
					NPCHandler.npcs[i].dealDamage(c.newDamage / 2);
					NPCHandler.npcs[i].handleHitMask(c.newDamage / 2);
					c.sendMessage(
							"@blu@You feel your amulet surge through you and unleash an extra bolt at your target!");
					c.usingKarils = 0;
				}

				if (c.usingMagic) { // magic hit delay
					int pX = c.getX();
					int pY = c.getY();
					int nX = NPCHandler.npcs[i].getX();
					int nY = NPCHandler.npcs[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					c.castingMagic = true;
					c.projectileStage = 2;
					c.stopMovement();
					if (c.MAGIC_SPELLS[c.spellId][3] > 0) {
						if (c.getCombat().getStartGfxHeight() == 100) {
							c.gfx100(c.MAGIC_SPELLS[c.spellId][3]);
						} else {
							c.gfx0(c.MAGIC_SPELLS[c.spellId][3]);
						}
					}
					if (c.MAGIC_SPELLS[c.spellId][4] > 0) {
						c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 78, c.MAGIC_SPELLS[c.spellId][4],
								c.getCombat().getStartHeight(), c.getCombat().getEndHeight(), i + 1, 50);
					}
					c.hitDelay = c.getCombat().getHitDelay(i,
							c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.oldNpcIndex = i;
					c.oldSpellId = c.spellId;
					c.spellId = 0;
					if (!c.autocasting)
						c.npcIndex = 0;
				}

				if (c.usingBow && Config.CRYSTAL_BOW_DEGRADES) { // crystal bow
																	// degrading
					if (c.playerEquipment[c.playerWeapon] == 4212) { // new
																		// crystal
																		// bow
																		// becomes
																		// full
																		// bow
																		// on
																		// the
																		// first
																		// shot
						c.getItems().wearItem(4214, 1, 3);
					}

					if (c.crystalBowArrowCount >= 250) {
						switch (c.playerEquipment[c.playerWeapon]) {

						case 4223: // 1/10 bow
							c.getItems().wearItem(-1, 1, 3);
							c.sendMessage("Your crystal bow has fully degraded.");
							if (!c.getItems().addItem(4207, 1)) {
								Server.itemHandler.createGroundItem(c, 4207, c.getX(), c.getY(), c.heightLevel, 1,
										c.getId());
							}
							c.crystalBowArrowCount = 0;
							break;

						default:
							c.getItems().wearItem(++c.playerEquipment[c.playerWeapon], 1, 3);
							c.sendMessage("Your crystal bow degrades.");
							c.crystalBowArrowCount = 0;
							break;
						}
					}
				}
			}
		}
	}
}