package ru.dondays.protocoltags.groups;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.dondays.protocoltags.api.TagApplier;
import ru.dondays.protocoltags.bridge.VaultBridge;
import ru.dondays.protocoltags.utils.Utils;

public class Group {

    private String name;
    private String prefix;
    private String suffix;
    private String priority;

    public Group(String name, String prefix, String suffix, int priority) {
        this(name, prefix, suffix, priority > 26 || priority == 1 ? "A": String.valueOf((char)('A' + priority)));
    }

    public Group(String name, String prefix, String suffix, String priority) {
        this.name = name;
        this.priority = priority;

        String groupPrefix = VaultBridge.getChat().getGroupPrefix(Bukkit.getWorlds().get(0), name) == null ? "":
                VaultBridge.getChat().getGroupPrefix(Bukkit.getWorlds().get(0), name);
        String groupSuffix = VaultBridge.getChat().getGroupSuffix(Bukkit.getWorlds().get(0), name) == null ? "":
                VaultBridge.getChat().getGroupSuffix(Bukkit.getWorlds().get(0), name);
        if(prefix == null || prefix.isEmpty()) prefix = groupPrefix;
        if(suffix == null || suffix.isEmpty()) suffix = groupSuffix;
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
        TagApplier.applyTag(player, Utils.parseName(priority + player.getName()), prefix, suffix);
    }
}