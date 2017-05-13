package ru.dondays.protocoltags.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.dondays.protocoltags.Utils;

import java.util.List;
import java.util.Set;

public class TagData {

    private final String name;
    private final String prefix;
    private final String suffix;

    private Set<Player> players = Sets.newHashSet();

    private TagPacket packet;
    private boolean sended = false;

    protected TagData(final String name, final String prefix, final String suffix, final int goal) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;

        this.packet = new TagPacket(this, goal);
    }

    protected TagData(final String name, final String prefix, final String suffix) {
        this(name, prefix, suffix, 0);
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void send(Player player) {
        this.packet.send(player);
        if(!sended) sended = true;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(final Set<Player> players) {
        this.setPlayers(players, true);
    }

    public void setPlayers(final Set<Player> players, boolean update) {
        this.players = players;
        if(update) this.packet.update();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        this.packet.update();
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        this.packet.update();
    }

    public List<String> getPlayerNames() {
        return Lists.newArrayList(Utils.getNames(this.getPlayers()));
    }

    public void send() {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

    public TagPacket getPacket() {
        return packet;
    }

    public boolean isSended() {
        return sended;
    }
}
