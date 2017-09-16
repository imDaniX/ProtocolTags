package ru.dondays.protocoltags;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dondays.protocoltags.api.TagManager;
import ru.dondays.protocoltags.config.ConfigurationManager;
import ru.dondays.protocoltags.listeners.PlayerListener;
import ru.dondays.protocoltags.utils.Utils;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ProtocolTags
    extends JavaPlugin {

    private static ProtocolTags instance;
    private ConfigurationManager configManager = new ConfigurationManager(this);
    private TagManager tagManager = new TagManager();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        configManager.load();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public TagManager getTagManager() {
        return tagManager;
    }

    public void setTags() {
        getServer().getOnlinePlayers().forEach(this::setTags);
    }
    
    public void setTags(Player player) {
        PermissionUser user = PermissionsEx.getUser(player);
        PermissionGroup group = Utils.getPermissionGroup(user);
        if(user == null || group == null) return;

        if(configManager.isCustomTagsEnabled()
                && (!group.getPrefix().equals(user.getPrefix()) || !group.getSuffix().equals(user.getSuffix()))) {
            tagManager.setTag(player, configManager.getPriority(group.getName()),
                    Utils.colorize(user.getPrefix()), Utils.colorize(user.getSuffix()));
        } else {
            configManager.getGroup(group.getName()).apply(player);
        }

        if(configManager.isDisplayNamesRewrite()) {
            player.setDisplayName((configManager.isCustomTagsEnabled() ? Utils.colorize(user.getPrefix()) :
                Utils.colorize(group.getPrefix())) + player.getDisplayName());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("protocoltags.reload")) {
            sender.sendMessage("§cУ вас нет прав");
            return false;
        }
        if(args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("§eИспользуйте §c/protocoltags reload");
            return false;
        }
        reloadConfig();
        configManager.load();
        tagManager.clearTags();
        setTags();
        sender.sendMessage("§aКонфигурация успешно перезагружена!");
        return true;
    }

    public ConfigurationManager getConfigManager() {
        return configManager;
    }

    public static ProtocolTags getInstance() {
        return instance;
    }
}
