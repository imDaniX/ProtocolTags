package ru.dondays.protocoltags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.dondays.protocoltags.api.TagApplier;
import ru.dondays.protocoltags.bridge.VaultBridge;
import ru.dondays.protocoltags.groups.Group;
import ru.dondays.protocoltags.utils.Utils;

public class TagSetup {

    public static void setTags() {
        Bukkit.getOnlinePlayers().forEach(TagSetup::setTags);
    }

    public static void setTags(Player player) {
        if(ProtocolTags.getInstance().getConfig().getBoolean("UseAsAPI", false)) return;
        String group = VaultBridge.getPermission().getPrimaryGroup(player).toLowerCase();

        String groupPrefix = VaultBridge.getChat().getGroupPrefix(player.getWorld(), group);
        String groupSuffix = VaultBridge.getChat().getGroupSuffix(player.getWorld(), group);
        String playerPrefix = VaultBridge.getChat().getPlayerPrefix(player);
        String playerSuffix = VaultBridge.getChat().getPlayerSuffix(player);

        if(ProtocolTags.getInstance().getConfiguration().isCustomTagsEnabled()
                && (!groupPrefix.equals(playerPrefix) || !groupSuffix.equals(playerSuffix))) {
            TagApplier.applyTag(player, ProtocolTags.getInstance().getConfiguration().getPriority(group) + player.getName(),
                    Utils.colorize(playerPrefix), Utils.colorize(playerSuffix));
        } else {
            Group tagGroup = ProtocolTags.getInstance().getConfiguration().getGroupFromMap(group);
            if(tagGroup != null) {
                tagGroup.apply(player);
            } else {
                TagApplier.applyTag(player, ProtocolTags.getInstance().getConfiguration().getPriority(group) + player.getName(),
                        Utils.colorize(playerPrefix), Utils.colorize(playerSuffix));
            }
        }

        if(ProtocolTags.getInstance().getConfiguration().isDisplayNamesRewrite()) {
            player.setDisplayName((ProtocolTags.getInstance().getConfiguration().isCustomTagsEnabled() ?
                    Utils.colorize(playerPrefix) :
                    Utils.colorize(groupPrefix)) + player.getName());
        }
    }
}
