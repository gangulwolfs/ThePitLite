package com.mc.gaul.thepitcore.Command.SubCommand;

import com.mc.gaul.thepitcore.ThePitLite;
import com.mc.gaul.thepitcore.Utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public abstract class IFcmd {
    String name;
    String description;

    public IFcmd(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void execute(CommandSender sender, Command cmd, String s, String[] args) throws IOException {
        Player p = (Player) sender;
        ThePitLite.getMain().getConfig().set("options.game.lobby", p.getLocation());
        ThePitLite.getMain().saveConfig();
        p.sendMessage(MessageUtil.Preifx + "completed spawn location: " + p.getLocation().getBlockX()
                + " , " + p.getLocation().getBlockY()
                + " , " + p.getLocation().getBlockZ());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
