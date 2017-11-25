package org.brutality.util.json;

import java.util.Arrays;
import java.util.Objects;

import org.brutality.model.npcs.drops.NpcDrop;
import org.brutality.model.npcs.drops.NpcDropCache;
import org.brutality.model.npcs.drops.NpcDropManager;
import org.brutality.model.npcs.drops.NpcDropTable;
import org.brutality.util.JsonLoader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The {@link JsonLoader} implementation that loads all npc drops.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class NpcDropTableLoader extends JsonLoader {

    /**
     * Creates a new {@link NpcDropTableLoader}.
     */
    public NpcDropTableLoader() {
        super("./Data/json/npc_drops.json");
    }

    @Override
    public void load(JsonObject reader, Gson builder) {
        int[] array = builder.fromJson(reader.get("ids"), int[].class);
        NpcDrop[] unique = Objects.requireNonNull(builder.fromJson(reader.get("unique"), NpcDrop[].class));
        NpcDropCache[] common = Objects.requireNonNull(builder.fromJson(reader.get("common"), NpcDropCache[].class));
        if (Arrays.stream(common).anyMatch(Objects::isNull))
            throw new NullPointerException("Invalid common drop table, npc_drops.json");
        Arrays.stream(array).forEach(id -> NpcDropManager.TABLES.put(id, new NpcDropTable(unique, common)));
    }
}