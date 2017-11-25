package org.brutality.model.content.dialogue.teleport;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;
import org.brutality.model.content.dialogue.OptionDialogue;
import org.brutality.model.content.teleport.Position;
import org.brutality.model.content.teleport.TeleportExecutor;
import org.brutality.model.npcs.boss.Kraken.Kraken;
import org.brutality.model.npcs.boss.Kalphite.Kalphite;
import org.brutality.model.players.Player;

public enum Teleports {

	PVP(/**/
			new int[] { 4140, 50235, 117112 }, /**/
			new Teleport("Ardougne Lever", 2561, 3311), /**/
			new Teleport("East Dragons (Wild)", 3347, 3672, true), /**/
			new Teleport("Edgeville", 3087, 3514), /**/
			new Teleport("Lava Dragons (Wild)", 3202, 3859, true), /**/
			new Teleport("Mage Arena (Wild)", 3106, 3960, true), /**/
			new Teleport("Mage Bank", 2540, 4715), /**/
			new Teleport("West Dragons (Wild)", 2979, 3597, true), /**/
			new Teleport("44 Portals (Wild)", 2980, 3871, true) /**/
	), /**/
	TRAINING( /**/
			new int[] { 4143, 50245, 117123 }, /**/
			new Teleport("Brimhaven Dungeon", 2680, 9563), /**/
			new Teleport("Edgeville Dungeon", 3117, 9856), /**/
			new Teleport("Elven Camp", 2171, 3125), /**/
			new Teleport("Earth Warriors", 3120, 9975), /**/
			new Teleport("Mithril Dragons", 1749, 5330), /**/
			new Teleport("Neitiznot", 2317, 3823), /**/
			new Teleport("Relekka Dungeon", 2791, 10018), /**/
			new Teleport("Rock Crabs", 2672, 3712), /**/
			new Teleport("Slayer Tower", 3429, 3538), /**/
			new Teleport("Taverley Dungeon", 2884, 9801) /**/
	), /**/
	BOSS( /**/
			new int[] { 4146, 50253, 117131 }, /**/
			new Teleport("Barrelchest", 3808, 2844), /**/
			new Teleport("Cerberus", 2872, 9847), /**/
			new Teleport("Callisto (Wild)", 3318, 3834, true), /**/
			new Teleport("Venenatis (Wild & Multi)", 3352, 3730, true), /**/
			new Teleport("Vet'ion (Wild & Multi)", 3179, 3774, true), /**/
			new Teleport("Scorpia (Wild)", 3233, 10335, true),
			new Teleport("Crazy Archaeologist (Wild)", 2980, 3705, true), /**/
			new Teleport("Chaos Fanatic (Wild)", 2991, 3851, true), /**/
			new Teleport("Corporal Beast", 2976, 4384, 2), /**/
			new Teleport("Chaos Elemental (Wild)", 3260, 3927, true), /**/
			new Teleport("Dagannoth Kings", 2900, 4449), /**/
			new Teleport("Demonic Gorilla", 2116, 5658), /**/
			new Teleport("Godwars", CustomTeleports.godwarsTeleport), /**/
			new Teleport("Giant Mole 'Spade'", 3000, 3383), /**/
			new Teleport("KBD Entrance (Wild)", 3005, 3850, true), /**/
			new Teleport("Kraken", Kraken::enter), /**/
			new Teleport("Lizardman Shaman", 1495, 3700), /**/
			new Teleport("Kalphite Queen", Kalphite::enter) /**/
	), /**/
	MINIGAME( /**/
			new int[] { 4150, 51005, 117154 }, /**/
			new Teleport("Barrows", 3565, 3314), /**/
			new Teleport("Duel Arena", 3365, 3266), /**/
			new Teleport("Fight Caves", 2445, 5176), /**/
			new Teleport("Fishing Tourney", 2639, 3441), /**/
			new Teleport("Warrior's Guild", 2846, 3542) /**/
	), /**/
	SKILLING( /**/
			new int[] { 6004, 51013, 117186 }, /**/
			new Teleport("Agility Courses", CustomTeleports.agilityTeleport),/**/
			new Teleport("Blast Mining", 1469, 3863), /**/
			new Teleport("Skilling Area", 3027, 3379), /**/
			new Teleport("Hunter", CustomTeleports.hunterTeleport), /**/
			new Teleport("Resource Area (Wild)", 3184, 3945, true), /**/
			new Teleport("Runecrating Abyss", 3039, 4835), /**/
			new Teleport("Farming", 3001, 3374), /**/
			new Teleport("Woodcutting Guild", 1590, 3482) /**/
	); /**/

	private final int[] buttonIds;
	private final Teleport[] teleports;

	Teleports(int[] buttonIds, Teleport... teleports) {
		this.buttonIds = buttonIds;
		this.teleports = teleports;
	}

	public int[] getButtonIds() {
		return buttonIds;
	}

	public Teleport[] getTeleports() {
		return teleports;
	}

	private static Teleports[] values = values();

	public static boolean onButton(Player player, int buttonId) {
		Optional<Teleports> optional = Arrays.stream(values)
				.filter(value -> ArrayUtils.contains(value.getButtonIds(), buttonId)).findFirst();

		if (optional.isPresent()) {
			Teleports value = optional.get();
			player.start(new TeleportDialogue(value.getTeleports()));
			return true;
		}

		return false;
	}

	private static class CustomTeleports {

		public static final Consumer<Player> godwarsTeleport = player -> {
			player.start(new OptionDialogue(/**/
					"Armadyl", p -> TeleportExecutor.teleport(p, new Position(2839, 5292, 2)), /**/
					"Bandos", p -> TeleportExecutor.teleport(p, new Position(2860, 5354, 2)), /**/
					"Zamorak", p -> TeleportExecutor.teleport(p, new Position(2925, 5335, 2)), /**/
					"Saradomin", p -> TeleportExecutor.teleport(p, new Position(2909, 5265, 0)) /**/
			));
		};
		public static final Consumer<Player> agilityTeleport = player -> {
			player.start(new OptionDialogue(/**/
					"Draynor Agility Course", p -> TeleportExecutor.teleport(p, new Position(3105, 3279, 0)), /**/
					"Al Kharid Agility Course", p -> TeleportExecutor.teleport(p, new Position(3273, 3197, 0)), /**/
					"Varrock Agility Course", p -> TeleportExecutor.teleport(p, new Position(3223, 3414, 0)), /**/
					"Canifis Agility Course", p -> TeleportExecutor.teleport(p, new Position(3506, 3487, 0)), /**/
					"Coming Soon", p -> player.getDH().sendStatement("This teleport is coming soon.") /**/
			));
		};

		public static final Consumer<Player> hunterTeleport = player -> {
			player.start(new OptionDialogue(/**/
					"Hunter (1-50)", p -> TeleportExecutor.teleport(p, new Position(1834, 3669)), /**/
					"Hunter (50-99)", p -> TeleportExecutor.teleport(p, new Position(1829, 3601)) /**/
			));
		};

	}

}