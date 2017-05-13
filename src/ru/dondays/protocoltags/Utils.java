package ru.dondays.protocoltags;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Utils {

    private static Map<String, String> positions;

    public static String getTeamName(final PermissionUser user) {
        String s = positions.get(getGroup(user));
        if(s == null) s = "Z";
        return s + getGroup(user);
    }

    public static String getGroup(final PermissionUser user) {
        PermissionGroup[] groups = user.getGroups();
        if(groups.length <= 0) return Main.getInstance().getConfig().getString("DefaultGroup");
        return groups[0].getName();
    }

    public static PermissionGroup getPermissionGroup(final PermissionUser user) {
        PermissionGroup[] groups = user.getGroups();
        if(groups.length <= 0) return PermissionsEx.getPermissionManager().getGroup(Main.getInstance().getConfig().getString("DefaultGroup"));
        return groups[0];
    }

    public static Set<String> getNames(final Collection<Player> players) {
        final Set<String> names = Sets.newHashSet();
        players.forEach(p -> names.add(p.getName()));
        return names;
    }

    public static void setField(final Class<?> clazz, final Object instance, final String name, final Object value) {
        try {
            final Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            f.set(instance, value);
        } catch(final Exception ex) {
            Main.getInstance().getLogger().info("Ошибка установки значения переменной: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    static void load() {
        if(positions != null) {
            positions.clear();
        } else {
            positions = Maps.newHashMap();
        }
        Main.getInstance().getConfig().getConfigurationSection("Positions").getKeys(false).forEach(position ->
            positions.put(position, Main.getInstance().getConfig().getString("Positions." + position))
        );
    }
}
