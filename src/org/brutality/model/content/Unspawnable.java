package org.brutality.model.content;

import org.brutality.model.items.Item;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.players.Player;

public class Unspawnable {
	
	public final static String[] names = {
			"Dharok", "Ahrim", "Karil", "Verac", 
			"Partyhat", "Santa", "spirit shield", "Void", "3rd", 
			"Primordial", "Eternal", "Pegasian", "Anguish", "Tome", "Third",
			"Berserker ring", "Seers ring", "Archers ring", "Warrior ring",
			"godsword", "Staff of the dead", "Smouldering", "Bandos", "Fighter",
			"tentacle", "Fire cape", "Super combat", "partyhat", "h'ween", "halloween",
			"Halloween", "Odium", "Dark infinity", "Light infinity", "dark bow paint",
			"Volcanic", "Malediction", "Ranger boot", "Robin hood", "Zamorakian", "Armadyl",
			"max cape", "max hood", "hilt", "Max cape", "Max hood", "Occult", "Graceful", "Pet",
			"sigil", "Bunny", "Overload", "Crystal hal", "Magma", "Tanzanite", "Antisanta",
			"Cow", "Black mask", "Slayer helm", "slayer helm", "Baby", "Kalphite", "Vet'ion", "Abyssal dagger",
			"ballista", "Ballista", "crystal hal", "Mysterious", "Looting", "Toxic", "Trident", "trident", "toxic",
			"fury", "Dragonfire", "Sled", "Dragon warhammer", "of anguish", "Ring of suffering", "Tormented bracelet",
			"null", "crate", "torture", "Guthan", "Graceful", "Super combat", "fury", "Draconic", "Serp", "Zenyte", "Onyx",
			"Ring of wealth", "Dark crab", "Raw dark crab"
	};
	
	public final static int[] ids = {
			9747, 9748, 9749, 9751, 9752, 9753, 9754, 9757, 9758, 9760, 9761, 9755, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795,
			9792, 9774, 9771, 9777, 9786, 9810, 9765, 9789, 9763, 9764, 9948, 13280, 13281, 13282, 13329, 13330,
			13331, 13332, 13333, 13334, 13335, 13336, 13337, 13338, 13342, 9766, 9767, 9768, 9769, 9770, 9772,
			9774, 9775, 9776, 9778, 9779, 9781, 9782, 9784, 9785, 9787, 9788, 9790, 9791, 9793, 9794, 9796, 9797,
			9799, 9800, 9802, 9803, 9805, 9806, 9808, 9809, 9811, 9812, 9813, 9814
	};
	
	public static void spawnItem(Player player,int id, int amount){
		String message = canSpawn(id);
		if(message.length() > 0 ){
			player.sendMessage(message);
			return;
		}
		player.getItems().addItem(id, amount);
	}

	public static String canSpawn(int itemId) {
		switch (itemId) {
		case 962:
		case 963:
		case 12691:
		case 12692:
		case 10887:
		case 10888:
		case 11798:
		case 11799:
		case 4551:
		case 4552:
		case 4164:
		case 4165:
		case 4166:
		case 4167:
		case 4168:
		case 4169:
		case 12653:
		case 2572:
		case 11849:
		case 13073:
		case 13072:
		case 13241:
		case 13243:
		case 6739:
		case 6740:
		case 12774:
		case 12773:
		case 19722:
		case 10594:
		case 5020:
		case 13204:
		case 13307:
		case 8125:
		case 2701:
		case 2691:
		case 2690:
		case 2692:
		case 11936:
		case 13441:
		case 13439:
		case 11934:
		case 2996:
		case 10548:
		case 12954:
		case 8851:
		case 11235:
		case 11236:
		case 15098:
		case 299:
		case 6199:
		case 12601: 
		case 13344:
		case 13343:
		case 12602: 
		case 12791:
		case 995:
		case 12603: 
		case 13320: 
		case 13225: 
		case 13321: 
		case 13322: 
		case 13247:
		case 12604: 
		case 12605: 
		case 12606:
		case 13263:
		case 4149:
		case 13262:
		case 13178:
		case 13177:
		case 13181:
		case 10476:
		case 12851:
		case 12853:
		case 13652:
		case 12904:
		case 12610:
		case 12608:
		case 3272:
		case 12612:
		case 6889:
		case 6914:
		case 12596:
		case 19994:
		case 12804:
		case 12526:
		case 12932:
		case 12934:
		case 12938:
		case 20408:
		case 19970:
		case 12765:
		case 12766:
		case 12767:
		case 12768:
			return "This item is not spawnable";
		}
		String itemName = ItemDefinition.forId(itemId).getName();
		for (String unspawnables : names) {
			if (itemName.contains(unspawnables))
				return "This item is not spawnable";
		}

		for (int i : ids) {
			if (itemId == i) {
				return "This item is not spawnable";
			}
		}
		return "";
	}
}
