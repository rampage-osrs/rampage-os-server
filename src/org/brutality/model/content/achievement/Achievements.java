package org.brutality.model.content.achievement;

import java.util.EnumSet;
import java.util.Set;

import org.brutality.Server;
import org.brutality.model.players.Player;
import org.brutality.model.players.PlayerHandler;
import org.brutality.util.Misc;
/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @date Mar 26, 2014
 */
public class Achievements {
	
	public enum Achievement {
		/**
		 * Tier 1 Achievement Start
		 */
		FIRST_TASTE(0, AchievementTier.TIER_1, AchievementType.KILL_PLAYER, null, "Kill 10 Players", 10, 1),
        HUNGRY_FOR_MORE(1, AchievementTier.TIER_1, AchievementType.KILL_PLAYER, null, "Kill 25 Players", 25, 1),
        BLOOD_LUST(2, AchievementTier.TIER_1, AchievementType.KILL_PLAYER, null, "Kill 50 Players", 50, 1),
        A_WARRIOR(3, AchievementTier.TIER_1, AchievementType.KILL_PLAYER, null, "Kill 100 Players", 100, 1),
        A_KILLER(4, AchievementTier.TIER_1, AchievementType.KILL_PLAYER, null, "Kill 150 Players", 150, 1),
        BLOOD_BOILING(5, AchievementTier.TIER_1, AchievementType.KILL_PLAYER, null, "Kill 200 Players", 200, 1),
        BOSS_KILLER(6, AchievementTier.TIER_1, AchievementType.KILL_BOSS, null, "Kill 50 Bosses", 50, 1),
        BOSS_DESTROYER(7, AchievementTier.TIER_1, AchievementType.KILL_BOSS, null, "Kill 100 Bosses", 100, 1),
        JELLY_LEGS(8, AchievementTier.TIER_1, AchievementType.AGILITY, null, "Complete 5 laps", 5, 1),
        THE_SPARK(9, AchievementTier.TIER_1, AchievementType.FIREMAKING, null, "Light 25 logs", 25, 1),
        FISHAMAN(10, AchievementTier.TIER_1, AchievementType.FISHING, null, "Catch 25 fish", 25, 1),
        THE_CURE(11, AchievementTier.TIER_1, AchievementType.HERBLORE, null, "Make 25 potions", 25, 1),
        WOOD_CHOPPER(12, AchievementTier.TIER_1, AchievementType.WOODCUTTING, null, "Cut 25 logs", 25, 1),
        ORE_GATHERER(13, AchievementTier.TIER_1, AchievementType.MINING, null, "Mine 25 ores", 25, 1),
        MONSTER_SLAYER(14, AchievementTier.TIER_1, AchievementType.SLAYER, null, "Complete 5 Slayer Tasks", 5, 1),
        LAVA_ADDICT(15, AchievementTier.TIER_1, AchievementType.TZ_TOK_JAD, null, "Complete the fight cave minigame two times", 2, 1),
        GHOST_BUSTER(16, AchievementTier.TIER_1, AchievementType.BARROWS, null, "Complete the barrows minigame 10 times", 10, 1),
/**
* Tier 2 Achievement Start
*/
A_MONSTER(0, AchievementTier.TIER_2, AchievementType.KILL_PLAYER, null, "Kill 250 Players", 250, 2),
        BLOOD_LOVER(1, AchievementTier.TIER_2, AchievementType.KILL_PLAYER, null, "Kill 375 Players", 375, 2),
        RISING_LEGEND(2, AchievementTier.TIER_2, AchievementType.KILL_PLAYER, null, "Kill 500 Players", 500, 2),
        GLADIATOR(3, AchievementTier.TIER_2, AchievementType.KILL_PLAYER, null, "Kill 750 Players", 750, 2),
        THE_CONQUEROR(4, AchievementTier.TIER_2, AchievementType.KILL_BOSS, null, "Kill 250 Bosses", 250, 2),
        BOSS_ADDICT(5, AchievementTier.TIER_2, AchievementType.KILL_BOSS, null, "Kill 500 Bosses", 500, 2),
        THE_RUNNER(6, AchievementTier.TIER_2, AchievementType.AGILITY, null, "Complete 50 laps", 50, 2),
        BURNING_BRIGHT(7, AchievementTier.TIER_2, AchievementType.FIREMAKING, null, "Light 250 logs", 250, 2),
        THA_FISHAMAN(8, AchievementTier.TIER_2, AchievementType.FISHING, null, "Catch 250 fish", 250, 2),
        THE_VACCINE(9, AchievementTier.TIER_2, AchievementType.HERBLORE, null, "Make 250 potions", 250, 2),
        SELECTIVE_CUTTING(10, AchievementTier.TIER_2, AchievementType.WOODCUTTING, null, "Cut 250 logs", 250, 2),
        ORE_COLLECTOR(11, AchievementTier.TIER_2, AchievementType.MINING, null, "Mine 250 ores", 250, 2),
        MONSTER_FIGHTER(12, AchievementTier.TIER_2, AchievementType.SLAYER, null, "Complete 25 Slayer Tasks", 25, 2),
        LAVA_BROTHER(13, AchievementTier.TIER_2, AchievementType.TZ_TOK_JAD, null, "Complete the fight cave minigame five times", 5, 2),
        GRAVEDIGGER(14, AchievementTier.TIER_2, AchievementType.BARROWS, null, "Complete the barrows minigame 100 times", 100, 2),
        
/**
* Tier 3 Achievement Start
*/
        BLOOD_CRAZED(0, AchievementTier.TIER_3, AchievementType.KILL_PLAYER, null, "Kill 1000 Players", 1000, 3),
        PLAYER_KILLER(1, AchievementTier.TIER_3, AchievementType.KILL_PLAYER, null, "Kill 1250 Players", 1250, 3),
        LEGENDARY_PKER(2, AchievementTier.TIER_3, AchievementType.KILL_PLAYER, null, "Kill 1500 Players", 1500, 3),
        TACTIC_MASTER(3, AchievementTier.TIER_3, AchievementType.KILL_BOSS, null, "Kill 1000 Bosses", 1000, 3),
        BOSS_EXPERT(4, AchievementTier.TIER_3, AchievementType.KILL_BOSS, null, "Kill 2000 Bosses", 2000, 3),
        FORREST_GUMP(5, AchievementTier.TIER_3, AchievementType.AGILITY, null, "Complete 250 laps", 250, 3),
        FAHRENHEIT_451(6, AchievementTier.TIER_3, AchievementType.FIREMAKING, null, "Light 1000 logs", 1000, 3),
        MASTA_FISHAMAN(7, AchievementTier.TIER_3, AchievementType.FISHING, null, "Catch 1000 fish", 1000, 3),
        THE_PLAGUE_DOCTOR(8, AchievementTier.TIER_3, AchievementType.HERBLORE, null, "Make 1000 potions", 1000, 3),
        LUMBERJACK(9, AchievementTier.TIER_3, AchievementType.WOODCUTTING, null, "Cut 1000 logs", 1000, 3),
        THE_MINER(10, AchievementTier.TIER_3, AchievementType.MINING, null, "Mine 1000 ores", 1000, 3),
        MONSTER_HUNTER(11, AchievementTier.TIER_3, AchievementType.SLAYER, null, "Complete 100 Slayer Tasks", 100, 3),
        VOLCANIC(12, AchievementTier.TIER_3, AchievementType.TZ_TOK_JAD, null, "Complete the fight cave minigame ten times", 10, 3),
        NECROPHILIA(13, AchievementTier.TIER_3, AchievementType.BARROWS, null, "Complete the barrows minigame 250 times", 250, 3);

