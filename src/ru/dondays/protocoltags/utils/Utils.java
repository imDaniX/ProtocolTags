package ru.dondays.protocoltags.utils;

import org.bukkit.ChatColor;
import ru.dondays.protocoltags.ProtocolTags;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Utils {

    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String parseName(String input) {
        if(input.length() > 15) return input.substring(0, 15);
        return input;
    }

    public static String parse(String input) {
        if(input.length() > 16) return input.substring(0, 16);
        return input;
    }

    public static PermissionGroup getPermissionGroup(PermissionUser user) {
        PermissionGroup[] groups = user.getGroups();
        if(groups.length <= 0) {
            return PermissionsEx.getPermissionManager().getGroup(ProtocolTags.getInstance().getConfigManager().getDefaultGroup());
        }
        return groups[0];
    }
}
