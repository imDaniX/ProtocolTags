package ru.dondays.protocoltags.api;

import ru.dondays.protocoltags.packetwrapper.WrapperPlayServerScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.dondays.protocoltags.utils.Utils;

import java.util.Collection;

public class TagData {

    private TagPacket packet;

    /* ---------------- */
    private String prefix;
    private String suffix;
    /* ---------------- */

    public TagData(String name, String prefix, String suffix) {
        packet = new TagPacket(name, WrapperPlayServerScoreboardTeam.Mode.TEAM_CREATED);

        this.prefix = Utils.fix(prefix);
        this.suffix = Utils.fix(suffix);

        packet.insertData(this);
    }

    public TagPacket getPacket() {
        return packet;
    }

    public String getName() {
        return getPacket().getName();
    }

    public boolean hasPlayer(Player player) {
        return getPacket().hasPlayer(player);
    }

    public void addPlayer(Player player) {
        getPacket().addPlayer(player, this);
    }

    public void removePlayer(Player player) {
        getPacket().removePlayer(player, this);
    }

    public Collection<Player> getPlayers() {
        return getPacket().getPlayers();
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void destroy() {
        Bukkit.getOnlinePlayers().forEach(this::destroy);
    }

    public void destroy(Player player) {
        TagPacket packet = new TagPacket(getName(), WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);
        packet.insertData(this);
        packet.send(player);
    }
}
