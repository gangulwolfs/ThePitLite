package com.mc.gaul.thepitcore.Utils;

import com.mc.gaul.thepitcore.ThePitLite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RunnableScoreBoard {

    public RunnableScoreBoard() {
        ScoreBoard sb = new ScoreBoard();
        Bukkit.getScheduler().runTaskTimer(ThePitLite.getMain(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                sb.applyLobbyBoard(p);
            }
        }, 0L, 20L);
    }
}
