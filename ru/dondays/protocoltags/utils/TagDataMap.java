package ru.dondays.protocoltags.utils;

import ru.dondays.protocoltags.api.TagData;

import java.util.concurrent.ConcurrentHashMap;

public class TagDataMap
    extends ConcurrentHashMap<String, TagData> {

    public TagData get(String key) {
        return super.get(Utils.fix(key));
    }
}
