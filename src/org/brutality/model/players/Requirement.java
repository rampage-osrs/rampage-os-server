package org.brutality.model.players;

import java.util.HashMap;
import java.util.Map;

import org.brutality.model.players.skills.Skill;

/**
 * The container class that represents one equipment requirement.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class Requirement {

    /**
     * The hash collection of equipment requirements.
     */
    public static final Map<Integer, Requirement[]> REQUIREMENTS = new HashMap<>();

    /**
     * The level of this equipment requirement.
     */
    private final int level;

    /**
     * The skill identifier for this equipment requirement.
     */
    private final Skill skill;

    /**
     * Creates a new {@link Requirement}.
     *
     * @param level
     *            the level of this equipment requirement.
     * @param skill
     *            the skill identifier for this equipment requirement.
     */
    public Requirement(int level, Skill skill) {
        this.level = level;
        this.skill = skill;
    }

    /**
     * A substitute for {@link Object#clone()} that creates another 'copy' of
     * this instance. The created copy is <i>safe</i> meaning it does not hold
     * <b>any</b> references to the original instance.
     *
     * @return a reference-free copy of this instance.
     */
    public Requirement copy() {
        return new Requirement(level, skill);
    }

    /**
     * Determines if {@code player} can equip {@code item} based on its
     * equipment requirements.
     *
     * @param player
     *            the player that is equipping the item.
     * @return {@code true} if the player can equip the item, {@code false}
     *         otherwise.
     */
    public static boolean canEquip(Player player, int id) {
        Requirement[] req = REQUIREMENTS.get(id);
       if (req == null)
            return true;
        for (Requirement r : req) {
            if (Player.getLevelForXP(player.playerXP[r.skill.getId()]) < r.level) {
                String append = appendIndefiniteArticle(r.skill.toString());
                player.sendMessage("You need " + append + " " + "level of " + r.level + " to equip this item.");
                return false;
            }
        }
        return true;
    }
    
    private static String determineIndefiniteArticle(String thing) {
        char first = thing.toLowerCase().charAt(0);
        boolean vowel = first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u';
        return vowel ? "an" : "a";
    }
    public static String appendIndefiniteArticle(String thing) {
        return determineIndefiniteArticle(thing).concat(" " + thing);
    }

    /**
     * Gets the level of this equipment requirement.
     *
     * @return the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the skill identifier for this equipment requirement.
     *
     * @return the skill identifier.
     */
    public Skill getSkill() {
        return skill;
    }
}