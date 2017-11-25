package org.brutality.model.players.skills.farming;

import java.util.ArrayList;
import java.util.List;

import org.brutality.Config;
import org.brutality.Server;
import org.brutality.event.CycleEvent;
import org.brutality.event.CycleEventContainer;
import org.brutality.event.CycleEventHandler;
import org.brutality.model.items.ItemDefinition;
import org.brutality.model.players.Player;
import org.brutality.model.players.skills.Skill;
import org.brutality.util.Misc;

/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @date Oct 27, 2013
 */

public class Farming {

	public static final int MAX_PATCHES = 1;
	private Player player;
	private int weeds;

	public Farming(Player player) {
		this.player = player;
	}

	private int HARVEST_SEEDS = 8143;

	public void patchObjectInteraction(final int objectId, final int itemId, final int x, final int y) {
		Patch patch = Patch.get(x, y);
		if (patch == null)
			return;
		final int id = patch.getId();
		player.face(x, y);
		if (objectId == FarmingConstants.GRASS_OBJECT || objectId == FarmingConstants.HERB_PATCH_DEPLETED) {
			if (player.getFarmingState(id) < State.RAKED.getId()) {
				if (!player.getItems().playerHasItem(FarmingConstants.RAKE, 1))
					player.sendMessage("You need to rake this patch to remove all the weeds.");
				else if (itemId == FarmingConstants.RAKE || player.getItems().playerHasItem(FarmingConstants.RAKE)) {
					player.animation(FarmingConstants.RAKING_ANIM);
					player.face(x, y);
					if (weeds <= 0)
						weeds = 3;
					CycleEventHandler.getSingleton().stopEvents(this);
					CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (player == null || player.disconnected || player.teleporting || player.isDead) {
								container.stop();
								return;
							}
							if (weeds > 0) {
								weeds--;
								player.face(x, y);
								if (player.getItems().freeSlots() >= 1) {
								player.getItems().addItem(6055, 1);
								} else {
									Server.itemHandler.createGroundItem(player, 6055, player.absX, player.absY, player.heightLevel, 1, player.index);
								}
								player.animation(FarmingConstants.RAKING_ANIM);
							} else if (weeds == 0) {
								player.setFarmingState(id, State.RAKED.getId());
								player.sendMessage(
										"You raked the patch of all it's weeds, now the patch is ready for compost.",
										255);
								player.animation(65535);
								updateObjects();
								container.stop();
							}
						}

						@Override
						public void stop() {

						}

					}, 3);
				}
			} else if (player.getFarmingState(id) >= State.RAKED.getId()
					&& player.getFarmingState(id) < State.COMPOST.getId()) {
				if (!player.getItems().playerHasItem(FarmingConstants.COMPOST, 1))
					player.sendMessage("You need to put compost on this to enrich the soil.");
				else if (itemId == FarmingConstants.COMPOST
						|| player.getItems().playerHasItem(FarmingConstants.COMPOST) && itemId == -1) {
					player.face(x, y);
					player.animation(FarmingConstants.PUTTING_COMPOST);
					player.getItems().deleteItem2(FarmingConstants.COMPOST, 1);
					player.getItems().addItem(3727, 1);
					player.setFarmingState(id, State.COMPOST.getId());
					player.sendMessage("You put compost on the soil, it is now time to seed it.");
				}
			} else if (player.getFarmingState(id) >= State.COMPOST.getId()
					&& player.getFarmingState(id) < State.SEEDED.getId()) {
				if (!player.getItems().playerHasItem(FarmingConstants.SEED_DIBBER, 1)) {
					player.sendMessage("You need to use a seed dibber with a seed on this patch.");
					return;
				}
				final FarmingHerb.Herb herb = FarmingHerb.getHerbForSeed(itemId);
				if (herb == null) {
					player.sendMessage("You must use an appropriate seed on the patch at this stage.");
					return;
				}
				if (Player.getLevelForXP(player.playerXP[19]) < herb.getLevelRequired()) {
					player.sendMessage("You need a farming level of " + herb.getLevelRequired() + " to grow "
							+ ItemDefinition.forId(itemId).getName() + ".");
					return;
				}
				if (itemId == herb.getSeedId() && player.getItems().playerHasItem(FarmingConstants.SEED_DIBBER)) {
					player.face(x, y);
					player.animation(FarmingConstants.SEED_DIBBING);
					CycleEventHandler.getSingleton().stopEvents(this);
					CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

						@Override
						public void execute(CycleEventContainer container) {
							if (player == null || player.disconnected || player.teleporting || player.isDead) {
								container.stop();
								return;
							}
							if (!player.getItems().playerHasItem(herb.getSeedId()))
								return;
							player.getItems().deleteItem2(herb.getSeedId(), 1);
							player.setFarmingState(id, State.SEEDED.getId());
							player.setFarmingSeedId(id, herb.getSeedId());
							if (player.playerEquipment[player.playerWeapon] == FarmingConstants.MAGIC_SECATEURS) {
								player.setFarmingTime(id, herb.getGrowthTime() / 2);
								player.setFarmingHarvest(id, 6 + Misc.random(1));
							} else {
								player.setFarmingTime(id, herb.getGrowthTime());
								player.setFarmingHarvest(id, 3 + Misc.random(4));
							}
							if (player.playerEquipment[player.playerHat] == 13646 && player.playerEquipment[player.playerChest] == 13642 && player.playerEquipment[player.playerLegs] == 13640 && player.playerEquipment[player.playerFeet] == 13644) {
								player.getPA().addSkillXP(herb.getPlantingXp() * Config.FARMING_EXPERIENCE * .05, 19);
							} else {
								player.getPA().addSkillXP(herb.getPlantingXp() * Config.FARMING_EXPERIENCE, 19);
							}
							player.sendMessage("You dib a seed into the soil, it is now time to water it.");
							updateObjects();
							container.stop();
						}

						@Override
						public void stop() {
						}

					}, 3);
				}
			}
		} else if (objectId == FarmingConstants.HERB_OBJECT) {
			if (player.getFarmingState(id) >= State.SEEDED.getId()
					&& player.getFarmingState(id) < State.GROWTH.getId()) {
				if (!player.getItems().playerHasItem(FarmingConstants.WATERING_CAN, 1))
					player.sendMessage("You need to water the herb before you can harvest it.");
				else if (itemId == FarmingConstants.WATERING_CAN
						|| player.getItems().playerHasItem(FarmingConstants.WATERING_CAN) && itemId == -1) {
					player.face(x, y);
					player.animation(FarmingConstants.WATERING_CAN_ANIM);
					player.setFarmingState(id, State.GROWTH.getId());
					player.sendMessage("You water the herb, wait " + ((int) (player.getFarmingTime(id) * .6))
							+ " seconds for the herb to mature.");
					return;
				}
			}
			if (player.getFarmingState(id) == State.GROWTH.getId()) {
				if (player.getFarmingTime(id) > 0) {
					player.sendMessage("You need to wait another " + (player.getFarmingTime(id) * .6)
							+ " seconds until the herb is mature.");
					return;
				}
			}
			if (player.getFarmingState(id) == State.HARVEST.getId()) {
				if (player.getItems().freeSlots() < 1) {
					player.getDH().sendStatement("You need atleast 1 free space to harvest some herbs.");
					player.nextChat = -1;
					return;
				}
				if (player.getFarmingHarvest(id) == 0 || player.getFarmingState(id) != State.HARVEST.getId()) {
					resetValues(id);
					updateObjects();
					return;
				}
				final FarmingHerb.Herb herb = FarmingHerb.getHerbForSeed(player.getFarmingSeedId(id));
				if (herb != null) {
					CycleEventHandler.getSingleton().stopEvents(this);
					CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

						@Override
						public void execute(CycleEventContainer container) {
							if (player == null || player.disconnected || player.teleporting || player.isDead) {
								container.stop();
								return;
							}
							if (player.getItems().freeSlots() < 1) {
								player.getDH().sendStatement("You need atleast 1 free space to harvest some herbs.");
								player.nextChat = -1;
								player.animation(65535);
								container.stop();
								return;
							}
							if (player.getFarmingHarvest(id) <= 0) {
								player.sendMessage("The herb patch has completely depleted...", 600000);
								player.animation(65535);
								resetValues(id);
								updateObjects();
								container.stop();
								return;
							}
							player.animation(FarmingConstants.PICKING_HERB_ANIM);
							player.setFarmingHarvest(id, player.getFarmingHarvest(id) - 1);
							player.getItems().addItem(herb.getGrimyId(), 1);
							int chance = Misc.random(50);
							if (chance == 0) {
								player.getPA().rewardPoints(2, "Congrats, You randomly got 2 PK Points from farming!");
							}
							if (player.playerEquipment[player.playerHat] == 13646 && player.playerEquipment[player.playerChest] == 13642 && player.playerEquipment[player.playerLegs] == 13640 && player.playerEquipment[player.playerFeet] == 13644) {
								player.getPA().addSkillXP(herb.getHarvestingXp() * Config.FARMING_EXPERIENCE * .05, 19);
							} else {
								player.getPA().addSkillXP(herb.getHarvestingXp() * Config.FARMING_EXPERIENCE, 19);
							}
						}

						@Override
						public void stop() {
						}

					}, 3);
				}
			}
		}
	}

	int seedsId[] = { 5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304 };

	public int itemUsed() {
		return seedsId[seedsId.length - 1];
	}

	public void farmingProcess() {
		for (int i = 0; i < Farming.MAX_PATCHES; i++) {
			if (player.getFarmingTime(i) > 0 && player.getFarmingState(i) == Farming.State.GROWTH.getId()) {
				player.setFarmingTime(i, player.getFarmingTime(i) - 1);
				if (player.getFarmingTime(i) == 0) {
					FarmingHerb.Herb herb = FarmingHerb.getHerbForSeed(player.getFarmingSeedId(i));
					if (herb != null)
						// player.sendMessage("Your farming patch of
						// "+herb.getSeedName().replaceAll(" seed", "")+" is
						// ready to be harvested.", 255);
						// player.sendMessage("Your farming patch is ready to be
						// harvested.");
						player.sendMessage("Your farming patch of " + ItemDefinition.forId(herb.seedId).getName()
								+ ". is ready to be harvested.");
					player.setFarmingState(i, Farming.State.HARVEST.getId()); // TO
																				// DO
				}
			}
		}
	}

	public void resetValues(int id) {
		player.setFarmingHarvest(id, 0);
		player.setFarmingSeedId(id, 0);
		player.setFarmingState(id, 0);
		player.setFarmingTime(id, 0);
	}

	public void updateObjects() {
		for (int i = 0; i < Farming.MAX_PATCHES; i++) {
			Patch patch = Patch.get(i);
			if (patch == null)
				continue;
			if (player.distanceToPoint(patch.getX(), patch.getY()) > 60)
				continue;
			if (player.getFarmingState(i) < State.RAKED.getId()) {
				player.getPA().checkObjectSpawn(FarmingConstants.GRASS_OBJECT, patch.getX(), patch.getY(), 0, 10);
			} else if (player.getFarmingState(i) >= State.RAKED.getId()
					&& player.getFarmingState(i) < State.SEEDED.getId()) {
				player.getPA().checkObjectSpawn(FarmingConstants.HERB_PATCH_DEPLETED, patch.getX(), patch.getY(), 0,
						10);
			} else if (player.getFarmingState(i) >= State.SEEDED.getId()) {
				player.getPA().checkObjectSpawn(FarmingConstants.HERB_OBJECT, patch.getX(), patch.getY(), 0, 10);
			}
		}
	}

	public boolean isHarvestable(int id) {
		return player.getFarmingState(id) == State.HARVEST.getId();
	}

	public enum State {
		NONE(0), RAKED(1), COMPOST(2), SEEDED(3), WATERED(4), GROWTH(5), HARVEST(6);

		private int id;

		State(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	enum Patch {
		FALADOR_PARK(0, 3003, 3372);

		int id, x, y;

		Patch(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}

		public int getId() {
			return this.id;
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		static List<Patch> patches = new ArrayList<>();

		static {
			for (Patch patch : Patch.values())
				patches.add(patch);
		}

		public static Patch get(int x, int y) {
			for (Patch patch : patches)
				if (patch.getX() == x && patch.getY() == y)
					return patch;
			return null;
		}

		public static Patch get(int id) {
			for (Patch patch : patches)
				if (patch.getId() == id)
					return patch;
			return null;
		}
	}
}
