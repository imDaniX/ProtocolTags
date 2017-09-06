package ru.dondays.protocoltags.utils;

import com.google.common.collect.Maps;
import ru.dondays.protocoltags.ProtocolTags;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.lang.reflect.Field;
import java.util.Map;

public class Utils {

    private static Map<String, String> positions;

    public static String fixName(String input) {
        if(input.length() > 15) return input.substring(0, 15);
        return input;
    }

    public static String fix(String input) {
        if(input.length() > 16) return input.substring(0, 16);
        return input;
    }

    public static String getTeamName(PermissionUser user) {
        String s = positions.get(getGroup(user));
        if(s == null) s = "Z";
        return s + getGroup(user);
    }

    public static String getGroup(PermissionUser user) {
        PermissionGroup[] groups = user.getGroups();
        if(groups.length <= 0) return ProtocolTags.getInstance().getConfig().getString("DefaultGroup");
        return groups[0].getName();
    }

    public static PermissionGroup getPermissionGroup(PermissionUser user) {
        PermissionGroup[] groups = user.getGroups();
        if(groups.length <= 0) return PermissionsEx.getPermissionManager().getGroup(ProtocolTags.getInstance().getConfig().getString("DefaultGroup"));
        return groups[0];
    }

    public static void setField(Class<?> clazz, Object instance, String name, Object value) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            f.set(instance, value);
        } catch(Exception ex) {
            ProtocolTags.getInstance().getLogger().info("Ошибка установки значения переменной: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void load() {
        if(positions != null) {
            positions.clear();
        } else {
            positions = Maps.newHashMap();
        }
        ProtocolTags.getInstance().getConfig().getConfigurationSection("Positions").getKeys(false).forEach(position ->
            positions.put(position, ProtocolTags.getInstance().getConfig().getString("Positions." + position))
        );
    }
}
