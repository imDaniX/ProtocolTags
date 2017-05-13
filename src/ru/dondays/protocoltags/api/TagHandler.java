package ru.dondays.protocoltags.api;

import org.bukkit.entity.Player;
import ru.dondays.protocoltags.Main;
import ru.dondays.protocoltags.Utils;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagHandler {

    public static void joinTag(final Player player) {
        final PermissionUser user = PermissionsEx.getUser(player);
        if(!Utils.getPermissionGroup(user).getPrefix().equals(user.getPrefix()) || !Utils.getPermissionGroup(user).getSuffix().equals(user.getSuffix())) {
            setTag(player, user.getPrefix().replace("&", "§"), user.getSuffix().replace("&", "§"));
        } else {
            setTag(player, Utils.getTeamName(user), user.getPrefix().replace("&", "§"), user.getSuffix().replace("&", "§"));
        }
    }

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
