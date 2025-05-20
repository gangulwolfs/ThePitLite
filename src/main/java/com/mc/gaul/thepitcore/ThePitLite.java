package com.mc.gaul.thepitcore;

import com.mc.gaul.thepitcore.API.Trait.PlayNPC;
import com.mc.gaul.thepitcore.Command.MainCommand;
import com.mc.gaul.thepitcore.Event.BlockBreak;
import com.mc.gaul.thepitcore.Event.BlockPlace;
import com.mc.gaul.thepitcore.Event.JoinEvents;
import com.mc.gaul.thepitcore.Event.NPCClick;
import com.mc.gaul.thepitcore.File.ConfigX;
import com.mc.gaul.thepitcore.Utils.DecentHolo;
import com.mc.gaul.thepitcore.Utils.MessageUtil;
import com.mc.gaul.thepitcore.Utils.RunnableScoreBoard;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class ThePitLite extends JavaPlugin {

    public static ThePitLite thePitLite;
    Plugin plugin;
    ConfigX configX;


    @Override
    public void onEnable() {
        Load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void Load() {
        saveResource("config.yml", false);
        saveResource("items.yml", false);
        saveResource("message.yml", false);
        saveResource("npc.yml", false);
        saveResource("perks.yml", false);
        thePitLite = this;
        plugin = this;
        configX = new ConfigX(plugin);

        CitizensAPI.getTraitFactory().registerTrait(
                TraitInfo.create(PlayNPC.class).withName("playnpc"));

        getCommand("thepit").setExecutor(new MainCommand());
        Bukkit.getPluginManager().registerEvents(new JoinEvents(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new NPCClick(), this);
        new MessageUtil();
        new RunnableScoreBoard();
        CreateNPCHolo("play", new ConfigX(this).getConfig("npc"));
        CreateNPCHolo("stats", new ConfigX(this).getConfig("npc"));
        CreateNPCHolo("quests", new ConfigX(this).getConfig("npc"));
        CreateNPCHolo("upgrade", new ConfigX(this).getConfig("npc"));
        CreateNPCHolo("items", new ConfigX(this).getConfig("npc"));
        CreateNPCHolo("prestige", new ConfigX(this).getConfig("npc"));
    }

    private void CreateNPCHolo(String npctype, FileConfiguration config){
        String path = "npc." + npctype.toUpperCase() + ".id";
        ConfigurationSection section = config.getConfigurationSection(path);
        DecentHolo decentHolo = null;
        Location loc = null;
        if (section != null) {
            Set<String> npcIds = section.getKeys(false); // false면 바로 아래 key만 가져옴
            for (String npcId : npcIds) {
                String fullPath = path + "." + npcId + ".location";
                loc = new Location(Bukkit.getWorld(config.getString(fullPath + ".name")), config.getDouble(fullPath + ".x"), config.getDouble(fullPath + ".y"), config.getDouble(fullPath + ".z"));
                decentHolo = new DecentHolo(npctype + npcId);
                decentHolo.createHologram(loc.add(0, 2.9, 0));
                if(npctype.equals("play")){
                    decentHolo.writeHologram(config.getString("play.text1"));
                    decentHolo.writeHologram(config.getString("play.text2").replaceAll("%ver%", ThePitLite.getMain().getPlugin().getDescription().getVersion()));
                    decentHolo.writeHologram(config.getString("play.text3").replaceAll("%count%", "§e0"));
                } else if (npctype.equals("stats")){
                    decentHolo.writeHologram("quest.text1");
                    decentHolo.writeHologram("quest.text2");
                } else if (npctype.equals("quests")){
                    decentHolo.writeHologram("stats.text1");
                    decentHolo.writeHologram("stats.text2");
                } else if (npctype.equals("upgrade")){
                    decentHolo.writeHologram("upgrade.text1");
                    decentHolo.writeHologram("upgrade.text2");
                } else if (npctype.equals("item")){
                    decentHolo.writeHologram("items.text1");
                    decentHolo.writeHologram("items.text2");
                } else if (npctype.equals("prestige")){
                    decentHolo.writeHologram("prestige.text1");
                    decentHolo.writeHologram("prestige.text2");
                }
            }
        }
    }

    public static ThePitLite getMain() {
        return thePitLite;
    }

    public ConfigX getCustomConfig() {
        return configX;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
