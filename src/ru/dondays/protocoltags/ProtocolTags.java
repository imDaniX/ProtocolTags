package ru.dondays.protocoltags;

import org.bukkit.plugin.java.JavaPlugin;
import ru.dondays.protocoltags.api.TagManager;
import ru.dondays.protocoltags.bridge.VaultBridge;
import ru.dondays.protocoltags.commands.ProtocolTagsCommand;
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
        getCommand("protocoltags").setExecutor(new ProtocolTagsCommand());
    }

    public TagManager getTagManager() {
        return tagManager;
    }

    public ConfigurationManager getConfiguration() {
        return configManager;
    }

    public static ProtocolTags getInstance() {
        return instance;
    }
}
