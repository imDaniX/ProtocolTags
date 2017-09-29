package ru.dondays.protocoltags.api;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import ru.dondays.protocoltags.packetwrapper.WrapperPlayServerScoreboardTeam;
import ru.dondays.protocoltags.utils.Utils;

import java.util.Collection;
import java.util.stream.Collectors;

public class TagPacket {

    private WrapperPlayServerScoreboardTeam packet;

    /* ------------------------------------------------- */
    private String name;
    private int mode;
    private Collection<Player> players = Sets.newHashSet();
    /* ------------------------------------------------- */

    public TagPacket(String name, int mode) {
        this.name = Utils.parseName(name);
        this.mode = mode;

        this.packet = new WrapperPlayServerScoreboardTeam();

        this.packet.setName(name);
        this.packet.setDisplayName(name);
        this.packet.setMode(mode);
        this.packet.setNameTagVisibility("ALWAYS");
    }

    public TagPacket(String name, int mode, Player player) {
        this(name, mode);
        players.add(player);
        packet.getPlayers().add(player.getName());
    }

    public TagPacket(String name, int mode, Collection<Player> players) {
        this(name, mode);
        this.players.addAll(players);
        packet.getPlayers().addAll(players.stream().map(HumanEntity::getName).collect(Collectors.toList()));
    }

    public void insertData(TagData data) {
        this.packet.setPrefix(data.getPrefix());
        this.packet.setSuffix(data.getSuffix());
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    @Deprecated
    public void addPlayer(Player player) {
        addPlayer(player, null);
    }

    public void addPlayer(Player player, TagData data) {
        players.add(player);
        packet.getPlayers().add(player.getName());

        TagPacket packet = new TagPacket(getName(), WrapperPlayServerScoreboardTeam.Mode.PLAYERS_ADDED, player);
        if(data != null) packet.insertData(data);
        packet.send();
    }

    @Deprecated
    public void removePlayer(Player player) {
        removePlayer(player, null);
    }

    public void removePlayer(Player player, TagData data) {
        players.remove(player);
        packet.getPlayers().remove(player.getName());

        TagPacket packet = new TagPacket(getName(), WrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED, player);
        if(data != null) packet.insertData(data);
        packet.send();
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public Collection<String> getPacketMembers() {
        return unsafe().getPlayers();
    }

    public String getName() {
        return name;
    }

    public void send(Player player) {
        packet.sendPacket(player);
    }

    public void sendToTeamMembers() {
        players.forEach(this::send);
    }

    public void send() {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

    public WrapperPlayServerScoreboardTeam unsafe() {
        return packet;
    }

    public int getMode() {
        return mode;
    }
}
