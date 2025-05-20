package com.mc.gaul.thepitcore.Command;

import com.mc.gaul.thepitcore.Command.SubCommand.Admin.CreateNPC;
import com.mc.gaul.thepitcore.Command.SubCommand.Admin.TestCMD;
import com.mc.gaul.thepitcore.Command.SubCommand.Arena;
import com.mc.gaul.thepitcore.Command.SubCommand.IFcmd;
import com.mc.gaul.thepitcore.Command.SubCommand.setlobby;
import com.mc.gaul.thepitcore.Command.SubCommand.setspawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor {

    List<IFcmd> ifcomdList = new ArrayList<>();
    List<IFcmd> ifcomdAdminList = new ArrayList<>();
    List<IFcmd> ifcomdArenaList = new ArrayList<>();

    public MainCommand() {
        ifcomdList.add(new setspawn());
        ifcomdList.add(new setlobby());
        ifcomdAdminList.add(new CreateNPC());
        ifcomdAdminList.add(new TestCMD());
        ifcomdArenaList.add(new Arena());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (ifcomdList.isEmpty()) {
            sender.sendMessage("IDK. why command is Empty.");
            return false;
        }

        if (args.length == 0) {
            CommandMath(ifcomdList, sender, 1, "");
            sender.sendMessage("&7[AdminCommand] -> /thepit admin".replaceAll("&", "§"));
            return false;
        }

        if (isNumberic(args[0])) {
            CommandMath(ifcomdList, sender, Integer.parseInt(args[0]), "");
            return true;
        }

        if (!args[0].equalsIgnoreCase("admin") && !args[0].equalsIgnoreCase("arena")) {
            ifcomdList.stream()
                    .filter(a -> a.getName().equalsIgnoreCase(args[0]))
                    .forEach(command -> {
                        try {
                            command.execute(sender, cmd, label, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return true;
        }

        // admin 명령 처리
        if (args[0].equalsIgnoreCase("admin")) {
            if (args.length < 2) {
                CommandMath(ifcomdAdminList, sender, 1, "admin");
                return true;
            }

            ifcomdAdminList.stream()
                    .filter(a -> a.getName().equalsIgnoreCase(args[1]))
                    .forEach(command -> {
                        try {
                            command.execute(sender, cmd, label, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return true;
        }

        // arena 명령 처리
        if (args[0].equalsIgnoreCase("arena")) {
            if (args.length < 2) {
                CommandMath(ifcomdArenaList, sender, 1, "arena");
                return true;
            }

            ifcomdArenaList.stream()
                    .filter(a -> a.getName().equalsIgnoreCase(args[1]))
                    .forEach(command -> {
                        try {
                            command.execute(sender, cmd, label, args);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return true;
        }

        return false;
    }

    private static boolean isNumberic(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }

    private void CommandMath(List<IFcmd> ifcomdList, CommandSender sender, int page, String baseCommandPath) {
        int commandsPerPage = 6;
        int totalCommands = ifcomdList.size();
        int totalPages = (int) Math.ceil((double) totalCommands / commandsPerPage);

        if (page < 1 || page > totalPages) {
            sender.sendMessage(ChatColor.RED + "Invalid page! (1 ~ " + totalPages + ")");
            return;
        }

        int startIndex = (page - 1) * commandsPerPage;
        int endIndex = Math.min(startIndex + commandsPerPage, totalCommands);

        sender.sendMessage(ChatColor.GOLD + "Command List (Page " + page + "/" + totalPages + "):");

        String prefix = "/thepit" + (baseCommandPath.isEmpty() ? "" : " " + baseCommandPath.trim());

        for (int i = startIndex; i < endIndex; i++) {
            IFcmd ifcmd = ifcomdList.get(i);
            sender.sendMessage(prefix + " " + ifcmd.getName() + " - " + ifcmd.getDescription());
        }
    }
}
