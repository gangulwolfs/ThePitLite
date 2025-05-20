package com.mc.gaul.thepitcore.Command.SubCommand.Admin;

import com.mc.gaul.thepitcore.Command.SubCommand.IFcmd;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;

public class TestCMD extends IFcmd {
    public TestCMD() {
        super("test", "give a metadataItems");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String s, String[] args) throws IOException {
        if(!(sender instanceof Player)) {
            return;
        }
        Player p = (Player) sender;
        ItemStack item = new ItemStack(Material.STONE);
        item.setAmount(64);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(Arrays.asList("&7CanPlace".replaceAll("&", "§")));

        item.setItemMeta(itemMeta);
        p.getInventory().addItem(item);

    }


}
