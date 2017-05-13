package ru.dondays.protocoltags;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.dondays.protocoltags.api.TagHandler;
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
        Utils.load();
        this.tagManager = new TagManager();
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public TagManager getTagManager() {
        return this.tagManager;
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
        Utils.load();
        //this.tagManager.checkTag(player);
        this.getServer().getOnlinePlayers().forEach(TagHandler::joinTag);
        sender.sendMessage("§aКонфигурация успешно перезагружена!");
        return true;
    }

    public static Main getInstance() {
        return instance;
    }
}
