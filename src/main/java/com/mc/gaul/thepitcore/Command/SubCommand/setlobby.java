package com.mc.gaul.thepitcore.Command.SubCommand;

import com.mc.gaul.thepitcore.File.ConfigX;
import com.mc.gaul.thepitcore.ThePitLite;
import com.mc.gaul.thepitcore.Utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class setlobby extends IFcmd{

    public setlobby() {
        super("setlobby", "set a spawn looby.");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String s, String[] args) throws IOException {
        Player player = (Player) sender;
        ConfigX configX = new ConfigX(ThePitLite.getMain().getPlugin());
        configX.getConfig("config").set("options.game.lobby.name", player.getWorld().getName());
        configX.getConfig("config").set("options.game.lobby.x", player.getLocation().getBlockX());
        configX.getConfig("config").set("options.game.lobby.y", player.getLocation().getBlockY());
        configX.getConfig("config").set("options.game.lobby.z", player.getLocation().getBlockZ());
        // 파일에 저장
        try {
            configX.getConfig("config").save(new File(ThePitLite.getMain().getDataFolder(), "config.yml")); // 또는 configX.save("config") 인 경우도 있음
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendMessage(MessageUtil.Preifx + "Location: " +
                "WorldName: " + player.getWorld().getName() + " , " +
                player.getLocation().getBlockX() + " , " +
                player.getLocation().getBlockY() + " , " +
                player.getLocation().getBlockZ());
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getName() {
        return super.getName();
    }

}
