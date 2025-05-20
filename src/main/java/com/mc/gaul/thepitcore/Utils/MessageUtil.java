package com.mc.gaul.thepitcore.Utils;

import com.mc.gaul.thepitcore.ThePitLite;
import org.bukkit.ChatColor;

public class MessageUtil {

    public static String Preifx = ThePitLite.getMain().getCustomConfig()
            .getConfig("message").getString("prefix").replaceAll("&", "§");

    public static String Message(String message) {
        return message.replaceAll("&", "§");
    }

}
