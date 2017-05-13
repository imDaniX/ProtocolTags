package ru.dondays.protocoltags.api;

import com.comphenix.packetwrapper.WrapperPlayServerScoreboardTeam;
import org.bukkit.entity.Player;

public class TagPacket {

    private TagData data;
    private WrapperPlayServerScoreboardTeam packet;

    public TagPacket(final TagData data, final int mode) {
        this.data = data;
        this.packet = new WrapperPlayServerScoreboardTeam();

        this.packet.setName(data.getName());
        this.packet.setDisplayName(data.getName());
        this.packet.setPrefix(data.getPrefix());
        this.packet.setSuffix(data.getSuffix());
        this.packet.setMode(mode);
        this.packet.setNameTagVisibility("true");
    }

    public void update() {
        this.packet.setPlayers(data.getPlayerNames());
    }

    public void playerRemoved(final Player player) {
        final TagData r = new TagData(data.getName(), data.getPrefix(), data.getSuffix(), 4);
        r.addPlayer(player);
        r.send();
    }

    public void send(final Player player) {
        this.packet.sendPacket(player);
    }

    public void destroy(final Player player) {
        this.packet.setMode(1);
        this.data.send(player);
        this.packet.setMode(0);
    }
}
