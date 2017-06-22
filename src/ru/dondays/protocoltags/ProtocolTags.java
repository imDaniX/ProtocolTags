package ru.dondays.protocoltags;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dondays.protocoltags.api.TagManager;
import ru.dondays.protocoltags.listeners.PlayerListener;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ProtocolTags
    extends JavaPlugin {

    private static ProtocolTags instance;
    private TagManager tagManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        Utils.load();
        this.tagManager = new TagManager();
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public TagManager getTagManager() {
        return this.tagManager;
    }

    public void setTags(Player player) {
        if(!ProtocolTags.getInstance().getConfig().getBoolean("SetTags", true)) return;
        PermissionUser user = PermissionsEx.getUser(player);
        if(!Utils.getPermissionGroup(user).getPrefix().equals(user.getPrefix()) || !Utils.getPermissionGroup(user).getSuffix().equals(user.getSuffix())) {
            tagManager.setTag(player, ChatColor.translateAlternateColorCodes('&', user.getPrefix()), ChatColor.translateAlternateColorCodes('&', user.getSuffix()));
        } else {
            tagManager.setTag(player, Utils.getTeamName(user), ChatColor.translateAlternateColorCodes('&', user.getPrefix()), ChatColor.translateAlternateColorCodes('&', user.getSuffix()));
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
        reload();
        sender.sendMessage("§aКонфигурация успешно перезагружена!");
        return true;
    }

    public void reload() {
        Utils.load();
        this.tagManager.clearTags();
        synchronized(this) {
            getServer().getOnlinePlayers().forEach(this::setTags);
        }
    }

    public static ProtocolTags getInstance() {
        return instance;
    }
}
