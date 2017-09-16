package ru.dondays.protocoltags.groups;

import org.bukkit.entity.Player;
import ru.dondays.protocoltags.api.TagAppiler;
import ru.dondays.protocoltags.utils.Utils;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Group {

    private String name;
    private String prefix;
    private String suffix;
    private String priority;

    public Group(String name, String prefix, String suffix, int priority) {
        this(name, prefix, suffix, priority > 26 || priority == 1 ? "A" : String.valueOf((char)('A'+priority)));
    }

    public Group(String name, String prefix, String suffix, String priority) {
        this.name = name;
        this.priority = priority;

        if(prefix == null || suffix == null) {
            PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(name);
            String groupPrefix = "";
            String groupSuffix = "";
            if(group != null) {
                groupPrefix = group.getPrefix();
                groupSuffix = group.getSuffix();
            }
            if(prefix == null) prefix = groupPrefix;
            if(suffix == null) suffix = groupSuffix;
        }
        this.prefix = Utils.colorize(prefix);
        this.suffix = Utils.colorize(suffix);
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPriority() {
        return priority;
    }

    public void apply(Player player) {
        TagAppiler.applyTag(player, Utils.parseName(priority + name), prefix, suffix);
    }
}
