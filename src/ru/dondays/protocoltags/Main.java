package ru.dondays.protocoltags;

import org.bukkit.plugin.java.JavaPlugin;
import ru.dondays.protocoltags.api.TagManager;
import ru.dondays.protocoltags.listeners.PlayerListener;

public class Main
    extends JavaPlugin {

    private static Main instance;
    private TagManager tagManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.tagManager = new TagManager();
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public TagManager getTagManager() {
        return this.tagManager;
    }

    public static Main getInstance() {
        return instance;
    }
}
