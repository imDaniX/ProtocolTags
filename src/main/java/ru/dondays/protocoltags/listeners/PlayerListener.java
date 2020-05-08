package ru.dondays.protocoltags.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.dondays.protocoltags.ProtocolTags;
import ru.dondays.protocoltags.TagSetup;

public class PlayerListener
    implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(ProtocolTags.getInstance(), () -> {
            if(!e.getPlayer().isOnline()) return;
            ProtocolTags.getInstance().getTagManager().sendTags(e.getPlayer());
        }, 10L);
        Bukkit.getScheduler().runTaskLaterAsynchronously(ProtocolTags.getInstance(), () -> {
            if(!e.getPlayer().isOnline()) return;
            TagSetup.setTags(e.getPlayer());
        }, 12L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ProtocolTags.getInstance().getTagManager().clearTag(e.getPlayer());
    }
}
