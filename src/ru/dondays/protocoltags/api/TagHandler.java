package ru.dondays.protocoltags.api;

import org.bukkit.entity.Player;
import ru.dondays.protocoltags.Main;

public class TagHandler {

    public static void resetTag(final Player player) {
        Main.getInstance().getTagManager().resetTag(player);
    }

    public static void setTag(final Player player, String prefix) {
        setTag(player, prefix, "");
    }

    public static void setTag(final Player player, final String prefix, final String suffix) {
        setTag(player, player.getName(), prefix, suffix);
    }

    public static void setTag(final Player player, final String name, final String prefix, final String suffix) {
        Main.getInstance().getTagManager().setTag(player, name, prefix, suffix);
    }
}
