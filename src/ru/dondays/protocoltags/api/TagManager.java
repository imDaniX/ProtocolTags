package ru.dondays.protocoltags.api;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;

public class TagManager {

    private Map<String, TagData> datas = Maps.newConcurrentMap();

    public TagManager() {
        TagHandler.init(this);
    }

    public void setTag(Player player, String prefix) {
        setTag(player, prefix, "");
    }

    public void setTag(Player player, String prefix, String suffix) {
        setTag(player, player.getName(), prefix, suffix);
    }

    public void setTag(Player player, String team, String prefix, String suffix) {
        team = team.toLowerCase();

        synchronized(this) {
            if(hasTag(player)) getTagData(player).destroy(player);
        }

        TagData data = null;
        for(TagData td: datas.values()) {
            if(!td.getName().equals(team)) continue;
            data = td;
        }
        if(data == null) {
            data = new TagData(team, prefix, suffix);
            datas.put(team, data);
            data.getPacket().send();
        }
        synchronized(this) {
            data.addPlayer(player);
        }
    }

    public void sendTags(Player player) {
        datas.values().forEach(data -> data.getPacket().send(player));
    }

    public void clearTags() {
        datas.values().forEach(this::removeTeam);
    }

    public void removeTeam(TagData team) {
        removeTeam(team.getName());
    }

    public void removeTeam(String team) {
        team = team.toLowerCase();

        if(datas.get(team) == null) return;
        TagData data = datas.get(team);
        data.destroy();
        datas.remove(team);
    }

    public void clearTag(Player player) {
        for(TagData data: datas.values()) {
            if(!data.hasPlayer(player)) continue;
            data.removePlayer(player);
        }
    }

    public boolean hasTag(Player player) {
        return getTagData(player) != null;
    }

    public TagData getTagData(Player player) {
        for(TagData data: datas.values()) {
            if(!data.hasPlayer(player)) continue;
            return data;
        }
        return null;
    }

    public Collection<TagData> getDatas() {
        return datas.values();
    }
}
