package com.mc.gaul.thepitcore.Event;

import com.mc.gaul.thepitcore.ThePitLite;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();
        boolean pc = false;
        if(player.isOp()){
            return;
        }
        if(item.getItemMeta().getLore() != null){
            for (String l : item.getItemMeta().getLore()) {
                ChatColor.stripColor(l).equalsIgnoreCase("CanPlace");
                pc = true;
            }
        }

        if (!pc) {
            event.setCancelled(true);
        }

        (new BukkitRunnable() {
            public void run() {
                event.getBlock().setType(Material.AIR);
            }
        }).runTaskLater(ThePitLite.getMain().getPlugin(), (long)3 * 20L);
    }
}
