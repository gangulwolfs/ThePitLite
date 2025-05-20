package com.mc.gaul.thepitcore.Command.SubCommand;

import com.mc.gaul.thepitcore.File.ConfigX;
import com.mc.gaul.thepitcore.ThePitLite;
import com.mc.gaul.thepitcore.Utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Arena extends IFcmd{

    public Arena() {
        super("setup", "SetUpArenaCommand");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(MessageUtil.Preifx + "you can't use this command in console.");
            return;
        }
        Player player = (Player) sender;
        if(args.length <= 2){
            player.sendMessage(MessageUtil.Preifx + "/thepit arena setup create <name>");
            player.sendMessage(MessageUtil.Preifx + "/thepit arena setup delete <name>");
            player.sendMessage(MessageUtil.Preifx + "/thepit arena setup check <name>");
            player.sendMessage(MessageUtil.Preifx + "/thepit arena setup setmax <name>");
            player.sendMessage(MessageUtil.Preifx + "/thepit arena setup list");
            return;
        }
        ConfigX configX = new ConfigX(ThePitLite.getMain());
        List<String> arenaList = configX
                .getFileNamesWithoutExtension(ThePitLite.getMain().getDataFolder() + "/arena");

        if(args[2].equalsIgnoreCase("list")){
            for (String arena : arenaList){
                sender.sendMessage("Arena: " + arena);
            }
            return;
        }
        if (args[2].equalsIgnoreCase("create") || args[2].equalsIgnoreCase("delete")) {
            String arenaName = args[3]; // args[2]를 arena 이름으로 가정

            boolean exists = arenaList.stream().anyMatch(a -> a.equalsIgnoreCase(arenaName));

            if (args[2].equalsIgnoreCase("create")) {
                if (exists) {
                    sender.sendMessage("§cIt's an arena that already exists.");
                    return;
                }
                // yaml 생성 로직
                createArenaYaml(arenaName);
                sender.sendMessage("§aArena creation completed: " + arenaName);
            }

            if (args[2].equalsIgnoreCase("delete")) {
                if (!exists) {
                    sender.sendMessage("§cThis is a non-existent arena.");
                    return;
                }
                // 🗑 yaml 제거 로직
                deleteArenaYaml(arenaName);
                sender.sendMessage("§aArena deleted complete: " + arenaName);
            }
        }
        if(args[2].equalsIgnoreCase("setup") || args[2].equalsIgnoreCase("setmax")){
            boolean exists = arenaList.stream().anyMatch(a -> a.equalsIgnoreCase(args[2]));
            if(!exists){
                sender.sendMessage("This arena does not exist.");
                return;
            }
            if(args[2].equalsIgnoreCase("setmax")){
                getArenaYaml(args[3]).set("max", Integer.parseInt(args[3]));
                sender.sendMessage(MessageUtil.Preifx + "It has been successfully setup. -> ArenaMaxSize: " + args[3]);
            }
        }
    }

    private YamlConfiguration getArenaYaml(String name){
        File file = new File(ThePitLite.getMain().getDataFolder() + "/arena", name + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    private void createArenaYaml(String name) {
        File file = new File(ThePitLite.getMain().getDataFolder() + "/arena", name + ".yml");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (file.createNewFile()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.set("name", name);
                config.set("maxplayer", 30);
                config.save(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteArenaYaml(String name) {
        File file = new File(ThePitLite.getMain().getDataFolder() + "/arena", name + ".yml");
        if (file.exists()) {
            file.delete();
        }
    }
}
