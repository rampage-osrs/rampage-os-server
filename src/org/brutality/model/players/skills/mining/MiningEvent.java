package org.brutality.model.players.skills.mining;

import java.util.Random;

import org.brutality.Config;
import org.brutality.Server;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.model.content.achievement.AchievementType;
import org.brutality.model.content.achievement.Achievements;
import org.brutality.model.npcs.NPC;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.model.players.skills.Firemaking;
import org.brutality.model.players.skills.Skill;
import org.brutality.model.players.skills.Smelting;
import org.brutality.util.Location3D;
import org.brutality.util.Misc;
import org.brutality.world.objects.GlobalObject;

/**
 * Represents a singular event that is executed when a player attempts to mine. 
 * 
 * @author Jason MacKeigan
 * @date Feb 18, 2015, 6:17:11 PM
 */
public class MiningEvent extends CycleEvent {
	
	public static final Random random25 = new Random();
	
	/**
	 * The amount of cycles that must pass before the animation is updated
	 */
	private final int ANIMATION_CYCLE_DELAY = 15;
	
	/**
	 * The value in cycles of the last animation
	 */
	private int lastAnimation;
	
	/**
	 * The player attempting to mine
	 */
	private final Player player;
	
	/**
	 * The pickaxe being used to mine
	 */
	private final Pickaxe pickaxe;
	
	/**
	 * The mineral being mined
	 */
	private final Mineral mineral;
	
	/**
	 * The object that we are mning
	 */
	private int objectId;
	
	/**
	 * The location of the object we're mining
	 */
	private Location3D location;
	
	/**
	 * The npc the player is mining, if any
	 */
	private NPC npc;
	
	/**
	 * Constructs a new {@link MiningEvent} for a single player
	 * @param player	the player this is created for
	 * @param objectId	the id value of the object being mined from
	 * @param location	the location of the object being mined from
	 * @param mineral	the mineral being mined
	 * @param pickaxe	the pickaxe being used to mine
	 */
	public MiningEvent(Player player, int objectId, Location3D location, Mineral mineral, Pickaxe pickaxe) {
		this.player = player;
		this.objectId = objectId;
		this.location = location;
		this.mineral = mineral;
		this.pickaxe = pickaxe;
	}
	
	/**
	 * Constructs a new {@link MiningEvent} for a single player
	 * @param player	the player this is created for
	 * @param npc		the npc being from from
	 * @param location	the location of the npc
	 * @param mineral	the mineral being mined
	 * @param pickaxe	the pickaxe being used to mine
	 */
	public MiningEvent(Player player, NPC npc, Location3D location, Mineral mineral, Pickaxe pickaxe) {
		this.player = player;
		this.npc = npc;
		this.location = location;
		this.mineral = mineral;
		this.pickaxe = pickaxe;
	}
	
	@Override 
	public void update(CycleEventContainer container) {
		if(player == null || player.disconnected || player.teleporting || player.isDead) {
			container.stop();
			return;
		}
		if (!player.getItems().playerHasItem(pickaxe.getItemId())
				&& !player.getItems().isWearingItem(pickaxe.getItemId())) {
			player.sendMessage("That is strange! The pickaxe could not be found.");
			container.stop();
			return;
		}
		if (player.getItems().freeSlots() == 0) {
			player.getDH().sendStatement("You have no more free slots.");
			container.stop();
			return;
		}
		if (Misc.random(100) == 0 && player.getInterfaceEvent().isExecutable()) {
			player.getInterfaceEvent().execute();
			container.stop();
			return;
		}
		if (objectId > 0) {
			if (Server.getGlobalObjects().exists(Mineral.EMPTY_VEIN, location.getX(), location.getY(), location.getZ())) {
				player.sendMessage("This vein contains no more minerals.");
				container.stop();
				return;
			}
		} else {
			if (npc == null || npc.isDead) {
				player.sendMessage("This vein contains no more minerals.");
				container.stop();
				return;
			}
		}
		if (container.getTotalTicks() - lastAnimation > ANIMATION_CYCLE_DELAY) {
			player.animation(pickaxe.getAnimation());
			lastAnimation = container.getTotalTicks();
		}
	}

