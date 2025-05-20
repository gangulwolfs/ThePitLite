package com.mc.gaul.thepitcore.Event;

import com.mc.gaul.thepitcore.File.ConfigX;
import com.mc.gaul.thepitcore.ThePitLite;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration configX = new ConfigX(ThePitLite.getMain().getPlugin()).getConfig("config");
        if(configX.get("options.game.lobby.name") != null){
            player.teleport(new Location(Bukkit.getWorld(configX.getString("options.game.lobby.name")), configX.getDouble("options.game.lobby.x"), configX.getDouble("options.game.lobby.y"), configX.getDouble("options.game.lobby.z")));
        }
    }
}
