package com.mc.gaul.thepitcore.API.Gui;

import com.mc.gaul.thepitcore.Utils.DecentHolo;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class Menu {

    static List<Inventory> inv;

    @FunctionalInterface
    interface SlotCustomizer {
        void customize();
    }

    public static void createMenu(String name, int size, SlotCustomizer customizer) {
        if(inv.stream().noneMatch(inv -> inv.getName().equalsIgnoreCase(name))) {
            customizer.customize();
            inv.add(Bukkit.createInventory(null, size, name));
            return;
        }
    }

    public static void openMenu(String name, Player player) {
        Inventory inventory = inv.stream().filter(inv -> inv.getName().equalsIgnoreCase(name)).findFirst().get();
        player.openInventory(inventory);
    }

}