		private AchievementTier tier;
		private AchievementRequirement requirement;
		private AchievementType type;
		private String description;
		private int amount, identification, points;
		
		Achievement(int identification, AchievementTier tier, AchievementType type, AchievementRequirement requirement, String description, int amount, int points) {
			this.identification = identification;
			this.tier = tier;
			this.type = type;
			this.requirement = requirement;
			this.description = description;
			this.amount = amount;
			this.points = points;
		}
		
		public int getId() {
			return identification;
		}
		
		public AchievementTier getTier() {
			return tier;
		}
		
		public AchievementType getType() {
			return type;
		}
		
		public AchievementRequirement getRequirement() {
			return requirement;
		}
		
		public String getDescription() {
			return description;
		}
		
		public int getAmount() {
			return amount;
		}
		
		public int getPoints() {
			return points;
		}
		
		public static final Set<Achievement> ACHIEVEMENTS = EnumSet.allOf(Achievement.class);
		
		public static Achievement getAchievement(AchievementTier tier, int ordinal) {
			for(Achievement achievement : ACHIEVEMENTS)
				if(achievement.getTier() == tier && achievement.ordinal() == ordinal)
					return achievement;
			return null;
		}
		
		public static boolean hasRequirement(Player player, AchievementTier tier, int ordinal) {
			for(Achievement achievement : ACHIEVEMENTS) {
				if(achievement.getTier() == tier && achievement.ordinal() == ordinal) {
					if(achievement.getRequirement() == null)
						return true;
					if(achievement.getRequirement().isAble(player))
						return true;
				}
			}
			return false;
		}
	}
	
