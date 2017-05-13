package ru.dondays.protocoltags.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.dondays.protocoltags.Main;
import ru.dondays.protocoltags.Utils;
import ru.dondays.protocoltags.api.TagHandler;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerListener
    implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.getInstance().getTagManager().clearTags(e.getPlayer());
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            if(!e.getPlayer().isOnline()) return;
            TagHandler.joinTag(e.getPlayer());
        }, 10L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> Main.getInstance().getTagManager().checkTag(e.getPlayer()), 10L);
    }
}
