package com.mc.gaul.thepitcore.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigX {

    private final Plugin plugin;
    private File file;
    private FileConfiguration config;

    public ConfigX(Plugin plugin) {
        this.plugin = plugin;
    }

    // 1. Create config file if not exists
    public void create(String dir, String name) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        this.file = new File(plugin.getDataFolder() + dir, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }
    }

    // 2. Delete config file
    public boolean delete(String name) {
        this.file = new File(plugin.getDataFolder(), name);
        return file.exists() && file.delete();
    }

    // 3. Get config object
    public FileConfiguration getConfig(String name) {
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        if (config == null) {
            config = YamlConfiguration.loadConfiguration(file);
        }
        return config;
    }

    public File getFile(String name) {
        return this.file = new File(plugin.getDataFolder(), name + ".yml");
    }

    public void save(String name) throws IOException {
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        config.save(file);
    }

    public List<String> getFileNamesWithoutExtension(String folderPath) {
        List<String> fileNames = new ArrayList<>();

        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return Arrays.asList("NotFound!");
        }

        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                String name = file.getName();
                int lastDot = name.lastIndexOf(".");
                if (lastDot > 0) {
                    name = name.substring(0, lastDot); // 확장자 제거
                }
                fileNames.add(name);
            }
        }

        return fileNames;
    }
}
