package ru.dondays.protocoltags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.dondays.protocoltags.api.TagApplier;
import ru.dondays.protocoltags.bridge.VaultBridge;
import ru.dondays.protocoltags.utils.Utils;

public class TagSetup {

    public static void setTags() {
        Bukkit.getOnlinePlayers().forEach(TagSetup::setTags);
    }

    public static void setTags(Player player) {
        String group = VaultBridge.getPermission().getPrimaryGroup(player).toLowerCase();

        String groupPrefix = VaultBridge.getChat().getGroupPrefix(player.getWorld(), group);
        String groupSuffix = VaultBridge.getChat().getGroupSuffix(player.getWorld(), group);
        String playerPrefix = VaultBridge.getChat().getPlayerPrefix(player);
        String playerSuffix = VaultBridge.getChat().getPlayerSuffix(player);

        if(ProtocolTags.getInstance().getConfiguration().isCustomTagsEnabled()
                && (!groupPrefix.equals(playerPrefix) || !groupSuffix.equals(playerSuffix))) {
            TagApplier.applyTag(player, ProtocolTags.getInstance().getConfiguration().getPriority(group),
                    Utils.colorize(playerPrefix), Utils.colorize(playerSuffix));
        } else {
            ProtocolTags.getInstance().getConfiguration().getGroup(group).apply(player);
        }

        if(ProtocolTags.getInstance().getConfiguration().isDisplayNamesRewrite()) {
            player.setDisplayName((ProtocolTags.getInstance().getConfiguration().isCustomTagsEnabled() ?
                    Utils.colorize(playerPrefix) :
                    Utils.colorize(groupPrefix)) + player.getName());
        }
    }
}
