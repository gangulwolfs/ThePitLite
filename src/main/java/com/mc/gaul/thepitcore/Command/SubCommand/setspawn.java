package com.mc.gaul.thepitcore.Command.SubCommand;

import com.mc.gaul.thepitcore.Utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawn extends IFcmd {

    public setspawn() {
        super("setspawn", "Default Spawn Set.");
    }

    public void execute(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;
        player.sendMessage(MessageUtil.Preifx + "응애. 일단 기다려보셈.");
    }
}