	public static void increase(Player player, AchievementType type, int amount) {
		for(Achievement achievement : Achievement.ACHIEVEMENTS) {
			if(achievement.getType() == type) {
				if(achievement.getRequirement() == null || achievement.getRequirement().isAble(player)) {
					int currentAmount = player.getAchievements().getAmountRemaining(achievement.getTier().ordinal(), achievement.getId());
					int tier = achievement.getTier().ordinal();
					if(currentAmount < achievement.getAmount() && !player.getAchievements().isComplete(achievement.getTier().ordinal(), achievement.getId())) {
						player.getAchievements().setAmountRemaining(tier, achievement.getId(), currentAmount + amount);
						if((currentAmount + amount) >= achievement.getAmount()) {
							String name = achievement.name().replaceAll("_", " ");
							player.getAchievements().setComplete(tier, achievement.getId(), true);
							player.getAchievements().setPoints(achievement.getPoints() + player.getAchievements().getPoints());
							player.sendMessage("Achievement completed on tier "+(tier + 1)+": '"+achievement.name().toLowerCase().replaceAll("_", " ")+"' and receive "+achievement.getPoints()+" point(s).", 255);
							if (tier == 0){
								player.getPA().rewardPoints(1, "You also recieve 1 PK Points for completeing a tier 1 achievement!");
							}
							if (tier == 1){
								player.getPA().rewardPoints(3, "You also recieve 3 Pk Points for completeing a tier 2 achievement!");
							}
							if (tier == 2){
								player.getPA().rewardPoints(5, "You also recieve 5 Pk Points for completeing a tier 3 achievement!");
							}
							if(achievement.getTier().ordinal() > 0) {
								for(Player p : PlayerHandler.players) {
									if(p == null)
										continue;
									Player c = p;
									c.sendMessage("<col=ff3300>[ACHIEVEMENT]</col><col=255> "+Misc.ucFirst(player.playerName)+" @bla@completed the achievement <col=255>"+name+" </col><col=000000>on tier</col><col=255>"+(tier + 1)+".");
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void reset(Player player, AchievementType type) {
		for(Achievement achievement : Achievement.ACHIEVEMENTS) {
			if(achievement.getType() == type) {
				if(achievement.getRequirement() == null || achievement.getRequirement().isAble(player)) {
					if(!player.getAchievements().isComplete(achievement.getTier().ordinal(), achievement.getId())) {
						player.getAchievements().setAmountRemaining(achievement.getTier().ordinal(), achievement.getId(),
								0);
					}
				}
			}
		}
	}
	
	public static void complete(Player player, AchievementType type) {
		for(Achievement achievement : Achievement.ACHIEVEMENTS) {
			if(achievement.getType() == type) {
				if(achievement.getRequirement() != null && achievement.getRequirement().isAble(player)
						&& !player.getAchievements().isComplete(achievement.getTier().ordinal(), achievement.getId())) {
					int tier = achievement.getTier().ordinal();
					//String name = achievement.name().replaceAll("_", " ");
					player.getAchievements().setAmountRemaining(tier, achievement.getId(), achievement.getAmount());
					player.getAchievements().setComplete(tier, achievement.getId(), true);
					player.getAchievements().setPoints(achievement.getPoints() + player.getAchievements().getPoints());
					player.sendMessage("Achievement completed on tier "+(tier + 1)+": '"+achievement.name().toLowerCase().replaceAll("_", " ")+"' and receive "+achievement.getPoints()+" point(s).", 255);
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static void checkIfFinished(Player player) {
		//complete(player, AchievementType.LEARNING_THE_ROPES);
	}
	
	public static int getMaximumAchievements() {
		return Achievement.ACHIEVEMENTS.size();
	}
}
