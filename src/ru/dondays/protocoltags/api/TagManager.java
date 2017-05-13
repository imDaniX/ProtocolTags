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

    public void setTag(final Player player, final String name, final String prefix, final String suffix) {
        TagData td = this.datas.get(name);
        if(td == null) {
            td = new TagData(name, prefix, suffix);
            this.datas.put(td.getName(), td);
            td.send();
        }
        if(!td.isSended()) td.send();
        td.addPlayer(player);
    }

    private TagData getTagDataToReset(final Player player) {
        for(final TagData data: datas.values()) {
            if(data.getPlayers().contains(player)) return data;
        }
        return new TagData(player.getName(), "", "");
    }
}
