package ru.dondays.protocoltags.utils;

import org.bukkit.ChatColor;

public class Utils {

    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String parseName(String input) {
        if(input.length() > 15) return input.substring(0, 15);
        return input;
    }
}
