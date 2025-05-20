package com.mc.gaul.thepitcore.Utils;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DecentHolo {

    String name;

    public DecentHolo(String name) {
        this.name = name;
    }

    public void createHologram(Location location){
        if(DHAPI.getHologram(name) != null)
            return;
        DHAPI.createHologram(name, location);
    }

    public void writeHologram(String line){
        if(DHAPI.getHologram(name) == null){
            Bukkit.getLogger().warning("Hologram " + name + " not found.");
            return;
        }
        DHAPI.addHologramLine(DHAPI.getHologram(name), line);
    }

    public void moveHologram(Location location){
        if(DHAPI.getHologram(name) == null){
            Bukkit.getLogger().warning("Hologram " + name + " not found.");
            return;
        }
        DHAPI.moveHologram(DHAPI.getHologram(name), location);
    }

    public void editHologram(int line, String value){
        if(DHAPI.getHologram(name) == null){
            Bukkit.getLogger().warning("Hologram " + name + " not found.");
            return;
        }
        DHAPI.setHologramLine(DHAPI.getHologram(name), line , value);
    }

    public void updateHologram(){
        if(DHAPI.getHologram(name) == null){
            Bukkit.getLogger().warning("Hologram " + name + " not found.");
            return;
        }
        DHAPI.updateHologram(name);
    }

}
