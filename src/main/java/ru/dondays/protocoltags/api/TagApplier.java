package ru.dondays.protocoltags.api;

import org.bukkit.entity.Player;
import ru.dondays.protocoltags.ProtocolTags;

public class TagApplier {

    public static void applyTag(Player player, String prefix) {
        applyTag(player, prefix);
    }

    public static void applyTag(Player player, String prefix, String suffix) {
        applyTag(player, player.getName(), prefix, suffix);
    }

    public static void applyTag(Player player, String team, String prefix, String suffix) {
        ProtocolTags.getInstance().getTagManager().setTag(player, team, prefix, suffix);
    }
}
