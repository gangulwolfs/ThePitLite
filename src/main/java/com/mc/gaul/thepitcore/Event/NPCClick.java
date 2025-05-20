package com.mc.gaul.thepitcore.Event;

import com.mc.gaul.thepitcore.API.Trait.PlayNPC;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCClick implements Listener {

    @EventHandler
    public void onNPCClick(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        if (npc.hasTrait(PlayNPC.class)) {
            player.sendMessage("Checked!");
        }
    }

    @EventHandler
    public void onNPCClick(NPCLeftClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();
        if (npc.hasTrait(PlayNPC.class)) {
            player.sendMessage("Checked!");
        }
    }

}
