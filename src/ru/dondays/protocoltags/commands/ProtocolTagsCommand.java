package ru.dondays.protocoltags.commands;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.dondays.protocoltags.ProtocolTags;
import ru.dondays.protocoltags.TagSetup;

import java.util.Set;

public class ProtocolTagsCommand
    implements CommandExecutor {

    private static final Set<String> SUBCOMMANDS = Sets.newHashSet(
            "retag",
            "reload"
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("protocoltags.reload")) {
            sender.sendMessage("§cУ вас нет прав");
            return false;
        }
        if(args.length == 0 || !SUBCOMMANDS.contains(args[0].toLowerCase())) {
            sender.sendMessage("§aProtocolTags v" + ProtocolTags.getInstance().getDescription().getVersion());
            sender.sendMessage("§eОбновить тег игрока - §c/protocoltags retag [игрок]");
            sender.sendMessage("§eПерезагрузить конфигурацию - §c/protocoltags reload");
            return false;
        }
        switch(args[0].toLowerCase()) {
            case "retag": {
                if(args.length != 2) {
                    sender.sendMessage("§eИспользуйте - §c/protocoltags retag [игрок]");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null || !target.isOnline()) {
                    sender.sendMessage("§cИгрок не найден");
                    return false;
                }
                ProtocolTags.getInstance().getTagManager().clearTag(target);
                Bukkit.getScheduler().runTaskLater(ProtocolTags.getInstance(), () -> TagSetup.setTags(target), 2L);
                sender.sendMessage("§eТег игрока §c" + target.getName() + "§e обновлен");
                break;
            }
            case "reload": {
                ProtocolTags.getInstance().reloadConfig();
                ProtocolTags.getInstance().getConfiguration().load();
                ProtocolTags.getInstance().getTagManager().clearTags();
                TagSetup.setTags();
                sender.sendMessage("§aКонфигурация успешно перезагружена!");
                break;
            }
        }
        return true;
    }
}
