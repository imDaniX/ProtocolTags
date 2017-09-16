package ru.dondays.protocoltags.config;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import ru.dondays.protocoltags.ProtocolTags;
import ru.dondays.protocoltags.groups.Group;

import java.util.Map;

public class ConfigurationManager {

    private ProtocolTags main;
    private String defaultGroup;
    private Map<String, Group> groups = Maps.newHashMap();
    private BukkitTask retagTask;

    public ConfigurationManager(ProtocolTags main) {
        this.main = main;
    }

    public void load() {
        groups.clear();

        defaultGroup = main.getConfig().getString("DefaultGroup").toLowerCase();
        main.getConfig().getConfigurationSection("Groups").getKeys(false).forEach(name ->
            groups.put(name.toLowerCase(), new Group(
                    name,
                    main.getConfig().getString("Groups." + name + ".prefix"),
                    main.getConfig().getString("Groups." + name + ".suffix"),
                    main.getConfig().getInt("Groups." + name + ".priority"))));

        if(!groups.containsKey(defaultGroup)) {
            groups.put(defaultGroup,
                    new Group(defaultGroup, "&7", "", "Z"));
        }

        if(main.getConfig().getBoolean("Retag.enabled")) {
            if(retagTask != null) retagTask.cancel();
            long interval = main.getConfig().getInt("Retag.interval")*20L;
            retagTask = Bukkit.getScheduler().runTaskTimerAsynchronously(main, () -> main.setTags(), interval, interval);
        } else {
            if(retagTask != null) retagTask.cancel();
        }
    }

    public boolean isCustomTagsEnabled() {
        return main.getConfig().getBoolean("EnableCustomTags");
    }

    public boolean isDisplayNamesRewrite() {
        return main.getConfig().getBoolean("RewriteDisplayNames");
    }

    public String getDefaultGroup() {
        return defaultGroup;
    }

    public Group getGroup(String name) {
        return groups.containsKey(name.toLowerCase()) ? groups.get(name.toLowerCase()) : groups.get(defaultGroup);
    }

    public String getPriority(String groupName) {
        return getGroup(groupName).getPriority();
    }
}
