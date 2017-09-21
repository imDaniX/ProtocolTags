package ru.dondays.protocoltags;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dondays.protocoltags.api.TagManager;
import ru.dondays.protocoltags.bridge.VaultBridge;
import ru.dondays.protocoltags.config.ConfigurationManager;
import ru.dondays.protocoltags.listeners.PlayerListener;

public class ProtocolTags
    extends JavaPlugin {

    private static ProtocolTags instance;

    private ConfigurationManager configManager = new ConfigurationManager(this);
    private TagManager tagManager = new TagManager();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        VaultBridge.setupHooks();
        configManager.load();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public TagManager getTagManager() {
        return tagManager;
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
        TagSetup.setTags();
        sender.sendMessage("§aКонфигурация успешно перезагружена!");
        return true;
    }

    public ConfigurationManager getConfiguration() {
        return configManager;
    }

    public static ProtocolTags getInstance() {
        return instance;
    }
}
