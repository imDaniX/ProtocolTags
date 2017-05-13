package ru.dondays.protocoltags.api;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.Map;

public class TagManager {

    private final Map<String, TagData> datas = Maps.newHashMap();

    public void checkTag(final Player player) {
        datas.values().forEach(data -> {
            if(data.getPlayers().remove(player)) {
                data.removePlayer(player);
                data.getPacket().playerRemoved(player);
            }
        });
    }

    public void clearTags(final Player player) {
        datas.values().forEach(data -> data.getPacket().destroy(player));
    }

    public void sendTags(final Player player) {
        this.datas.values().forEach(data -> data.send(player));
    }

    public void resetTag(final Player player) {
        final TagData data = this.getTagDataToReset(player);
        data.addPlayer(player);
        data.send();
    }

    public void setOwnTag(final Player player, final String prefix, final String suffix) {
        this.setOwnTag(player, prefix, suffix);
    }

    public void setOwnTag(final Player player, final String team, final String prefix, final String suffix) {
        this.setTag(player, team, prefix, suffix, false);
    }

    public void setTag(final Player player, final String prefix, final String suffix) {
        this.setTag(player, player.getName(), prefix, suffix);
    }

    public void setTag(final Player player, final String team, final String prefix, final String suffix) {
        this.setTag(player, team, prefix, suffix, true);
    }

    private void setTag(final Player player, final String team, final String prefix, final String suffix, final boolean send) {
        if(this.datas.containsKey(team.toLowerCase())) {
            final TagData data = this.datas.get(team.toLowerCase());
            if(!data.getPlayers().contains(player)) data.addPlayer(player);
            data.send();
            return;
        }
        final TagData data = new TagData(team, prefix, suffix);
        this.datas.put(team.toLowerCase(), data);
        data.addPlayer(player);
        if(send) {
            data.send();
        } else {
            data.send(player);
        }
    }

    private TagData getTagDataToReset(final Player player) {
        for(final TagData data: datas.values()) {
            if(data.getPlayers().contains(player)) return data;
        }
        return new TagData(player.getName(), "", "");
    }
}