	@Override
	public void execute(CycleEventContainer container) {
		if(player == null || player.disconnected || player.teleporting || player.isDead) {
			container.stop();
			return;
		}
		if (Misc.random(mineral.getDepletionProbability()) == 0 || mineral.getDepletionProbability() == 0) {
			if (objectId > 0) {
				Server.getGlobalObjects().add(new GlobalObject(Mineral.EMPTY_VEIN, location.getX(),
						location.getY(), location.getZ(), 0, 10, mineral.getRespawnRate(), objectId));
			} else {
				npc.isDead = true;
				npc.actionTimer = 0;
				npc.needRespawn = false;
			}
		}
		int chance20 = Misc.random(3);
		player.face(location.getX(), location.getY());
		player.getItems().addItem(mineral.getMineral(), 1);
		if (chance20 == 1 && player.smeltOre < 1 && player.getItems().playerHasItem(13243) || player.playerEquipment[player.playerWeapon] == 13243) {
			player.getPA().addSkillXP(2000, Player.playerSmithing);
			player.getItems().deleteItem(mineral.getMineral(), 1);
			player.gfx0(1180);
			if (Misc.random(500) == 0) {
				player.getPA().rewardPoints(1, "Congrats, You randomly got 1 PK Points from smithing!");
			}
			player.smeltOre++;
		}
		int chance = Misc.random(300);
		//player.sendMessage("Your chance to get 100 platinum tokens from skilling was " + chance + " you needed 0.");
		if (chance == 0) {
			player.getPA().rewardPoints(2, "Congrats, You randomly got 2 PK Points from mining!");
		}
		if (mineral.equals(Mineral.COPPER) || mineral.equals(Mineral.TIN)) {
			int random = Misc.random(6000);
			if (random == 6000) {
			player.sendMessage("@red@You receive a skilling pet. It has been added to your bank. Congratulations!");
			player.getItems().addItemToBank(13321, 1);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Player c2 = PlayerHandler.players[j];
						c2.sendMessage("<col=006600>" + player.playerName + " received a skilling pet: 1 x Rock Golem.");
					}
				}
			}
		} else if (mineral.equals(Mineral.IRON)) {
			int random = Misc.random(5500);
			if (random == 5500) {
			player.sendMessage("@red@You receive a skilling pet. It has been added to your bank. Congratulations!");
			player.getItems().addItemToBank(13321, 1);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Player c2 = PlayerHandler.players[j];
						c2.sendMessage("<col=006600>" + player.playerName + " received a skilling pet: 1 x Rock Golem.");
					}
				}
			}
		} else if (mineral.equals(Mineral.COPPER)) {
			int random = Misc.random(5000);
			if (random == 5000) {
			player.sendMessage("@red@You receive a skilling pet. It has been added to your bank. Congratulations!");
			player.getItems().addItemToBank(13321, 1);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Player c2 = PlayerHandler.players[j];
						c2.sendMessage("<col=006600>" + player.playerName + " received a skilling pet.1 x Rock Golem.");
					}
				}
			}
		} else if (mineral.equals(Mineral.COAL) || mineral.equals(Mineral.GOLD)) {
			int random = Misc.random(4500);
			if (random == 4500) {
			player.sendMessage("@red@You receive a skilling pet. It has been added to your bank. Congratulations!");
			player.getItems().addItemToBank(13321, 1);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Player c2 = PlayerHandler.players[j];
						c2.sendMessage("<col=006600>" + player.playerName + " received a skilling pet: 1 x Rock Golem.");
					}
				}
			}
		} else if (mineral.equals(Mineral.MITHRIL)) {
			int random = Misc.random(4000);
			if (random == 4000) {
			player.sendMessage("@red@You receive a skilling pet. It has been added to your bank. Congratulations!");
			player.getItems().addItemToBank(13321, 1);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Player c2 = PlayerHandler.players[j];
						c2.sendMessage("<col=006600>" + player.playerName + " received a skilling pet: 1 x Rock Golem.");
					}
				}
			}
		} else if (mineral.equals(Mineral.ADAMANTITE)) {
			int random = Misc.random(3500);
			if (random == 3500) {
			player.sendMessage("@red@You receive a skilling pet. It has been added to your bank. Congratulations!");
			player.getItems().addItemToBank(13321, 1);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Player c2 = PlayerHandler.players[j];
						c2.sendMessage("<col=006600>" + player.playerName + " received a skilling pet: 1 x Rock Golem.");
					}
				}
			}
		} else if (mineral.equals(Mineral.RUNITE)) {
			int random = Misc.random(3000);
			if (random == 3000) {
			player.sendMessage("@red@You receive a skilling pet. It has been added to your bank. Congratulations!");
			player.getItems().addItemToBank(13321, 1);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Player c2 = PlayerHandler.players[j];
						c2.sendMessage("<col=006600>" + player.playerName + " received a skilling pet: 1 x Rock Golem.");
					}
				}
			}
		}
		if (Misc.random(30) == 0) {
			player.getPA().rewardPoints(3, "Congrats, You randomly got 3 PK Points from mining!");
		}
		Achievements.increase(player, AchievementType.MINING, 1);
		if (player.playerEquipment[player.playerHat] == 12013 && player.playerEquipment[player.playerChest] == 12014 && player.playerEquipment[player.playerLegs] == 12015 && player.playerEquipment[player.playerFeet] == 12016) {
			player.getPA().addSkillXP(Config.MINING_EXPERIENCE * mineral.getExperience() * 1.2, Skill.MINING.getId());
		} else {
			player.getPA().addSkillXP(Config.MINING_EXPERIENCE * mineral.getExperience(), Skill.MINING.getId());
		}
		player.smeltOre = 0;
	}
	
	@Override
	public void stop() {
		if (player == null) {
			return;
		}
		player.stopAnimation();
	}
}
