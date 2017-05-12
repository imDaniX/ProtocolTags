package ru.dondays.protocoltags.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.dondays.protocoltags.Main;
import ru.dondays.protocoltags.Utils;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerListener
    implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            if(!e.getPlayer().isOnline()) return;
            PermissionUser user = PermissionsEx.getUser(e.getPlayer());
            Main.getInstance().getTagManager().setTag(e.getPlayer(), Utils.getTeamName(user), user.getPrefix().replace("&", "§"), user.getSuffix().replace("&", "§"));
            Main.getInstance().getTagManager().sendTags(e.getPlayer());
        }, 10L);
    }
}
